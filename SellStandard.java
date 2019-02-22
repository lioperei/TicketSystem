public class SellStandard extends User {

  /**
   * Constructor for the SellStandard user
   * @param username: Specified username for this user
   * @param credit: Specified credit amount for this user
   */
  public SellStandard(String username, double credit){
    super(username, "SS", credit);
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n\tSell\n\tRefund\n\tAdd Credit\n\tLogout";
  }

  @Override
  public boolean refund(){
    return true;
  }

  @Override
  public boolean sell(){
    return true;
  }

}