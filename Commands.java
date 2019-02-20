import java.util.ArrayList;

public interface Commands {
  public boolean create();
  public boolean delete();
  public ArrayList<User> buy(ArrayList<User> users);
  public boolean refund();
  public String getCommands();
}