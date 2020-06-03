import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * Description
 *
 * @version 1.0 from 6/3/2020
 * @author 
 */

public class AnmeldungsGUI extends JFrame {
  // start attributes
  private JLabel jLabel1 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  private JTextField jTextField3 = new JTextField();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private ClientGuiCommandBased mothergui;
  
  // end attributes
  
  public AnmeldungsGUI(ClientGuiCommandBased pgui) { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 300; 
    int frameHeight = 250;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("AnmeldungsGUI");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
    
    jLabel1.setBounds(0, 8, 286, 28);
    jLabel1.setText("Anmeldung");
    cp.add(jLabel1);
    jTextField1.setBounds(5, 42, 270, 20);
    cp.add(jTextField1);
    jTextField2.setBounds(6, 69, 270, 20);
    cp.add(jTextField2);
    jTextField3.setBounds(8, 96, 270, 20);
    cp.add(jTextField3);
    jButton1.setBounds(8, 128, 267, 25);
    jButton1.setText("login");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton1_ActionPerformed(evt);
      }
    });
    cp.add(jButton1);
    jButton2.setBounds(8, 168, 267, 25);
    jButton2.setText("create account");
    jButton2.setMargin(new Insets(2, 2, 2, 2));
    jButton2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton2_ActionPerformed(evt);
      }
    });
    cp.add(jButton2);
    // end components
    mothergui = pgui;
    setVisible(true);
    this.jTextField1.setText("username");
    this.jTextField2.setText("password");
    this.jTextField3.setText("email");
  } // end of public AnmeldungsGUI
  
  // start methods
  
  public static void main(String[] args) {
    new AnmeldungsGUI(null);
  } // end of main
  
  public void jButton1_ActionPerformed(ActionEvent evt) {
    this.mothergui.send("?LOGIN:"+jTextField1.getText()+":"+jTextField2.getText());
  } // end of jButton1_ActionPerformed

  public void jButton2_ActionPerformed(ActionEvent evt) {
    this.mothergui.send("?NEWACCOUNT:"+jTextField1.getText()+":"+jTextField2.getText()+":"+jTextField3.getText());
  } // end of jButton2_ActionPerformed

  // end methods
} // end of class AnmeldungsGUI

