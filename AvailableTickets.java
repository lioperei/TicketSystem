import java.io.*;
import java.util.ArrayList;

public class AvailableTickets {
  private static String ticketsFile = "available_tickets.txt";
  private static ArrayList<Event> tickets = new ArrayList<>();
  
  private static final int NAME = 18;
  private static final int SELLER = 34;
  private static final int QUANTITY = 37;
  private static final int PRICE = 38;

  /**
   * Initializes the available tickets and returns a sucess status
   * @return Success status
   */
  public static boolean login(){
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(ticketsFile)));
      String event, name, seller;
      int quantity;
      double price;
      while (!(event = br.readLine()).equals("END")) {
        name = event.substring(0, NAME);
        seller = event.substring(NAME + 1, SELLER);
        quantity = Integer.parseInt(event.substring(SELLER + 1, QUANTITY));
        price = Double.parseDouble(event.substring(PRICE));
        tickets.add(new Event(name, seller, price, quantity));
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Unable to read tickets");
      return false;
    }
    return true;
  }

  /**
   * Saves the available tickets file
   */
  public static void logout() {
    PrintWriter out;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(ticketsFile, false)));
    } catch (IOException e) {
      System.out.println("Error logging out");
      return;
    }
    for (Event e : tickets) {
      out.println(e.getTicketEntry());
    }
    out.println("END");
    tickets = new ArrayList<>();
    out.close();
  }

  /**
   * Adds the events to the list of tickets
   * @param e: Event to be added
   */
  public static void addEvent(Event e){
    tickets.add(e);
  }

  /**
   * Retrieves and returns an event by sellername and title
   * @param sellername: Name of seller
   * @param title: title of event
   * @return: Event is found, null if not
   */
  public static Event getEvent(String sellername, String title){
    for (Event e : tickets) {
      if(e.getName().equals(title) && e.getSeller().equals(sellername)){
        return e;
      }
    }
    return null;
  }

  /**
   * Decreases the quantity of the event by the specified amount
   * @param ev: Event to be decreased
   * @param q: quantity to be decreases
   */
  public static void sellTicket(Event ev, int q){
  for (Event e : tickets) {
      if(e.equals(ev)){
        e.sell(q);
        return;
      }
    }
  }
}