/**
 *
 * Description
 *
 * @version 1.0 from 5/14/2020
 * @author 
 */

public class Lager {
  
  // start attributes
  private List<Artikel> myitems;
  private int artikelnummercounter = 0;
  // end attributes
  
  public Lager() {
    this.myitems = null;
  }

  // start methods
  public List<Artikel> getMyitems() {
    return myitems;
  }

  public void setMyitems(List<Artikel> myitemsNew) {
    myitems = myitemsNew;
  }
  
  public List<Artikel> searchitem(String pname){
    this.myitems.toFirst();
    List<Artikel> temp = new List<Artikel>();
    while (this.myitems.hasAccess()) { 
      if (this.myitems.getContent().getName().toLowerCase().equals(pname.toLowerCase())) {
        temp.append(myitems.getContent());
      } // end of if
      this.myitems.next();
    } // end of while
    return temp;
    }
  
  public void additem(String pname, int ppreis, String Beschreibung, String Hersteller) {
    Artikel temp = new Artikel(pname,artikelnummercounter,ppreis,Beschreibung,Hersteller);
    artikelnummercounter++;
    this.myitems.append(temp);
  }
  
  public void sort(){
    List<Artikel> temp = new List<Artikel>();
    while (!myitems.isEmpty()) { 
      myitems.toFirst();
      Artikel max = myitems.getContent();
      while (myitems.hasAccess()) { 
        if (myitems.getContent().getverkaufszahl()>max.getverkaufszahl()) {
          max = myitems.getContent();
        } // end of if
        myitems.next();
      } // end of while
      temp.append(max);
      myitems.toFirst();
      while (myitems.hasAccess()) { 
        if (myitems.getContent()==max) {
          myitems.remove();
        } else {
           myitems.next();  
          } // end of if-else
      } // end of while
    } // end of while
    this.myitems.concat(temp);
    }
  
  public List<Artikel> nmostpopular(int n){
    List<Artikel> temp = new List<Artikel>();
    myitems.toFirst();
    int k = 0;
    while (myitems.hasAccess()&&k<n) { 
      temp.append(myitems.getContent());
      myitems.next();
      k++;
    } // end of while
    return temp;
    }
  // end methods
} // end of Lager

