import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * Description
 *
 * @version 1.0 from 5/12/2020
 * @author 
 */

public class Onlineshop_Client extends Client {
  private ClientGuiCommandBased mygui;
  public Onlineshop_Client(ClientGuiCommandBased pgui){
    super("192.168.178.24",80);
    this.mygui = pgui;
    }
  
  public void processMessage(String pMessage){
    //this could be automized but my time is precious
    this.mygui.print(pMessage);
    }         
} // end of Onlineshop_Client

