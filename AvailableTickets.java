import java.io.*;
import java.util.ArrayList;

public class AvailableTickets {
  private String ticketsFile;
  private ArrayList<Event> tickets;
  
  private static final int NAME = 18;
  private static final int SELLER = 34;
  private static final int QUANTITY = 37;
  private static final int PRICE = 38;

  public AvailableTickets(String ticketsFile){
    this.ticketsFile = ticketsFile;
    tickets  = new ArrayList<>();
  }


  /**
   * Initializes the available tickets and returns a sucess status
   * @return Success status
   */
  public boolean login(){
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(this.ticketsFile)));
      String event, name, seller;
      int quantity;
      double price;
      while (!(event = br.readLine()).equals("END")) {
        System.out.println(event);
        name = event.substring(0, NAME);
        seller = event.substring(NAME + 1, SELLER);
        quantity = Integer.parseInt(event.substring(SELLER + 1, QUANTITY));
        price = Double.parseDouble(event.substring(PRICE));
        this.tickets.add(new Event(name, seller, price, quantity));
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
  public void logout() {
    PrintWriter out;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(this.ticketsFile, false)));
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
  public void addEvent(Event e){
    this.tickets.add(e);
  }

  /**
   * Retrieves and returns an event by sellername and title
   * @param sellername: Name of seller
   * @param title: title of event
   * @return: Event is found, null if not
   */
  public Event getEvent(String sellername, String title){
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
  public void sellTicket(Event ev, int q){
  for (Event e : tickets) {
      if(e.equals(ev)){
        e.sell(q);
        return;
      }
    }
  }
}