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

public class PopUpDelete {
   // Table selection
   private JComboBox comboBox;
   private String comboBoxTable = "Customers";
   private DataInsertion data;
   private JLabel label5;
   private JLabel tableLabel;
   private JTextField textField;

   /**
    * Creates a pop up warning to confirm the deletion.
    */
   public PopUpDelete() {
      data = HomeWindow.getDataIn();
      SpringLayout layout = new SpringLayout();
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(300, 70));
      panel.setLayout(layout);
      tableLabel = new JLabel("Delete from: ");
      layout.putConstraint(SpringLayout.NORTH, tableLabel, 3, SpringLayout.NORTH, panel);
      layout.putConstraint(SpringLayout.WEST, tableLabel, 0, SpringLayout.WEST, panel);
      String tableArray[] = { "Customers", "Products", "ProductTypes" };

      // TODO: Añadir descripción
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
      JLabel label2 = new JLabel("ID: ");
      layout.putConstraint(SpringLayout.NORTH, label2, 17, SpringLayout.SOUTH, tableLabel);
      layout.putConstraint(SpringLayout.WEST, label2, 0, SpringLayout.WEST, tableLabel);
      tableLabel.setLabelFor(comboBox);
      panel.add(tableLabel);
      panel.add(comboBox);
      panel.add(label2);

      // Textfield
      textField = new JTextField();
      textField.setPreferredSize(new Dimension(195, 23));
      layout.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, comboBox);
      layout.putConstraint(SpringLayout.WEST, textField, 68, SpringLayout.EAST, label2);
      panel.add(textField);

      // TODO: Añadir descripción
      String[] options = new String[] { "Confirm", "Cancel" };
      int option = JOptionPane.showOptionDialog(null, panel, "Delete element",
               JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      String line = "";
      if (option == 0) // pressing OK button
      {
         line = textField.getText();
         if ((!line.contains("drop") || !line.contains("insert") || !line.contains("update"))
                  && Integer.parseInt(line) > 0) {
            data.deleteFromTable(comboBoxTable, Integer.parseInt(line));
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
         }
      } catch (NullPointerException e) {
         System.err.println("Error estupido");
      }
   }
}
