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
  private JTextArea jTextArea1 = new JTextArea("");
    private JScrollPane jTextArea1ScrollPane = new JScrollPane(jTextArea1);
  private JLabel jLabel5 = new JLabel();
  private JTextArea jTextArea2 = new JTextArea("");
    private JScrollPane jTextArea2ScrollPane = new JScrollPane(jTextArea2);
  private JLabel jLabel6 = new JLabel();
  private JButton jButton3 = new JButton();
  // end attributes
  
  public InterfaceForServer() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 979; 
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
    jTextArea1ScrollPane.setBounds(392, 32, 280, 284);
    cp.add(jTextArea1ScrollPane);
    jLabel5.setBounds(392, 8, 110, 20);
    jLabel5.setText("Artikel");
    cp.add(jLabel5);
    jTextArea2ScrollPane.setBounds(680, 32, 280, 284);
    cp.add(jTextArea2ScrollPane);
    jLabel6.setBounds(680, 8, 110, 20);
    jLabel6.setText("Accounts");
    cp.add(jLabel6);
    jButton3.setBounds(272, 248, 107, 73);
    jButton3.setText("refresh");
    jButton3.setMargin(new Insets(2, 2, 2, 2));
    jButton3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton3_ActionPerformed(evt);
      }
    });
    cp.add(jButton3);
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
  
  public void jButton3_ActionPerformed(ActionEvent evt) {
    jTextArea1.setText("");
    jTextArea2.setText("");
    jTextArea1.setText(this.myserver.allaccounts());
    jTextArea2.setText(this.myserver.allarticles());
  } // end of jButton3_ActionPerformed

  // end methods
} // end of class InterfaceForServer

