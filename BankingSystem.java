import java.util.Scanner;

public class BankingSystem {
  private static User user;
  private static String inputMessage;
  private static Scanner in;

  private static void login() {
    if (user == null) {
      System.out.println("Enter username");
      user = UserAccount.login(in.nextLine().toLowerCase());
      if (user != null)
        inputMessage = user.getCommands();
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
  
  private static void create() {
    String desiredUserType;
    String desiredUsername;
	  
    if (user == null) {
      System.out.println("Enter Desired username (less than 16 characters) ");
      desiredUsername = in.nextLine().toLowerCase();
	   if (desiredUsername.length() > 15) 
		   System.out.println("Error: must be less than 16 characters");
	      else
	      	System.out.println("Enter user Type: AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard");
	      	desiredUserType = in.nextLine().toLowerCase();
	      	UserAccount.create(desiredUsername, desiredUserType);
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
      case "create":      
        create();
        break;	      
      default:
        System.out.println("Invalid Command");
        break;
      }
    }
  }
}
