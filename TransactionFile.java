import java.io.*;
import java.util.ArrayList;

public class TransactionFile{
  // List of transaction strings that will be written to the transactions file
  private static String transactionsFile = "daily_transaction.txt";
  private static ArrayList<String> transactionLines = new ArrayList<>();

  public static void addTransaction(String line){
    transactionLines.add(line);
  }

  public static void userTransactionLine(String code, String username, String userType, double amount){
    transactionLines.add(String.format("%1$s %2$-15s %3$s %4$012.2f", code, username, userType, amount));
  }

  public static void refundTransactionLine(String buyer, String seller, double amount){
    transactionLines.add(String.format("05 %1$-15s %2$-15s %3$012.2f", buyer, seller, amount));
  }

  public static void buyTransactionLine(Event ev){
    transactionLines.add(String.format("04 %s", ev.getTicketEntry()));
  }

  public static void sellerTransactionLine(Event ev){
    transactionLines.add(String.format("03 %s", ev.getTicketEntry()));
  }
  
  public static User logout(User user) {
    PrintWriter out;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(transactionsFile, true)));
    } catch (IOException e) {
      System.out.println("Error logging out");
      return user;
    }
    transactionLines.add(user.endSession());
    for (String line : transactionLines) {
      out.println(line);  
    }
    out.close();
    transactionLines = new ArrayList<>();
    return null;
  }
}