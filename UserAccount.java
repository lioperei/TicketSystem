import java.io.*;
import java.util.ArrayList;

/*
  Responsible for interactions with the useraccounts file
 */
public class UserAccount {
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
        userLines = user.split(" ");
        switch (userLines[1]) {
        case "AA":
          u = new Admin(userLines[0], Double.parseDouble(userLines[2]));
          break;
        default:
          u = new User(userLines[0], userLines[1], Double.parseDouble(userLines[2]));
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

}