import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * Description
 *
 * @version 1.0 from 6/1/2020
 * @author 
 */

public class InterfaceForServer extends JFrame {
  // start attributes
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  private JTextField jTextField3 = new JTextField();
  private JButton jButton1 = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JButton jButton2 = new JButton();
  private Onlineshop_Server myserver;
  private JNumberField jNumberField1 = new JNumberField();
  // end attributes
  
  public InterfaceForServer() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 475; 
    int frameHeight = 365;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("InterfaceForServer");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
    
    jTextField1.setBounds(8, 8, 254, 28);
    cp.add(jTextField1);
    jTextField2.setBounds(8, 40, 254, 28);
    cp.add(jTextField2);
    jTextField3.setBounds(8, 72, 254, 28);
    cp.add(jTextField3);
    jButton1.setBounds(8, 136, 251, 33);
    jButton1.setText("Erzeugen");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton1_ActionPerformed(evt);
      }
    });
    cp.add(jButton1);
    jLabel1.setBounds(264, 8, 110, 20);
    jLabel1.setText("Name");
    cp.add(jLabel1);
    jLabel2.setBounds(264, 40, 110, 20);
    jLabel2.setText("Beschreibung");
    cp.add(jLabel2);
    jLabel3.setBounds(264, 72, 110, 20);
    jLabel3.setText("Hersteller");
    cp.add(jLabel3);
    jLabel4.setBounds(264, 104, 110, 20);
    jLabel4.setText("Preis");
    cp.add(jLabel4);
    jButton2.setBounds(8, 248, 251, 73);
    jButton2.setText("Close");
    jButton2.setMargin(new Insets(2, 2, 2, 2));
    jButton2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton2_ActionPerformed(evt);
      }
    });
    cp.add(jButton2);
    jNumberField1.setBounds(8, 104, 251, 28);
    jNumberField1.setText("");
    cp.add(jNumberField1);
    // end components
    this.myserver = new Onlineshop_Server(this);
    setVisible(true);
  } // end of public InterfaceForServer
  
  // start methods
  
  public static void main(String[] args) {
    new InterfaceForServer();
  } // end of main
  //String pname, int ppreis, String Beschreibung, String Hersteller
  public void jButton1_ActionPerformed(ActionEvent evt) {
    this.myserver.addproduct(jTextField1.getText(),jTextField2.getText(),jTextField3.getText(),jNumberField1.getInt());
    jTextField1.setText("");
    jTextField2.setText("");
    jTextField3.setText("");
    jNumberField1.clear();
    //String name, String beschreibung, String hersteller, int preis
  } // end of jButton1_ActionPerformed

  public void jButton2_ActionPerformed(ActionEvent evt) {
    this.myserver.close();
  } // end of jButton2_ActionPerformed

  // end methods
} // end of class InterfaceForServer

