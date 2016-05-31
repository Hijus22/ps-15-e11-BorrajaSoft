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

public class PopUpUpdate {
   // Table selection
   private JComboBox comboBox;
   private String comboBoxTable = "Customers";
   private DataInsertion data;
   private JLabel label5;
   private JLabel tableLabel;
   private JTextField textField;
   private JTextField textField2;

   /**
    * Creates a pop up warning to confirm the update.
    */
   public PopUpUpdate() {
      data = HomeWindow.getDataIn();
      SpringLayout layout = new SpringLayout();
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(300, 200));
      panel.setLayout(layout);
      tableLabel = new JLabel("Update table: ");
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
      // Textfield for query
      textField = new JTextField();
      textField.setPreferredSize(new Dimension(200, 23));
      layout.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, comboBox);
      layout.putConstraint(SpringLayout.WEST, textField, 16, SpringLayout.EAST, label2);
      panel.add(textField);
      // ID filter table label
      JLabel label4 = new JLabel("Element ID: ");
      layout.putConstraint(SpringLayout.NORTH, label4, 10, SpringLayout.SOUTH, label2);
      layout.putConstraint(SpringLayout.WEST, label4, 0, SpringLayout.WEST, panel);
      panel.add(label4);
      // Textfield for ID
      textField2 = new JTextField();
      textField2.setPreferredSize(new Dimension(200, 23));
      layout.putConstraint(SpringLayout.NORTH, textField2, 6, SpringLayout.SOUTH, textField);
      layout.putConstraint(SpringLayout.WEST, textField2, 16, SpringLayout.EAST, label4);
      panel.add(textField2);
      // First explanation label
      JLabel label = new JLabel("* columns must be separated by ','");
      layout.putConstraint(SpringLayout.NORTH, label, 15, SpringLayout.SOUTH, label4);
      layout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, panel);
      panel.add(label);
      // Second explanation label
      JLabel label3 = new JLabel("* empty fields must be equals to '-'");
      layout.putConstraint(SpringLayout.NORTH, label3, 15, SpringLayout.SOUTH, label);
      layout.putConstraint(SpringLayout.WEST, label3, 0, SpringLayout.WEST, panel);
      panel.add(label3);
      // Third explanation label
      label5 = new JLabel(getFormat());
      layout.putConstraint(SpringLayout.NORTH, label5, 10, SpringLayout.SOUTH, label3);
      layout.putConstraint(SpringLayout.WEST, label5, 0, SpringLayout.WEST, panel);
      panel.add(label5);
      String[] options = new String[] { "Confirm", "Cancel" };
      int option = JOptionPane.showOptionDialog(null, panel, "Update a element",
               JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      String line = "";
      int ID = -1;
      if (option == 0) // pressing OK button
      {
         line = textField.getText();
         ID = Integer.parseInt(textField2.getText());
         if (line.length() > 0 && ID > 0) {
            selectUpdate(line, ID);
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
    * Select the table in the database to update.
    * 
    * @param line
    *           Insert into 'comboBoxTable' the line contained in the TextField
    */
   private void selectUpdate(String line, int ID) {
      switch (comboBoxTable) {
         case "Customers":
            data.updateCustomersFromLine(line, ID);
            break;
         case "Products":
            data.updateProductsFromLine(line, ID);
            break;
         case "ProductTypes":
            data.updateProductTypesFromLine(line, ID);
            break;
      }
   }
}
