package gui;

import facade.DataInsertion;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class PopUpInsert {
   // Table selection
   private JComboBox comboBox;
   private String comboBoxTable = "Customers";
   private DataInsertion data;
   private JLabel label5;
   private JLabel tableLabel;
   private JTextField textField;

   /**
    * Creates a pop up warning to confirm the insertion.
    */
   public PopUpInsert() {
      data = HomeWindow.getDataIn();
      SpringLayout layout = new SpringLayout();
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(300, 145));
      panel.setLayout(layout);
      tableLabel = new JLabel("Insert a new: ");
      layout.putConstraint(SpringLayout.NORTH, tableLabel, 3, SpringLayout.NORTH, panel);
      layout.putConstraint(SpringLayout.WEST, tableLabel, 0, SpringLayout.WEST, panel);
      String tableArray[] = { "Customers", "Products", "ProductTypes" };
      comboBox = new JComboBox(tableArray);
      comboBox.setPreferredSize(new Dimension(200, 23));
      layout.putConstraint(SpringLayout.NORTH, comboBox, 2, SpringLayout.NORTH, panel);
      layout.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, tableLabel);
      comboBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            customComboBoxHandler(e);
         }
      });
      // New elements label
      JLabel label2 = new JLabel("New fields: ");
      layout.putConstraint(SpringLayout.NORTH, label2, 17, SpringLayout.SOUTH, tableLabel);
      layout.putConstraint(SpringLayout.WEST, label2, 0, SpringLayout.WEST, tableLabel);
      tableLabel.setLabelFor(comboBox);
      panel.add(tableLabel);
      panel.add(comboBox);
      panel.add(label2);
      // First explanation label
      JLabel label = new JLabel("* columns must be separated by ','");
      layout.putConstraint(SpringLayout.NORTH, label, 15, SpringLayout.SOUTH, label2);
      layout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, panel);
      panel.add(label);
      // Second explanation label
      label5 = new JLabel(getFormat());
      layout.putConstraint(SpringLayout.NORTH, label5, 10, SpringLayout.SOUTH, label);
      layout.putConstraint(SpringLayout.WEST, label5, 0, SpringLayout.WEST, panel);
      panel.add(label5);
      // Textfield
      textField = new JTextField();
      textField.setPreferredSize(new Dimension(200, 23));
      layout.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, comboBox);
      layout.putConstraint(SpringLayout.WEST, textField, 16, SpringLayout.EAST, label2);
      panel.add(textField);
      String[] options = new String[] { "Confirm", "Cancel" };
      int option = JOptionPane.showOptionDialog(null, panel, "Insert new element",
               JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      String line = "";
      if (option == 0) // pressing OK button
      {
         line = textField.getText();
         if (line.length() > 0) {
            selectInsertion(line);
         }
      }
   }

   /**
    * Handler for JComboBox
    */
   private void customComboBoxHandler(ActionEvent evt) {
      JComboBox cb = (JComboBox) evt.getSource();
      try {
         System.err.println();
         if (!comboBoxTable.equalsIgnoreCase((String) cb.getSelectedItem())) {
            // Update list
            comboBoxTable = (String) cb.getSelectedItem();
            label5.setText(getFormat());
         }
      } catch (NullPointerException e) {
         System.err.println("Error estupido");
      }
   }

   /**
    * @return a String which contains format for TextField
    */
   private String getFormat() {
      StringBuilder sb = new StringBuilder(50);
      switch (comboBoxTable) {
         case "Customers":
            sb.append("<html>* Format: password,name,surname,<br/>deliveringAddress,"
                     + "mailAddress,telephone,<br/>(user|admin)</html>");
            break;
         case "Products":
            sb.append("<html>* Format: productTypeID,brand,name,price,<br/>  stock</html>");
            break;
         case "ProductTypes":
            sb.append("<html>* Format: clothes,colour,style,image,size</html>");
            break;
      }
      return sb.toString();
   }

   /**
    * Select the table in the database to insert.
    * 
    * @param line
    *           Insert into 'comboBoxTable' the line contained in the TextField
    */
   private void selectInsertion(String line) {
      switch (comboBoxTable) {
         case "Customers":
            data.insertIntoCustomersFromLine(line);
            break;
         case "Products":
            data.insertIntoProductsFromLine(line);
            break;
         case "ProductTypes":
            data.insertIntoProductTypesFromLine(line);
            break;
      }
   }
}
