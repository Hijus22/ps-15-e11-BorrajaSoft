package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminButtons extends JPanel implements ActionListener {
   private static final long serialVersionUID = 23L;
   private JButton deleteButton;
   // Admin buttons
   private JButton insertButton;
   private JPanel layeredPane;
   private JButton updateButton;
   private JButton warningButton;

   /**
    * Creates the buttons for the administrator controls.
    */
   public AdminButtons() {
      layeredPane = HomeWindow.getLayeredPane();
      this.setBounds(495, 20, 250, 50);
      this.setLayout(new GridLayout(2, 2));
      setOpaque(true);

      // Insert button
      insertButton = new JButton("Insert element");
      add(insertButton);
      insertButton.setActionCommand("insert");

      // Update button
      updateButton = new JButton("Update element");
      add(updateButton);
      updateButton.setActionCommand("update");
      updateButton.addActionListener(this);
      insertButton.addActionListener(this);
      layeredPane.add(this);

      // Check warnings button
      warningButton = new JButton("Check warnings");
      add(warningButton);
      warningButton.setActionCommand("check");

      // Delete button
      deleteButton = new JButton("Delete element");
      add(deleteButton);
      deleteButton.setActionCommand("delete");
      deleteButton.addActionListener(this);
      warningButton.addActionListener(this);
   }

   /**
    * Shows the message warning when a button is pressed.
    */
   @Override
   public void actionPerformed(ActionEvent evt) {
      switch (evt.getActionCommand()) {
         case "insert":
            PopUpInsert insert = new PopUpInsert();
            break;

         case "update":
            PopUpUpdate update = new PopUpUpdate();
            break;

         case "delete":
            PopUpDelete delete = new PopUpDelete();
            break;

         case "check":
            PopUpWarnings warnings = new PopUpWarnings();
            break;
      }
   }
}