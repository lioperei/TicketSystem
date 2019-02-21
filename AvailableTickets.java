import java.io.*;
import java.util.ArrayList;

public class AvailableTickets {
  private static String ticketsFile = "available_tickets.txt";
  private static ArrayList<Event> tickets = new ArrayList<>();
  
  private static final int NAME = 0;
  private static final int SELLER = 1;
  private static final int QUANTITY = 2;
  private static final int PRICE = 3;

  public static boolean login(){
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(ticketsFile)));
      String[] ticketLines;
      String event;
      while (!(event = br.readLine()).equals("END")) {
        ticketLines = event.split("\\s+");
        tickets.add(new Event(ticketLines[NAME], ticketLines[SELLER], Double.parseDouble(ticketLines[PRICE]), Integer.parseInt(ticketLines[QUANTITY])));
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Unable to read tickets");
      return false;
    }
    return true;
  }

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

  public static void addEvent(Event e){
    tickets.add(e);
  }

  public static Event getEvent(String sellername, String title){
    for (Event e : tickets) {
      if(e.getName().equals(title) && e.getSeller().equals(sellername)){
        return e;
      }
    }
    return null;
  }

  public static void sellTicket(Event ev, int q){
    for (Event e : tickets) {
      if(e.equals(ev)){
        e.addQuantity(-q);
      }
    }
  }
}