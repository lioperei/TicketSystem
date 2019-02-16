public class User{
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

  public String getUserAccount(){
    return String.format("%1$-15s %2$s %3$012.2f", this.username, this.userType, this.credit);
  }

}