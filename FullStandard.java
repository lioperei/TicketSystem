public class FullStandard extends User {

  /**
   * Constructor for the full standard user
   * @param username: Specified username
   * @param credit: Specified credit amount
   */
  public FullStandard(String username, double credit){
    super(username, "FS", credit);
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n\tBuy\n\tSell\n\tRefund\n\tAdd Credit\n\tLogout";
  }

  @Override
  public boolean refund(){
    return true;
  }

  @Override
  public boolean buy(){
    return true;
  }


  @Override
  public boolean sell(){
    return true;
  }
}