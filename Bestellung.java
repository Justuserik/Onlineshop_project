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
  private Account K�ufer;
  private String Status;
  // end attributes
  
  public Bestellung(Artikel artikel, int Bestellungsnummer, Account K�ufer) {
    this.artikel = artikel;
    this.Bestellungsnummer = Bestellungsnummer;
    this.K�ufer = K�ufer;
  }

  // start methods
  public Artikel getArtikel() {
    return artikel;
  }

  public int getBestellungsnummer() {
    return Bestellungsnummer;
  }

  public Account getK�ufer() {
    return K�ufer;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String StatusNew) {
    Status = StatusNew;
  }

  // end methods
} // end of Bestellung

