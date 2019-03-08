import java.util.Arrays;
import java.util.Scanner;

public class TicketSystem {
  private static User user;
  private static UserAccount UA;
  private static AvailableTickets AT;
  private static String inputMessage;
  private static Scanner in;

  /**
   * Login command the initializes the user and available tickets
   */
  private static void login() {
    if (user == null) {
      System.out.println("Enter username");
      user = UA.login(getInput().toLowerCase().replaceAll("^\\s+", ""));
      AT.login();
      if (user != null)
        inputMessage = user.getCommands();
      else
        System.out.println("Invalid user");
    } else {
      System.out.println("Invalid");
    }
  }

  /**
   * Logs the user out of the system
   */
  public static void logout() {
    if (user != null) {
      user = UA.logout(user);
      AT.logout();
      inputMessage = "Welcome to the Banking System";
    } else {
      System.out.println("Invalid");
    }
  }

  /**
   * Handles the user create function
   */
  private static void create() {
    if (user != null && user.create()) {
      String desiredUserType;
      String desiredUsername;
      System.out.println("Enter Desired username (less than 16 characters) ");
      desiredUsername = getInput().toLowerCase().replaceAll("^\\s+", "");
      if (desiredUsername.length() > 15)
        System.out.println("Error: must be less than 16 characters");
      else if (UA.getUser(desiredUsername) != null) {
        System.out.println("Username already exists");
        return;
      } else {
        System.out.println("Enter user Type: AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard");
      }
      desiredUserType = getInput().toUpperCase().replaceAll("^\\s+", "");
      String[] types = { "AA", "FS", "BS", "SS" };
      if (!Arrays.asList(types).contains(desiredUserType)) {
        System.out.println("Invalid usertype");
        return;
      } else {
        UA.create(desiredUsername, desiredUserType);
      }
    } else {
      System.out.println("Invalid command");
    }
  }

  /**
   * Handles the user delete function
   */
  private static void delete() {
    if (user != null && user.delete()) {
      System.out.println("Enter Desired username (less than 16 characters) ");
      String desiredUsername = getInput().toLowerCase().replaceAll("^\\s+", "");
      if (desiredUsername.length() > 15)
        System.out.println("Error: must be less than 16 characters");
      else if (desiredUsername.equals(user.getUsername()))
        System.out.println("Cannot delete current user");
      else
        UA.delete(desiredUsername);
    } else {
      System.out.println("Invalid command");
    }
  }

  /**
   * Handles the user refund function
   */
  private static void refund() {
    if (user != null && user.refund()) {
      System.out.println("Enter buyer username");
      String buyer = getInput().replaceAll("^\\s+", "");
      System.out.println("Enter seller username");
      String seller = getInput().replaceAll("^\\s+", "");
      System.out.println("Enter refund amount");
      Double amount = Double.parseDouble(getInput().replaceAll("^\\s+", ""));
      UA.refund(buyer, seller, amount);
    } else {
      System.out.println("Invalid command");
    }
  }

  /**
   * Handles the user addcredit function
   */
  private static void addCredit() {
    if (user != null) {
      System.out.println("Enter amount to add. Limit $1000 per session");
      Double amount = Double.parseDouble(getInput().replaceAll("^\\s+", ""));
      if (user.getUserType().equals("AA")) {
        System.out.println("Enter username");
        UA.addCredit(getInput().replaceAll("^\\s+", ""), amount);
      } else {
        user.addCredit(amount, false);
      }
    } else {
      System.out.println("Invalid command");
    }
  }

  /**
   * Handles the user buy function
   */
  private static void buy() {
    if (user != null && user.buy()) {
      System.out.println("Enter Event title");
      String title = getInput().replaceAll("^\\s+", "");
      System.out.println("Enter number of tickets for purchase");
      int quantity = Integer.parseInt(getInput().replaceAll("^\\s+", ""));
      System.out.println("Enter seller's username");
      String seller = getInput().toLowerCase().replaceAll("^\\s+", "");
      Event e = AT.getEvent(seller, title);
      if (e != null) {
        double total = e.getPrice() * quantity;
        System.out.println(String.format("At $%1$.2f per ticket the total is $%2$.2f", e.getPrice(), total));
        Boolean confirm = false;
        String answer;
        while (!confirm) {
          System.out.println("Confirm purchase: Yes/No");
          answer = getInput().toLowerCase().replaceAll("^\\s+", "");
          if (answer.equals("no")) {
            confirm = true;
          } else if (answer.equals("yes")) {
            if (user.getCredit() < total) {
              System.out.println("Not enough credit");
              confirm = true;
            } else {
              if (e.getQuantity() < quantity) {
                System.out.println("Not enough tickets");
                confirm = true;
              } else {
                UA.addCredit(user.getUsername(), -total);
                AT.sellTicket(e, quantity);
                TransactionFile.buyTransactionLine(e);
                confirm = true;
              }
            }
          }
        }
      } else {
        System.out.println("Invalid event");
      }
    } else {
      System.out.println("Invalid command");
    }
  }

  /**
   * Handles the user sell function
   */
  private static void sell() {
    if (user != null && user.sell()) {
      System.out.println("Enter Event title less than 15 characters");
      String title = getInput().replaceAll("^\\s+", "");
      if (title.length() > 15) {
        System.out.println("Invalid Title");
        return;
      }
      System.out.println("Enter ticket price less than $1000");
      double price = Double.parseDouble(getInput().replaceAll("^\\s+", ""));
      if (price > 1000 || price <= 0) {
        System.out.println("Invalid Ticket price");
        return;
      }
      System.out.println("Enter total ticket quantity less than 100");
      int quantity = Integer.parseInt(getInput().replaceAll("^\\s+", ""));
      if (quantity > 101 || quantity <= 0) {
        System.out.println("Invalid Ticket quantity");
        return;
      }
      Event ev = new Event(title, user.getUsername(), price, quantity);
      AT.addEvent(ev);
      TransactionFile.sellerTransactionLine(ev);
    } else {
      System.out.println("Invalid command");
    }
  }

  public static String getInput() {
    String input = in.nextLine().replaceAll("^\\s+", "");
    if (input.equals("exit")) {
      System.exit(0);
      return "";
    } else {
      return input;
    }
  }

  /**
   * The main execution of the banking system
   */
  public static void main(String args[]) {
    in = new Scanner(System.in, "UTF-8");
    inputMessage = "Welcome to the Banking System";
    UA = new UserAccount(args[0]);
    AT = new AvailableTickets(args[1]);
    while (true) {
      System.out.println(inputMessage);
      try {
        String x = getInput().toLowerCase().replaceAll("^\\s+", "");
        switch (x) {
        case "login":
          login();
          break;
        case "logout":
          logout();
          break;
        case "create":
          create();
          break;
        case "delete":
          delete();
          break;
        case "buy":
          buy();
          break;
        case "sell":
          sell();
          break;
        case "refund":
          refund();
          break;
        case "add credit":
          addCredit();
          break;
        case "exit":
          System.exit(0);
          break;
        default:
          System.out.println(x);
          System.out.println("Invalid Command");
          break;
        }
      } catch (IllegalArgumentException e) {
        if (e != null) {
          System.out.println("Invalid parameter passed. Please try again");
        }
      }
    }
  }
}
