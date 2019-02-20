import java.util.ArrayList;

public class User implements Commands {
  private String username;
  private double credit;
  private String userType;

  public User(String username, String usertype, double credit) {
    this.username = username;
    this.userType = usertype;
    this.credit = credit;
  }

  public String getUsername() {
    return this.username;
  }

  public String getUserType() {
    return this.userType;
  }

  public Double getCredit() {
    return this.credit;
  }

  public String endSession() {
    return String.format("00 %1$-15s %2$s %3$012.2f", this.username, this.userType, this.credit);
  }

  public String getUserAccount() {
    return String.format("%1$-15s %2$s %3$012.2f", this.username, this.userType, this.credit);
  }

  @Override
  public ArrayList<User> create(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public ArrayList<User> delete(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public ArrayList<User> buy(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public ArrayList<User> refund(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public ArrayList<User> addCredit(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n  But they isn't any";
  }

}