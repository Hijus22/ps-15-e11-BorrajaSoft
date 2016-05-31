package gui;

import facade.Customer;
import facade.DataExtraction;
import facade.DataInsertion;
import facade.Order;
import facade.ProductOrder;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.ColorUIResource;

public class OrderWindow {
   private static DataExtraction data;
   private static DataInsertion dataIns;
   private static DefaultListModel<ProductOrder> mOrder; // To be used, to
   private static JList<ProductOrder> orderList;
   // update
   private Order myOrder;
   private ArrayList<ProductOrder> orderArray = null;
   private JPanel panel;

   /**
    * Creates the window to place the order.
    *
    * @param myOrder The order to insert.
    * @throws IOException
    */
   public OrderWindow(Order myOrder) throws IOException {
      this.myOrder = myOrder;
      data = HomeWindow.getDataEx();
      dataIns = HomeWindow.getDataIn();
      mOrder = new DefaultListModel<ProductOrder>();

      try {
         orderArray = data.getProductsFromOrder(myOrder);
      } catch (SQLException e){
         e.printStackTrace();
      }
      if (orderArray != null) {
         for (ProductOrder o : orderArray) {
            mOrder.addElement(o);
         }
      }

      inicializate();
   }

   /**
    * Generates an emergent window to chose a file to save.
    *
    * @throws IOException
    */
   public void generateFileChooser() throws IOException {
      System.out.println("Guardando recibo");
      JFileChooser saveFile = new JFileChooser();
      saveFile.addChoosableFileFilter(new FileFilter() {
         String description = "Sorted Files (*.txt)";// the filter you see
         String extension = "txt";// the filter passed to program

         @Override
         public boolean accept(File f) {
            if (f == null)
               return false;
            if (f.isDirectory())
               return true;
            return f.getName().toLowerCase().endsWith(extension);
         }

         @Override
         public String getDescription(){
            return description;
         }
      });
      // saveFile.showSaveDialog(null);
      saveFile.setDialogTitle("Specify a file to save");
      // saveFile.setCurrentDirectory(new File("*.txt"));
      int userSelection = saveFile.showSaveDialog(panel);
      if (userSelection == JFileChooser.APPROVE_OPTION) {
         File fileToSave = saveFile.getSelectedFile();
         System.out.println("Save as file: " + fileToSave.getAbsolutePath());
         String path = fileToSave.getAbsolutePath();
         printRecord(path);
      }
   }

   /**
    * Starts the order window.
    *
    * @throws IOException
    */
   public void inicializate() throws IOException {
      SpringLayout layout = new SpringLayout();
      UIManager.put("OptionPane.background", new ColorUIResource(Colors.ORDER_WINDOW_BACKGROUND));
      UIManager.put("Panel.background", new ColorUIResource(Colors.ORDER_WINDOW_BACKGROUND));

      // TODO: Añadir descripción
      panel = new JPanel();
      panel.setPreferredSize(new Dimension(300, 300));
      panel.setLayout(layout);
      panel.setBackground(Colors.ORDER_PANEL_BACKGROUND);

      // TODO: Añadir descripción
      JLabel msg = new JLabel("Product ID | Brand | Name | Price | Num. Items");
      panel.add(msg);

      // TODO: Añadir descripción
      JScrollPane scroll = new JScrollPane();
      scroll.setPreferredSize(new Dimension(300, 250));
      // scroll.setBounds(558, 124, 300, 250);
      panel.add(scroll);

      // TODO: Añadir descripción
      layout.putConstraint(SpringLayout.NORTH, scroll, 5, SpringLayout.SOUTH, msg);
      orderList = new JList<>(mOrder);
      orderList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
      scroll.setViewportView(orderList);
      orderList.setBackground(Colors.ORDER_BACKGROUND);
      String date = myOrder.getDate();
      Date d;
      int numDays = 0;

      // TODO: Añadir descripción
      try {
         Date currentDate = new Date();
         String date2 = (currentDate.getYear() + 1900) + "-" + (currentDate.getMonth() + 1) + "-"
                  + currentDate.getDate();
         SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
         long d1 = formater.parse(date).getTime();
         long d2 = formater.parse(date2).getTime();
         numDays = (int) (Math.abs((d1 - d2) / (1000 * 60 * 60 * 24)));
      } catch (ParseException e) {
         e.printStackTrace();
      }

      // TODO: Añadir descripción
      if (numDays > 15) {
         JLabel msg2 = new JLabel("You can't cancel this record");
         panel.add(msg2);
         layout.putConstraint(SpringLayout.NORTH, msg2, 5, SpringLayout.SOUTH, scroll);
         String[] options = new String[] { "Print", "Ok" };
         int option = JOptionPane.showOptionDialog(null, panel, "My order", JOptionPane.NO_OPTION,
                  JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
         if (option == 0) // print order
         {
            generateFileChooser();
            inicializate();
         }

      // TODO: Añadir descripción
      } else {
         String[] options = new String[] { "Cancel order", "Print", "Ok" };
         int option = JOptionPane.showOptionDialog(null, panel, "My order", JOptionPane.NO_OPTION,
                  JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
         if (option == 0) { // Delete the order form the DB
            dataIns.deleteOrder(myOrder, orderArray);
            inicializateEnd();
            HomeWindow.getCenter().update();
         } else if (option == 1) // print order
         {
            generateFileChooser();
            inicializate();
         }
      }
   }

   /**
    * Deletes the actual order if its rejected.
    */
   public void inicializateEnd() {
      SpringLayout layout = new SpringLayout();
      UIManager UI = new UIManager();
      UIManager.put("OptionPane.background", new ColorUIResource(Colors.PANEL_BACKGROUND));
      UIManager.put("Panel.background", new ColorUIResource(Colors.PANEL_BACKGROUND));

      // TODO: Añadir descripción
      panel = new JPanel();
      panel.setLayout(layout);
      JLabel msg = new JLabel("Your order has been removed");
      panel.add(msg);
      String[] options = new String[] { "Ok" };
      int option = JOptionPane.showOptionDialog(null, panel, "My order", JOptionPane.NO_OPTION,
               JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      if (option == 0) { // Exit
      }
   }

   /**
    * Print the current record in a file.
    *
    * @param path The location of the file.
    * @throws IOException
    */
   public void printRecord(String path) throws IOException {
      BufferedWriter bw = new BufferedWriter(new FileWriter(path));
      try {
         Customer user = data.getCustomerInfo(myOrder.getCustomerID());
         bw.write("BorrajaSoft\n");
         bw.write("Textile Manager                           Order: #" + myOrder.getOrderID()
                  + "  -  " + myOrder.getDate() + "\n");
         bw.write("===================================================================\n\n");
         bw.write(user.getSurname() + ", " + user.getName() + "\n");
         bw.write(user.getMailAddress() + "\n");
         bw.write(user.getDeliveringAddress() + "\n");
         bw.write("===================================================================\n");
         bw.write("===================================================================\n\n\n");
         bw.write("Product Id | Brand | Name | Price | Cuantity\n");
         bw.write("-------------------------------------------------------------------\n");
         for (ProductOrder po : orderArray) {
            bw.write(po + "\n");
         }
         bw.write("-------------------------------------------------------------------\n\n");
         bw.write("                                             Total price: "
                  + myOrder.getTotalPrice() + " €");
      } catch (SQLException e) {
         e.printStackTrace();
      }
      bw.close();
   }
}
