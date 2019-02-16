import java.util.Scanner;

public class BankingSystem {
  private static User user = null;
  private static String inputMessage;
  private static Scanner in;

  private static void login() {
    if (user == null) {
      System.out.println("Enter username");
      user = UserAccount.login(in.nextLine().toLowerCase());
      if (user != null)
        inputMessage = "Enter Command\n";
      else
        System.out.println("Invalid user");
    } else {
      System.out.println("Invalid");
    }
  }

  public static void logout() {
    if (user != null) {
      user = UserAccount.logout(user);
      inputMessage = "";
    } else {
      System.out.println("Invalid");
    }
  }

  public static void main(String args[]) {
    in = new Scanner(System.in);
    inputMessage = "Welcome to the Banking System";
    while (true) {
      System.out.println(inputMessage);
      switch (in.nextLine().toLowerCase()) {
      case "login":
        login();
        break;
      case "logout":
        logout();
        break;
      default:
        System.out.println("Invalid Command");
        break;
      }
    }
  }
}