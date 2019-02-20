public class BuyStandard extends User {

  public BuyStandard(String username, double credit){
    super(username, "BS", credit);
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n\tBuy\n\tAdd Credit\n\tLogout";
  }

}