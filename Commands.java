/**
 * Interface for the commands that all users must implement
 */
public interface Commands {
  public boolean create();
  public boolean delete();
  public boolean buy();
  public boolean sell();
  public boolean refund();
  public String getCommands();
}