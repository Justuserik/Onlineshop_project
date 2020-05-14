/**
 *
 * Description
 *
 * @version 1.0 from 5/14/2020
 * @author 
 */

public class Bestellung {
  
  // start attributes
  private Artikel artikel;
  private int Bestellungsnummer;
  private Account Käufer;
  // end attributes
  
  public Bestellung(Artikel artikel, int Bestellungsnummer, Account Käufer) {
    this.artikel = artikel;
    this.Bestellungsnummer = Bestellungsnummer;
    this.Käufer = Käufer;
  }

  // start methods
  public Artikel getArtikel() {
    return artikel;
  }

  public int getBestellungsnummer() {
    return Bestellungsnummer;
  }

  public Account getKäufer() {
    return Käufer;
  }

  // end methods
} // end of Bestellung

