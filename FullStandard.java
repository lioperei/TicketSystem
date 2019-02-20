public class FullStandard extends User {

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
}