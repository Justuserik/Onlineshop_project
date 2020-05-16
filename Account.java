/**
 *
 * Description
 *
 * @version 1.0 from 5/14/2020
 * @author 
 */

public class Account {
  
  // start attributes
  private String Name;
  private String Passwort;
  private List<Artikel> Basket;
  private String Email;
  private List<Artikel> Wishlist;
  private List<String> Notifications;
  // end attributes
  
  public Account(String Name, String Passwort, String Email) {
    this.Name = Name;
    this.Passwort = Passwort;
    this.Email = Email;
    this.Basket = new List<Artikel>();
    this.Wishlist = new List<Artikel>();
    this.Notifications = new List<String>();
  }

  // start methods
  public String getName() {
    return Name;
  }

  public void setName(String NameNew) {
    Name = NameNew;
  }

  public String getPasswort() {
    return Passwort;
  }

  public void setPasswort(String PasswortNew) {
    Passwort = PasswortNew;
  }

  public List<Artikel> getBasket() {
    return null;
  }

  public void setBasket(List<Artikel> BasketNew) {
    Basket = BasketNew;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String EmailNew) {
    Email = EmailNew;
  }
  
  public void clearbasket() {
    this.Basket = new List<Artikel>();
  }
  
  public List<Artikel> getWishlist() {
    return Wishlist;
  }
  
  public void clearwishlist() {
    this.Wishlist = new List<Artikel>();
  } 
  
  public void addtobasket(Artikel partikel){
    if (partikel!=null) {
      this.Basket.append(partikel);
    } // end of if
    }
  
  public void addtowishlist(Artikel partikel){
    if (partikel!=null) {
      this.Wishlist.append(partikel);
    } // end of if
    }
  
  public void removefrombasket(int partikelnummer) {
    this.Basket.toFirst();
    while (this.Basket.hasAccess()) { 
      if (this.Basket.getContent().getArtikelnummer()==partikelnummer) {
        this.Basket.remove();
      } else {
        this.Basket.next();
        } // end of if-else
    } // end of while
  }
  
  public void removefromwishlist(int partikelnummer) {
    this.Wishlist.toFirst();
    while (this.Wishlist.hasAccess()) { 
      if (this.Wishlist.getContent().getArtikelnummer()==partikelnummer) {
        this.Wishlist.remove();
      } else {
        this.Wishlist.next();  
        } // end of if-else
    } // end of while
  }
  
  public void deletenotifications (){
    this.Notifications = new List<String>();
    }
  
  public void notify (String pstring){
    if(pstring!=null)this.Notifications.append(pstring);
    }
  
  public List<String> getNotifications(){
    return this.Notifications;
    }
  // end methods
} // end of Account

