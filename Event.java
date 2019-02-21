public class Event{
  
  private String name;
  private String seller;
  private double price;
  private int quantity;

  public Event(String name, String seller, double price, int quantity){
    this.name = name;
    this.seller = seller;
    this.price = price;
    this.quantity = quantity;
  }

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

  public String getSeller(){
    return this.seller;
  }

  public double getPrice(){
    return this.price;
  }

  public int getQuantity(){
    return this.quantity;
  }

  public void addQuantity(int q){
    this.quantity += q;
  }

  public String getTicketEntry(){
    return String.format("%1$-25s %2$-15s %3$03d %4$09.2f", this.name, this.seller, this.quantity, this.price);
  }
  
}