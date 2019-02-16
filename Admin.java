import java.util.ArrayList;

public class Admin extends User implements Commands{

  public Admin(String username, double credit){
    super(username, "AA", credit);
  }

  @Override
  public ArrayList<User> create(ArrayList<User> users) {
    return null;
  }

  @Override
  public ArrayList<User> delete(ArrayList<User> users) {
    return null;
  }

  @Override
  public ArrayList<User> buy(ArrayList<User> users) {
    return null;
  }

  @Override
  public ArrayList<User> refund(ArrayList<User> users) {
    return null;
  }

  @Override
  public ArrayList<User> addCredit(ArrayList<User> users) {
    return null;
  }

  @Override
  public String getCommands() {
    return null;
  }

}