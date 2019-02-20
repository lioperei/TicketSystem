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
    try {
      FileWriter fr = new FileWriter(userAccountsFile, true);
      BufferedWriter br = new BufferedWriter(fr);
      PrintWriter pr = new PrintWriter(br);
      pr.println(String.format("%-15s", desiredUsername) + " " + desiredUserType + " 000000000.00");
      pr.close();
    } catch (IOException e) {
      System.out.println("Error creating account");
    }

  }

}
