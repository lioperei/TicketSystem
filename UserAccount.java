import java.io.*;
import java.util.ArrayList;

public class UserAccount {
  private static String userAccountsFile = "user_account.txt";
  private static String transactionsFile = "daily_transaction.txt";
  private static ArrayList<User> users = new ArrayList<>();

  public static User login(String username) {

    BufferedReader br;
    User found = null;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(userAccountsFile)));
      String user;
      String[] userLines;
      
      while ((user = br.readLine()) != null) {
        userLines = user.split(" ");
        User u = new User(userLines[0], userLines[1], Double.parseDouble(userLines[2]));
        if (u.getUsername().equals(username)) {
          found = u;
        }
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
      out = new PrintWriter(new BufferedWriter(new FileWriter(transactionsFile, true)));
    } catch (IOException e) {
      System.out.println("Error logging out");
      return user;
    }
    out.println(user.endSession());
    out.close();
    System.out.println("Logged out");
    return null;
  }

}