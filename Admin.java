public class Admin extends User {

  public Admin(String username, double credit){
    super(username, "AA", credit);
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n\tCreate\n\tDelete\n\tBuy\n\tSell\n\tRefund\n\tAdd Credit\n\tLogout";
  }

  @Override
  public boolean create(){
    return true;
  }

  @Override
  public boolean delete(){
    return true;
  }

  @Override
  public boolean refund(){
    return true;
  }
}