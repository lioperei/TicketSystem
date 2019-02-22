import java.io.*;
import java.util.ArrayList;
import  java.time.LocalDate;

/**
 * Handles the interaction with the tranaction file
 */
public class TransactionFile{
  // List of transaction strings that will be written to the transactions file
  private static String transactionsFile = String.format("%s_transaction.txt", LocalDate.now());
  private static ArrayList<String> transactionLines = new ArrayList<>();

  /**
   * Add transaction line to the file
   * @param line: Line to be added
   */
  public static void addTransaction(String line){
    transactionLines.add(line);
  }

  /**
   * Adds a user transactions line for create, delete, addcredit and end of session
   * @param code: Code of line
   * @param username: Username of user
   * @param userType: Uesr type of user
   * @param amount: Credit amount
   */
  public static void userTransactionLine(String code, String username, String userType, double amount){
    transactionLines.add(String.format("%1$s %2$-15s %3$s %4$012.2f", code, username, userType, amount));
  }

  /**
   * Adds a refund line to the file
   * @param buyer: Buyer username for this line
   * @param seller: Seller username for this line
   * @param amount: Refund amount
   */
  public static void refundTransactionLine(String buyer, String seller, double amount){
    transactionLines.add(String.format("05 %1$-15s %2$-15s %3$012.2f", buyer, seller, amount));
  }

  /**
   * Adds the transaction line when tickets are bought
   * @param ev: Event where ticekts were bought
   */
  public static void buyTransactionLine(Event ev){
    transactionLines.add(String.format("04 %s", ev.getTicketEntry()));
  }

  /**
   * Adds the transaction line when tickets are sold
   * @param ev: Event where ticekts were sold
   */
  public static void sellerTransactionLine(Event ev){
    transactionLines.add(String.format("03 %s", ev.getTicketEntry()));
  }
  
  /**
   * On logout writes to the transaction file and resets list of transactions
   * @param user: User that is logging out
   * @return: User if logging out was unsuccessful, null otherwise
   */
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