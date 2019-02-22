public class Event{
  
  private String name;
  private String seller;
  private double price;
  private int quantity;

  /**
   * Constructor for the event
   * @param name: Name of the event
   * @param seller: Seller of the event
   * @param price: Price of the event
   * @param quantity: Quantity of the event
   */
  public Event(String name, String seller, double price, int quantity){
    this.name = name;
    this.seller = seller;
    this.price = price;
    this.quantity = quantity;
  }

  /**
   * Retrieves the name of this event
   * @return: The name of this event
   */
  public String getName(){
    return this.name;
  }

  @Override
  public boolean equals(Object other){
    if(other instanceof Event){
      Event o = (Event)  other;
      if(this.name.equals(o.name) && this.seller.equals(o.seller)){
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * Retrieves the seller username of this event
   * @return: The seller username of this event
   */
  public String getSeller(){
    return this.seller;
  }

  /**
   * Retrieves the price of this event
   * @return: The price of this event
   */
  public double getPrice(){
    return this.price;
  }

  /**
   * Retrieves the quantity of tickets available for this event
   * @return: The remaining quanitity
   */
  public int getQuantity(){
    return this.quantity;
  }

  /**
   * Increments the quantity of tickets for this event
   * @param q: The quanity to add
   */
  public void addQuantity(int q){
    this.quantity += q;
  }

  /**
   * Decrements the by the specified amount it enough tickets exist
   * @param q: The amount to decrement by
   */
  public void sell(int q){
    if((quantity - q) >= 0){
      this.quantity -= q;
    }
  }

  /**
   * Gets the ticket entry for the available tickets file
   * @return: The string representation for this event
   */
  public String getTicketEntry(){
    return String.format("%1$-19s %2$-13s %3$03d %4$06.2f", this.name, this.seller, this.quantity, this.price);
  }
  
}