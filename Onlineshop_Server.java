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
        {
          if (temp.getMyaccount()==null) {
            Account foo = this.userbank.searchbyname(input[1]);
            if (foo!=null) {
              this.removeuserswithaccount(foo);
              foo.clearbasket();
              if (foo.getPasswort().equals(input[2])) {
                temp.setMyaccount(foo);
                this.send(pClientIP,pClientPort,"!LOGGEDIN");
              } else {
                this.send(pClientIP,pClientPort,"!LOGINERROR");  
              } // end of if-else
            } else {
              this.send(pClientIP,pClientPort,"!LOGINERROR");
            } // end of if-else
          } else {
            this.send(pClientIP,pClientPort,"!LOGGEDIN");
          } // end of if-else 
        }
        break;
      case "?BASKET" :
        {
        
          if (temp.getMyaccount()!=null) {
            String back = "!BASKET"+this.Artikelformat(temp.getMyaccount().getBasket());
            this.send(pClientIP,pClientPort,back);
          } else {
            this.send(pClientIP,pClientPort,"FAILURE");
          } // end of if-else
        }
        break;
      case "?SEARCH" :
        {
        String back = "!RESULTS" + this.Artikelformat(this.myitems.searchitem(input[1])); 
          this.send(pClientIP,pClientPort,back); 
        }
        break;
      case "?ADDTOBASKET" :
        {  
          if (temp.getMyaccount()!=null) {
            Artikel partikel = myitems.searchitemtbynumber(Integer.parseInt(input[1]));
            temp.getMyaccount().addtobasket(partikel);
            if (partikel!=null) {
              this.send(pClientIP,pClientPort,"!ADDTOBASKET:SUCCESS:"+input[1]);
            } else {
              this.send(pClientIP,pClientPort,"!ADDTOBASKET:FALURE:"+input[1]);  
            } // end of if-else
          } else {
            this.send(pClientIP,pClientPort,"!ADDTOBASKET:FALURE:"+input[1]);   
          } // end of if-else
        }
        break;
      case "?REMOVEFROMBASKET" :
        { 
          if (temp.getMyaccount()!=null) {
            temp.getMyaccount().removefrombasket(Integer.parseInt(input[1]));
            this.send(pClientIP,pClientPort,"!REMOVEFROMBASKET:SUCCESS:"+input[1]);
          } else {
            this.send(pClientIP,pClientPort,"!REMOVEFROMBASKET:FAILURE:"+input[1]);
          } // end of if-else
        }
        break;
      case "?NEWACCOUNT" :
        {
          if (temp.getMyaccount()==null) {
            userbank.adduser(input[1],input[2],input[3]);
            Account foo = this.userbank.searchbyname(input[1]);
            if (foo!=null) {
              this.removeuserswithaccount(foo);
              foo.clearbasket();
              if (foo.getPasswort().equals(input[2])) {
                temp.setMyaccount(foo);
                this.send(pClientIP,pClientPort,"!NEWACCOUNT:SUCCESS:"+input[1]);
              } else {
                this.send(pClientIP,pClientPort,"!NEWACCOUNT:FAILURE:"+input[1]);  
              } // end of if-else
            } else {
              this.send(pClientIP,pClientPort,"!NEWACCOUNT:FAILURE:"+input[1]);
            } // end of if-else
          } else {
            this.send(pClientIP,pClientPort,"!NEWACCOUNT:FAILURE:"+input[1]);
          } // end of if-else
        }
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
      default:
        this.send(pClientIP,pClientPort,"-ERR");
        break;
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
  
  public void removeuserswithaccount(Account pacc){
    this.onlineusers.toFirst();
    while (this.onlineusers.hasAccess()) { 
      if (this.onlineusers.getContent().getMyaccount()==pacc) {
        onlineusers.remove();
      } else {
        onlineusers.next();
      } // end of if-else
    } // end of while
  }
  
  public String Artikelformat (List<Artikel> plis){
    plis.toFirst();
    String basket = "";
    while (plis.hasAccess()) { 
      basket = basket + "!ARTIKEL:"+plis.getContent().getArtikelnummer() + "," + plis.getContent().getName()+","+plis.getContent().getBeschreibung()+","+plis.getContent().getPreis()+","+plis.getContent().getHersteller()+":";
      plis.next();
    } // end of while
    return basket;
  }
} // end of Onlineshop_Server

