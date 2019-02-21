import java.util.Scanner;

public class BankingSystem {
  private static User user;
  private static String inputMessage;
  private static Scanner in;

  private static void login() {
    if (user == null) {
      System.out.println("Enter username");
      user = UserAccount.login(in.nextLine().toLowerCase());
      AvailableTickets.login();
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
      AvailableTickets.logout();
      inputMessage = "Welcome to the Banking System";
    } else {
      System.out.println("Invalid");
    }
  }

  private static void create() {
    String desiredUserType;
    String desiredUsername;

    if (user != null && user.create()) {
      System.out.println("Enter Desired username (less than 16 characters) ");
      desiredUsername = in.nextLine().toLowerCase();
      if (desiredUsername.length() > 15)
        System.out.println("Error: must be less than 16 characters");
      else
        System.out.println("Enter user Type: AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard");
      desiredUserType = in.nextLine().toUpperCase();
      UserAccount.create(desiredUsername, desiredUserType);
    } else {
      System.out.println("Invalid command");
    }
  }

  private static void delete() {
    if (user != null && user.delete()) {
      System.out.println("Enter Desired username (less than 16 characters) ");
      String desiredUsername = in.nextLine().toLowerCase();
      if (desiredUsername.length() > 15)
        System.out.println("Error: must be less than 16 characters");
      else if (desiredUsername.equals(user.getUsername()))
        System.out.println("Cannot delete current user");
      else
        UserAccount.delete(desiredUsername);
    } else {
      System.out.println("Invalid command");
    }
  }

  private static void refund(){
    if (user != null && user.refund()){
      System.out.println("Enter buyer username");
      String buyer = in.nextLine();
      System.out.println("Enter seller username");
      String seller = in.nextLine();
      System.out.println("Enter refund amount");
      Double amount = Double.parseDouble(in.nextLine());
      UserAccount.refund(buyer, seller, amount);
    } else {
      System.out.println("Invalid command");
    }
  }

  private static void addCredit() {
    if (user != null) {
      System.out.println("Enter amount to add. Limit $1000 per session");
      Double amount = Double.parseDouble(in.nextLine());
      if (user.getUserType().equals("AA")) {
        System.out.println("Enter username");
        UserAccount.addCredit(in.nextLine(), amount);
      } else {
        user.addCredit(amount, false);
      }
    } else {
      System.out.println("Invalid command");
    }
  }

  private static void buy(){
    if(user != null && user.buy()){
      System.out.println("Enter Event title");
      String title = in.nextLine();
      System.out.println("Enter number of tickets for purchase");
      int quantity = Integer.parseInt(in.nextLine());
      System.out.println("Enter seller's username");
      String seller = in.nextLine().toLowerCase();
      Event e = AvailableTickets.getEvent(seller, title);
      if(e != null){
        double total = e.getPrice() * quantity; 
        System.out.println(String.format("At $%1$.2f per ticket the total is $%2$.2f",
        e.getPrice(), total));
        Boolean confirm = false;
        String answer;
        while(!confirm){
          System.out.println("Confirm purchase: Yes/No");
          answer = in.nextLine().toLowerCase();
          if(answer.equals("no")){
            return;
          } else if (answer.equals("yes")){
            if(user.getCredit() < total){
              System.out.println("Not enough credit");
            } else {
              if(e.getQuantity() < quantity){
                System.out.println("Not enough tickets");
              } else {
                UserAccount.addCredit(user.getUsername(), -total);
                AvailableTickets.sellTicket(e, quantity);
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

  private static void sell(){
    if(user != null && user.sell()){
      System.out.println("Enter Event title");
      String title = in.nextLine();
      System.out.println("Enter ticket price");
      double price = Double.parseDouble(in.nextLine());
      System.out.println("Enter total ticket quantity");
      int quantity = Integer.parseInt(in.nextLine());
      AvailableTickets.addEvent(new Event(title, user.getUsername(), price, quantity));
    } else {
      System.out.println("Invalid command");
    }
  }

  public static void main(String args[]) {
    in = new Scanner(System.in, "UTF-8");
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
      default:
        System.out.println("Invalid Command");
        break;
      }
    }
  }
}
