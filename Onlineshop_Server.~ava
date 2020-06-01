/**
 *
 * Description
 *
 * @version 1.0 from 5/12/2020
 * @author JUSTUS E. STAMM && LASER OEZMEN
 */

public class Onlineshop_Server extends Server {
  private Userbank userbank;
  private Lager myitems;
  private Bestellungen myorders;
  private List<User> onlineusers;
  private InterfaceForServer mygui;
  public Onlineshop_Server(InterfaceForServer pgui){
    super(80);
    userbank = new Userbank();
    myitems = new Lager();
    myorders = new Bestellungen();
    onlineusers = new List<User>();
    this.mygui = pgui;
  }
  
  public static void main(String[] args){
    new Onlineshop_Server(null);
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
        {
          if (temp.getMyaccount()!=null) {
            List<Artikel> plis = temp.getMyaccount().getBasket();
            plis.toFirst();
            while (plis.hasAccess()) { 
              if(plis.getContent()!=null) this.myorders.bestellen(plis.getContent(),temp.getMyaccount());
              plis.next();
            } // end of while
            temp.getMyaccount().clearbasket();
            this.send(pClientIP,pClientPort,"!LOGOUT&BUY:SUCCESS");
            this.closeConnection(pClientIP,pClientPort);
          } else {
            this.send(pClientIP,pClientPort,"!LOGOUT&BUY:SUCCESS");
            this.closeConnection(pClientIP,pClientPort);  
          } // end of if-else
        }
        break;
      case "?CLEARBASKET" :
        if (temp.getMyaccount()!=null) {
          temp.getMyaccount().clearbasket();
          this.send(pClientIP,pClientPort,"!CLEARBASKET");
        } // end of if
        break;
      case "?HISTORY" :
        {
          if (temp.getMyaccount()!=null) {
            String back = "HISTORY:"+this.Bestellformat(this.myorders.allordersfromaccount(temp.getMyaccount()));
            this.send(pClientIP,pClientPort,back);
          } else {
            this.send(pClientIP,pClientPort,"FAILURE");
          } // end of if-else  
        }
        break;
      case "?NOTIFICATIONS" :
        {
          if(temp.getMyaccount()!=null){
            String back = this.Notificationformat(temp.getMyaccount().getNotifications());
            this.send(pClientIP,pClientPort,back);
          }
        }
        break;
      case "?DELETENOTIFICATIONS" :
        if (temp.getMyaccount()!=null) {
          temp.getMyaccount().deletenotifications();
          this.send(pClientIP,pClientPort,"!DELETENOTIFICATIONS");
        } else {
          this.send(pClientIP,pClientPort,"FAILURE");
        } // end of if-else
        break;
      case "?WISHLIST" :
        {
          if (temp.getMyaccount()!=null) {
            String back = "!WISHLIST:" + this.Artikelformat(temp.getMyaccount().getWishlist());
            this.send(pClientIP,pClientPort,back);    
          } else {
            this.send(pClientIP,pClientPort,"FAILURE");
          } // end of if-else
        }
        break;
      case "?ADDTOWISHLIST" :
        {
          if (temp.getMyaccount()!=null) {
            Artikel partikel = myitems.searchitemtbynumber(Integer.parseInt(input[1]));
            temp.getMyaccount().addtowishlist(partikel);
            if (partikel!=null) {
              this.send(pClientIP,pClientPort,"!ADDTOWISHLIST:SUCCESS:"+input[1]);
            } else {
              this.send(pClientIP,pClientPort,"!ADDTOWISHLIST:FAILURE:"+input[1]);
            } // end of if-else 
          } else {
            this.send(pClientIP,pClientPort,"FAILURE");  
          } // end of if-else
        }
        break;
      case "?REMOVEFROMWISHLIST" :
        {
          if (temp.getMyaccount()!=null) {
            temp.getMyaccount().removefromwishlist(Integer.parseInt(input[1]));
            this.send(pClientIP,pClientPort,"!REMOVEFROMWISHLIST:SUCCESS:"+input[1]);
          } else {
            this.send(pClientIP,pClientPort,"FAILURE");
          } // end of if-else  
        }
        break;
      case "?CLEARWISHLIST" :
        if (temp.getMyaccount()!=null) {
          this.send(pClientIP,pClientPort,"!CLEARWISHLIST");
          temp.getMyaccount().clearwishlist();
        } else {
          this.send(pClientIP,pClientPort,"FAILURE");  
        } // end of if-else
        break;
      case "?RECOMMEND" :
        {
        List<Artikel> recommended = this.myitems.nmostpopular(Integer.parseInt(input[1]));
          String back ="!RECOMMENDED:" +this.Artikelformat(recommended); 
          this.send(pClientIP,pClientPort,back);
        }
        break;
      case "?ARTIKEL" :
        {
        Artikel item = this.myitems.searchitemtbynumber(Integer.parseInt(input[1]));
          List<Artikel> val = new List<Artikel>();
          val.append(item);
          String back = this.Artikelformat(val);
          this.send(pClientIP,pClientPort,back);
                  }
        break;
      default:
        this.send(pClientIP,pClientPort,"-ERR");
        break;
    }
  }
  
  public void processClosingConnection(String pClientIP, int pClientPort){
    this.onlineusers.toFirst();
    while (onlineusers.hasAccess()) { 
      if (onlineusers.getContent().getIp().equals(pClientIP)&&onlineusers.getContent().getPort()==pClientPort) {
        this.onlineusers.remove();
      } else {
        this.onlineusers.next();
      } // end of if-elsef
    } // end of while
    this.send(pClientIP,pClientPort,"CONNECTION_TERMINATED");
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
  
  public String Bestellformat (List<Bestellung> plis){
    plis.toFirst();
    String basket = "";
    while (plis.hasAccess()) { 
      basket = basket + "!BESTELLUNG:"+ plis.getContent().getArtikel().getArtikelnummer() + "," + plis.getContent().getArtikel().getName()+","+plis.getContent().getArtikel().getBeschreibung()+","+plis.getContent().getArtikel().getPreis()+","+plis.getContent().getArtikel().getHersteller()+":"+plis.getContent().getBestellungsnummer()+":";
      plis.next();
    } // end of while
    return basket;
  }
  
  public String Notificationformat(List<String> plis){
    plis.toFirst();
    String notifications = "";
    while (plis.hasAccess()) { 
      notifications = notifications + "!NOTIFICATION:"+ plis.getContent() + ":";
      plis.next();
    } // end of while
    return notifications;
  }

  public void addproduct(String name, String beschreibung, String hersteller, int preis){
    this.myitems.additem(name,preis,beschreibung,hersteller);
    }
  
  public void notifyall(){
    
   }
} // end of Onlineshop_Server

