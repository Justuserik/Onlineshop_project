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

public class ClientGuiCommandBased extends JFrame {
  // start attributes
  private JButton jButton1 = new JButton();
  private JTextArea jTextArea1 = new JTextArea("");
  private JScrollPane jTextArea1ScrollPane = new JScrollPane(jTextArea1);
  private JButton jButton2 = new JButton();
  private JTextField jTextField1 = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private Onlineshop_Client myclient;
  // end attributes
  
  public ClientGuiCommandBased() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 590; 
    int frameHeight = 386;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("ClientGuiCommandBased");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
    
    jButton1.setBounds(16, 264, 539, 33);
    jButton1.setText("Send");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton1_ActionPerformed(evt);
      }
    });
    cp.add(jButton1);
    jTextArea1ScrollPane.setBounds(16, 40, 536, 180);
    cp.add(jTextArea1ScrollPane);
    jButton2.setBounds(16, 224, 539, 33);
    jButton2.setText("Clear");
    jButton2.setMargin(new Insets(2, 2, 2, 2));
    jButton2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton2_ActionPerformed(evt);
      }
    });
    cp.add(jButton2);
    jTextField1.setBounds(16, 304, 542, 36);
    cp.add(jTextField1);
    jLabel1.setBounds(16, 0, 534, 36);
    jLabel1.setText("Command Console");
    cp.add(jLabel1);
    // end components
    this.myclient = new Onlineshop_Client(this);
    setVisible(true);
  } // end of public ClientGuiCommandBased
  
  // start methods
  
  public static void main(String[] args) {
    new ClientGuiCommandBased();
  } // end of main
  
  public void jButton1_ActionPerformed(ActionEvent evt) {
    this.myclient.send(this.jTextField1.getText());
    this.jTextField1.setText("");
  } // end of jButton1_ActionPerformed

  public void jButton2_ActionPerformed(ActionEvent evt) {
    this.jTextArea1.setText("");
  } // end of jButton2_ActionPerformed
  
  public void print(String pstring){
    this.jTextArea1.append(pstring+"\n");
    }
} // end of class ClientGuiCommandBased

