/**
 *
 * Description
 *
 * @version 1.0 from 5/14/2020
 * @author 
 */

public class Bestellungen {
  
  // start attributes
  private List<Bestellung> bestellungen;
  private int bestellungsnummecounter = 0;
  // end attributes
  
  // start methods
  public Bestellungen (){
    bestellungen = new List<Bestellung>();
    }
  
  public List<Bestellung> getBestellungen() {
    return bestellungen;
  }

  public void setBestellungen(List<Bestellung> bestellungenNew) {
    bestellungen = bestellungenNew;
  }

  public int getBesttellungsnummecounter() {
    return bestellungsnummecounter;
  }
  
  public void bestellen(Artikel partikel, Account paccount){
    Bestellung temp = new Bestellung(partikel,bestellungsnummecounter,paccount);
    bestellungsnummecounter++;
    bestellungen.append(temp);
    }
  
  public List<Bestellung> allordersfromaccount(Account pacc){
    this.bestellungen.toFirst();
    List<Bestellung> temp = new List<Bestellung>();
    while (this.bestellungen.hasAccess()) { 
      if (this.bestellungen.getContent().getKäufer()==pacc) {
        temp.append(this.bestellungen.getContent());
      } // end of if
      this.bestellungen.next();
    } // end of while
    return temp;
  }

  // end methods
} // end of Bestellungen

