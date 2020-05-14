/**
 *
 * Description
 *
 * @version 1.0 from 5/12/2020
 * @author 
 */

public class Onlineshop_Server extends Server {
  private Userbank userbank;
  private Lager myitems;
  private Bestellungen myorders;
  private List<User> onlineusers;
  public Onlineshop_Server(){
    super(80);
    userbank = new Userbank();
    myitems = new Lager();
    myorders = new Bestellungen();
    onlineusers = new List<User>();
  }
  
  public void processNewConnection(String pClientIP, int pClientPort){
    User temp = new User(pClientIP,pClientPort);
    onlineusers.append(temp);
    this.send(pClientIP,pClientPort,"Hallo Kunde. Sie sind jetzt mit dem Onlineshop verbunden. Bitte melden sie sich nun an oder erstellen einen neuen Account.");
  }
  
  public void processMessage(String pClientIP, int pClientPort, String pMessage){
    String[] input = pMessage.split(":");
    User temp = this.getuserbyIpandPort(pClientIP,pClientPort);
    switch(input[0]){
      case "?LOGIN" :
        break;
      case "?BASKET" :
        break;
      case "?SEARCH" :
        break;
      case "?ADDTOBASKET" :
        break;
      case "?REMOVEFROMBASKET" :
        break;
      case "?NEWACCOUNT" :
        break;
      case "?LOGOUT&BUY" :
        break;
      case "?CLEARBASKET" :
        break;
      case "?HISTORY" :
        break;
      case "?NOTIFICATIONS" :
        break;
      case "?DELETENOTIFICATIONS" :
        break;
      case "?WISHLIST" :
        break;
      case "?ADDTOWISHLIST" :
        break;
      case "?REMOVEFROMWISHLIST" :
        break;
      case "?CLEARWISHLIST" :
        break;
      case "?RECOMMEND" :
        break;
      case "?ARTIKEL" :
        break;
      default: break;
    }
    
    
  }
  
  public void processClosingConnection(String pClientIP, int pClientPort){
    
  }
  
  public User getuserbyIpandPort(String ip, int port){
    this.onlineusers.toFirst();
    while (onlineusers.hasAccess()) { 
      if (onlineusers.getContent().getIp().equals(ip)&&onlineusers.getContent().getPort()==port) {
        return onlineusers.getContent();
      } // end of if
      onlineusers.next();
    } // end of while
    return null;
  }
} // end of Onlineshop_Server

