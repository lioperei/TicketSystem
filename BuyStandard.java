public class BuyStandard extends User {
  /**
   * Constructor for the BuyStandard user
   * @param username: Specified username
   * @param credit: Specificed credit
   */
  public BuyStandard(String username, double credit){
    super(username, "BS", credit);
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n\tBuy\n\tAdd Credit\n\tLogout";
  }

  @Override
  public boolean buy(){
    return true;
  }
  
}