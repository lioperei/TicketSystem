import java.util.ArrayList;

public class Admin extends User {

  public Admin(String username, double credit){
    super(username, "AA", credit);
  }

  public String getCommands() {
    return "Enter Command:\n\tCreate\n\tDelete\n\tBuy\n\tRefund\n\tAdd Credit";
  }

}