/**
 *
 * Description
 *
 * @version 1.0 from 5/14/2020
 * @author 
 */

public class Userbank {
  
  // start attributes
  private List<Account> users;
  // end attributes
  
  public Userbank() {
    this.users = new List<Account>();
  }

  // start methods
  public List<Account> getUsers() {
    return users;
  }
  
  public void adduser(String name, String passwort, String email){
    Account temp = new Account(name,passwort,email);
    if (this.searchbyname(name)==null) {
      this.users.append(temp);
    } // end of if
    }
  
  public Account searchbyname(String pname){
    users.toFirst();
    while (users.hasAccess()) { 
      if (users.getContent().getName().equals(pname)) {
        return users.getContent();
      } // end of if
      this.users.next();
    } // end of while
    return null;
    }
} // end of Userbank

