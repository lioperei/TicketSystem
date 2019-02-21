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
  private static ArrayList<User> users = new ArrayList<>();
  private static String userAccountsFile = "user_account.txt";

  public static User login(String username) {

    BufferedReader br;
    User found = null;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(userAccountsFile)));
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
        users.add(u);
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Login Error. Try again");
      return null;
    }

    return (found != null) ? found : null;
  }

  public static User logout(User user) {
    PrintWriter out;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(userAccountsFile, false)));
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

  public static void create(String desiredUsername, String desiredUserType) {
    for (User u : users) {
      if (u.getUsername().equals(desiredUsername)) {
        System.out.println("Username already taken");
        return;
      }
    }

    try {
      FileWriter fr = new FileWriter(userAccountsFile, true);
      BufferedWriter br = new BufferedWriter(fr);
      PrintWriter pr = new PrintWriter(br);
      pr.println(String.format("%-15s", desiredUsername) + " " + desiredUserType + " 000000000.00");
      pr.close();
      switch (desiredUserType) {
      case "AA":
        users.add(new Admin(desiredUsername, 0));
        break;
      case "FS":
        users.add(new FullStandard(desiredUsername, 0));
        break;
      case "SS":
        users.add(new SellStandard(desiredUsername, 0));
        break;
      case "BS":
        users.add(new BuyStandard(desiredUsername, 0));
        break;
      default:
        users.add(new User(desiredUsername, desiredUserType, 0));
        break;
      }
      TransactionFile.userTransactionLine("01", desiredUsername, desiredUserType, 0);
    } catch (IOException e) {
      System.out.println("Error creating account");
    }

  }

  public static void addCredit(String username, double amount){
    for (User u : users) {
      if(u.getUsername().equals(username)){
        u.addCredit(amount, false);
        return;
      }
    }
    System.out.println("Invalid username");
  }

  public static void refund(String buyerUsername, String sellerUsername, double amount){
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

  public static void delete(String username){
    User u = getUser(username);
    if(users.remove(u)){
      TransactionFile.userTransactionLine("02", u.getUsername(), u.getUserType(), u.getCredit());
      System.out.println(String.format("Deleted %s", u.getUsername()));
    } else {
      System.out.println("No such user");
    }
  }

  private static User getUser(String username){
    for (User u : users) {
      if(u.getUsername().equals(username)){
        return u;
      }
    }
    return null;
  }


}
