import java.util.ArrayList;

public class User implements Commands {
  private final double MAX = 999999;
  private final double SESSION_MAX = 1000;

  private String username;
  private double credit;
  private String userType;
  private double sessionAmount;

  public User(String username, String usertype, double credit) {
    this.username = username;
    this.userType = usertype;
    this.credit = credit;
    this.sessionAmount = 0;
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
  public boolean create() {
    return false;
  }

  @Override
  public boolean delete() {
    return false;
  }

  @Override
  public ArrayList<User> buy(ArrayList<User> users) {
    System.out.println("Invalid command");
    return users;
  }

  @Override
  public boolean refund() {
    return false;
  }

  @Override
  public String getCommands() {
    return "Enter Command:\n  But they isn't any";
  }

  public boolean addCredit(double amount, boolean refund) {
    if ((this.credit + amount) < MAX) {
      if (refund) {
        this.credit += amount;
      } else if ((this.sessionAmount + amount) < SESSION_MAX) {
        this.credit += amount;
        this.sessionAmount += amount;
      }
      TransactionFile.userTransactionLine("06", this.username, this.userType, amount);
      return true;
    } else {
      System.out.println("Amount exceeds limit");
      return false;
    }
  }

  public boolean deductCredit(double amount) {
    if ((this.credit - amount) < 0) {
      this.credit -= amount;
      return true;
    } else {
      return false;
    }
  }

  public String toString() {
    return this.username + " " + this.userType;
  }
}