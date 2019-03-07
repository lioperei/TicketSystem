import java.io.*;
import java.util.ArrayList;

/*
Responsible for interactions with the useraccounts file
*/
public class UserAccount {

  private static final int USERNAME = 0;
  private static final int USERTYPE = 1;
  private static final int CREDIT = 2;

  // List of users that will be read from the user account file on every login
  private ArrayList<User> users;
  // private static String this.accountsFile = "user_account.txt";
  private String accountsFile;

  public UserAccount(String accountsFile){
    this.accountsFile = accountsFile;
    this.users = new ArrayList<>();
  }

  /**
   * Attempts to retrieve the user via username 
   * @param username
   * @return: User is found null otherwise
   */
  public User login(String username) {

    BufferedReader br;
    User found = null;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(this.accountsFile)));
      String user;
      String[] userLines;
      User u;
      while (!(user = br.readLine()).equals("END")) {
        userLines = user.split("\\s+");
        switch (userLines[USERTYPE]) {
        case "AA":
          u = new Admin(userLines[USERNAME], Double.parseDouble(userLines[CREDIT]));
          break;
        case "BS":
          u = new BuyStandard(userLines[USERNAME], Double.parseDouble(userLines[CREDIT]));
          break;
        case "SS":
          u = new SellStandard(userLines[USERNAME], Double.parseDouble(userLines[CREDIT]));
          break;
        case "FS":
          u = new FullStandard(userLines[USERNAME], Double.parseDouble(userLines[CREDIT]));
          break;
        default:
          u = new User(userLines[USERNAME], userLines[USERTYPE], Double.parseDouble(userLines[CREDIT]));
          break;
        }
        found = (u.getUsername().equals(username)) ? u : found;
        this.users.add(u);
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Login Error. Try again");
      return null;
    }

    return (found != null) ? found : null;
  }

  /**
   * Logs the user out of the system and writes all users to the useraccounts file
   * @param user
   * @return: User is unsuccessful, null otherwise
   */
  public User logout(User user) {
    PrintWriter out;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(this.accountsFile, false)));
      TransactionFile.logout(user);
    } catch (IOException e) {
      System.out.println("Error logging out");
      return user;
    }
    for (User u : users) {
      out.println(u.getUserAccount());
    }
    out.println("END");
    System.out.println("Logged out");
    users = new ArrayList<>();
    out.close();
    return null;
  }

  /**
   * Creates a user and adds logs the transaction
   * @param desiredUsername
   * @param desiredUserType
   */
  public void create(String desiredUsername, String desiredUserType) {
    User u = getUser(desiredUsername);
    if(u != null){
      System.out.println("User already exists");
      return;
    }

    try {
      FileWriter fr = new FileWriter(this.accountsFile, true);
      BufferedWriter br = new BufferedWriter(fr);
      PrintWriter pr = new PrintWriter(br);
      pr.println(String.format("%-15s", desiredUsername) + " " + desiredUserType + " 000000000.00");
      pr.close();
      switch (desiredUserType) {
      case "AA":
        this.users.add(new Admin(desiredUsername, 0));
        break;
      case "FS":
        this.users.add(new FullStandard(desiredUsername, 0));
        break;
      case "SS":
        this.users.add(new SellStandard(desiredUsername, 0));
        break;
      case "BS":
        this.users.add(new BuyStandard(desiredUsername, 0));
        break;
      default:
        this.users.add(new User(desiredUsername, desiredUserType, 0));
        break;
      }
      TransactionFile.userTransactionLine("01", desiredUsername, desiredUserType, 0);
    } catch (IOException e) {
      System.out.println("Error creating account");
    }

  }

   /**
    * Add credit the the specified user
    * @param username: Username to add credit to
    * @param amount: Amount of credit to add
    */
  public void addCredit(String username, double amount){
    User u = getUser(username);
    if(u != null){
      u.addCredit(amount, false);
    }
  }

  /**
   * Updates the seller and buyer to reflect the refund
   * @param buyerUsername: Username of buyer
   * @param sellerUsername: Username of seller
   * @param amount: Amount to refund
   */
  public void refund(String buyerUsername, String sellerUsername, double amount){
    User buyer = getUser(buyerUsername);
    if( buyer == null ){
      System.out.println("Invalid Buyer");
      return;
    }
    User seller = getUser(sellerUsername);
    if( seller == null ){
      System.out.println("Invalid Seller");
      return;
    }
    if(seller.deductCredit(amount)){
      if(!buyer.addCredit(amount, true)){
        seller.addCredit(amount, true);
      } else {
        TransactionFile.refundTransactionLine(buyerUsername, sellerUsername, amount);
        System.out.print("Successfull transfer");
      }
    } else {
      System.out.println("Insufficient seller funds");
    }

  }

  /**
   * Deletes the specified user from the user account file
   * @param username: Username to delete
   */
  public void delete(String username){
    User u = getUser(username);
    if(this.users.remove(u)){
      TransactionFile.userTransactionLine("02", u.getUsername(), u.getUserType(), u.getCredit());
      System.out.println(String.format("Deleted %s", u.getUsername()));
    } else {
      System.out.println("No such user");
    }
  }

  /**
   * Returns the user specified by username
   * @param username: Username to search for
   * @return: The user if found, null otherwise
   */
  private User getUser(String username){
    for (User u : users) {
      if(u.getUsername().equals(username)){
        return u;
      }
    }
    return null;
  }


}
