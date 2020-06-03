import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.NumberFormat;

 

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

 

public class GUIAnmeldung extends JFrame{
  JTextField textfield_name;
  JTextField textfield_passwort;
  JTextField textfield_ausgabe1;
  JButton button_anmelden;
  Onlineshop_Client GUIClient;
    
  public GUIAnmeldung(){
    super();
    this.getContentPane().setLayout(null);
    this.initWindowAnmeldung();
  }
  
  public static void main(String[] args){
    new GUIAnmeldung();
    }
  
  protected void initWindowAnmeldung(){
    textfield_name = new JTextField();
    textfield_passwort = new JTextField();
    textfield_ausgabe1 = new JTextField();
    button_anmelden = new JButton("Anmelden");
    GUIClient = null;
  
    textfield_name.setBounds(5,10,400,25);
    textfield_passwort.setBounds(5,80,400,25);
    textfield_ausgabe1.setBounds(5,150,400,25);
    button_anmelden.setBounds(300,110,100,30);
    
    this.getContentPane().add(textfield_name);
    this.getContentPane().add(textfield_passwort);
    this.getContentPane().add(textfield_ausgabe1);
    this.getContentPane().add(button_anmelden);
    this.pack();
    setSize(500 , 300);
    button_anmelden.addActionListener (new ActionListener(){
      public void actionPerformed(ActionEvent arg0){
        ButtonAnmeldungClicked();
      }
    });

    setVisible(true);
  }
  
  public void ButtonAnmeldungClicked(){
    String Name = null;
    String Password = null;
    Name = textfield_name.getText();
    Password = textfield_passwort.getText();
    if(Name==null||Password==null){
      textfield_ausgabe1.setText("Ups! Da ist wohl etwas schiefgelaufen. Ihr Nutzername oder ihr Passwort stimmt nicht. Bitte versuchen sie es nocheinmal."); 
    }else{
      GUIClient.send("?LOGIN:"+Name+":"+Password);
    }
  }
  
}
