/**
* Version 1
* Author: Sergio
*/

package gui;

import facade.Customer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.BCrypt;

public class UserInfo extends JPanel implements ActionListener {
   private static final long serialVersionUID = 1L;
   private JButton cancel;
   private JPanel control, fields;
   private Customer customer;
   private JLabel[] errorLabel;
   private JTextField name, lName, mail, address, phone;
   private JPasswordField password, cPassword, nPassword, ncPassword;
   private JButton update;

   /**
    * Creates the user info panel.
    */
   public UserInfo() {
      initialize();	//Method which initialices the UserInfo JPanel
   }

   /**
    * Creates the behaviour for actions related to update/cancel the update of a user info.  
	* @param evt must be a semantic event which indicates that a component-defined action ocurred.
    */
   public void actionPerformed(ActionEvent evt) {
	  boolean err = false;
      switch (evt.getActionCommand()) {
         case "cancel":	//If click on cancel option -> nothing happened
            initialize();
            break;
         case "update":	//Otherwise -> update all fields
        	 String newPassword = new String(nPassword.getPassword());
        	 String newCPassword = new String(ncPassword.getPassword());
        	  			
        	 //Check if new password fields match
 			if (!newPassword.equals(newCPassword)){
 				err = true;
 			   this.printError(5, " Passwords don't", " match");

 			} else{
 				this.clearError(5, true);
 			}
        	 
            //Check if password fields are equal
			if (!new String(password.getPassword()).equals(new String(cPassword.getPassword()))){
               //If they are not equal -> Warning!
			   err = true; // Error found
			   this.printError(7, " Passwords don't", " match");

               password.selectAll();
               password.requestFocusInWindow();
               cPassword.setText("");
            } else{
            	this.clearError(7, true);
            }
			
			
			if(!err){ // Check if the password is correct
				int idUser=-1;
				try {
					idUser = HomeWindow.getDataEx().login(mail.getText(), 
										new String(password.getPassword()));
//					idUser=1; // CAMBIARLO
				//} catch (SQLException e) {
				} catch (Exception e){
					err = true;
					e.printStackTrace();
				} finally{
					if (idUser == -1){
						err = true;
						this.printError(7, " Wrong Password", " ");

			            password.selectAll();
			            password.requestFocusInWindow();
			            cPassword.setText("");
					} else{
						clearError(7,false);
					}
				}
			} 
			
			if (!err) {//If no errors found -> Update user info
               customer.setDeliveringAddress(address.getText());
               customer.setMailAddress(mail.getText());
               customer.setTelephone(phone.getText());
               if(newPassword.equals("")){ // Do not update password
                   customer.setPassword(new String(password.getPassword()));
               } else{ // Update password
            	   //String generatedSecuredPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            	   //System.out.println("El hash generado es: " + generatedSecuredPasswordHash);
                   //customer.setPassword(generatedSecuredPasswordHash);
            	   customer.setPassword(newPassword);
               }
               try {
                  HomeWindow.getDataIn().updateUserInfo(customer);	//Method which update info
                  initialize();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
            this.updateUI();
            break;
      }
   }
   
   
   /**
    * Method to clear error messages in two consecutive rows
    * @param pos - which row to start
    * @param both - Indicates if both rows should be cleared
    */
   private void clearError(int pos, boolean both){
	   errorLabel[pos+1].setText("");
       errorLabel[pos+1].setForeground(Color.red);
       if(both){
       errorLabel[pos].setText("");
       errorLabel[pos].setForeground(Color.red);
       }
   }
   
   /**
    * Method to set error messages in two consecutive rows
    * @param pos which row to start
    * @param msg1 - Message to be displayed in row [0]
    * @param msg2 - Message to be displayed in row [1]
    */
   private void printError(int pos, String msg1, String msg2){
	   errorLabel[pos].setText(msg1);
       errorLabel[pos].setForeground(Color.red);
       errorLabel[pos+1].setText(msg2);
       errorLabel[pos+1].setForeground(Color.red);
   }
	
	/**
	* Method which initialises the UserInfo JPanel
	*/
   private void initialize() {
      this.removeAll();
      this.setOpaque(false);
	  //Setting up both JPanel
      fields = new JPanel(new GridLayout(0, 6));
      fields.setOpaque(false);
      control = new JPanel(new GridLayout(15, 5));
      control.setOpaque(false);
      errorLabel = new JLabel[9]; //Creates a JLabel array
	  //Setting up Update JButton
      update = new JButton("Update");
      update.setActionCommand("update");
      update.addActionListener(this);
	  //Setting up Cancel JButton
      cancel = new JButton("Cancel");
      cancel.setActionCommand("cancel");
      cancel.addActionListener(this);
	  //Add both JButtons and every JLabel
      for (int j = 0; j < 80; j++) {
         if (j == 2) {
            control.add(update);
         } else if (j == 3) {
            control.add(cancel);
         } else {
            control.add(new JLabel(" "));
         }
      }
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      add(fields);
      add(control);
      String[] labelsArray = { "First Name", "Last Name", "Mail Address", "Delivering Address",
               "Phone Number","New Password", "Rep New Password", "Password", "Confirm Password" };
      customer = new Customer(HomeWindow.getIdUser(), "", "", "", "", "", "");
      try {
		  //Get Customer Info
         customer = HomeWindow.getDataEx().getCustomerInfo(HomeWindow.getIdUser());
      } catch (SQLException e) {
         e.printStackTrace();
      }
	  //Clear every JLabel
      for (int j = 0; j < 12; j++) {
         fields.add(new JLabel(" "));
      }
      for (int i = 0; i < labelsArray.length; i++) {
         fields.add(new JLabel(" "));
         fields.add(new JLabel(" "));
         fields.add(new JLabel(labelsArray[i]));
         switch (labelsArray[i]) {
            case "First Name":	//Setting up First Name JTextField
               name = new JTextField(customer.getName());
               name.setEditable(false);
               name.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
               fields.add(name);
               break;
            case "Last Name":	//Setting up Last Name JTextField
               lName = new JTextField(customer.getSurname());
               lName.setEditable(false);
               fields.add(lName);
               break;
            case "Mail Address":	//Setting up Mail Address JTextField
               mail = new JTextField(customer.getMailAddress());
               fields.add(mail);
               break;
            case "Delivering Address":	//Setting up Delivering Address JTextField
               address = new JTextField(customer.getDeliveringAddress());
               fields.add(address);
               break;
            case "Phone Number":	//Setting up Phone Number JTextField
               phone = new JTextField(customer.getTelephone());
               fields.add(phone);
               break;
            case "New Password": // Setting up new Password Field
                nPassword = new JPasswordField();
                fields.add(nPassword);
                break;
            case "Rep New Password": // Setting up new Password Field
                ncPassword = new JPasswordField();
                fields.add(ncPassword);
                break;
            case "Password":	//Setting up Password JTextField
               password = new JPasswordField();
               fields.add(password);
               break;
            case "Confirm Password":	//Setting up Confirm Password JTextField
               cPassword = new JPasswordField();
               fields.add(cPassword);
               break;
         }
         fields.add(errorLabel[i] = new JLabel(" "));
         fields.add(new JLabel(" "));
      }
   }
}
