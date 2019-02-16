import java.util.ArrayList;

public interface Commands {
  public ArrayList<User> create(ArrayList<User> users);
  public ArrayList<User> delete(ArrayList<User> users);
  public ArrayList<User> buy(ArrayList<User> users);
  public ArrayList<User> refund(ArrayList<User> users);
  public ArrayList<User> addCredit(ArrayList<User> users);
  public String getCommands();
}