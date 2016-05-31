package gui;

/*
 * File: HomeWindow.java
 * Version: 2.0
 * Author: Antonio Martínez
 */

/*
 * File: HomeWindow.java
 * Version: 1.1
 * Author: Antonio Martínez
 */
 
/*
 * File: HomeWindow.java
 * Version: 1.0
 * Author: Víctor Sánchez
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import database.DataAccess;
import facade.Customer;
import facade.DataExtraction;
import facade.DataInsertion;
import facade.Product;

public class HomeWindow {
   private static CenterPanel center;
   private static ArrayList<Customer> customerArray;
   public static DataAccess data;
   private static DataExtraction dataEx;
   private static DataInsertion dataIn;
   private static JFrame frameRopaUltracool;
   private static int idUser;
   private static String lastSearch;
   private static JPanel layeredPane;
   private static Menu menu;
   private static ArrayList<Product> productArray;
   private static RightPanel right;
   private static JButton searchButton;
   private static JTabbedPane tabbedPane;
   private static JTextField textField;
   private static TopPanel top;

   /**
    * Creates the principal window of the application.
    */
   public HomeWindow() {
      
      layeredPane = new JPanel();
      layeredPane.setBackground(Colors.HOME_BACKGROUND);
      layeredPane.setOpaque(false);
      LoginWindow loginW = new LoginWindow();
      tabbedPane = new JTabbedPane();
//      tabbedPane.setBackground(Colors.HOME_BACKGROUND);
      idUser = -1; // CHANGE to -1
      // Show up log-in window
      while (idUser == -1) {
         System.out.println("ID = " + idUser);
         idUser = loginW.login();
         System.out.println("ID = " + idUser);
      }
      if (idUser != -2) {
//    	 dataEx = new DataExtraction(data);
//         dataIn = new DataInsertion(data);
    	 dataIn.updateCustomers();
         initialize();
         frameRopaUltracool.setVisible(true);
      }
   }

   public static CenterPanel getCenter() {
      return center;
   }

   public static ArrayList<Customer> getCustomerArray() {
      return customerArray;
   }

  // public static DataAccess get
   
   public static DataExtraction getDataEx() {
      return dataEx;
   }

   public static DataInsertion getDataIn() {
      return dataIn;
   }

   public static JFrame getFrameRopaUltracool() {
      return frameRopaUltracool;
   }

   public static int getIdUser() {
      return idUser;
   }

   public static String getLastSearch() {
      return lastSearch;
   }

   public static JPanel getLayeredPane() {
      return layeredPane;
   }

   public static Menu getMenu() {
      return menu;
   }

   public static ArrayList<Product> getProductArray() {
      return productArray;
   }

   public static RightPanel getRight() {
      return right;
   }

   public static JButton getSearchButton() {
      return searchButton;
   }

   public static JTabbedPane getTabbedPane() {
      return tabbedPane;
   }

   public static JTextField getTextField() {
      return textField;
   }

   public static TopPanel getTop() {
      return top;
   }
   
   public static void setDataAccess(DataAccess mda) {
	  HomeWindow.data = mda;
	}   

   public static void setDataEx(DataExtraction dataEx) {
	HomeWindow.dataEx = dataEx;
   }

   public static void setDataIn(DataInsertion dataIn) {
	HomeWindow.dataIn = dataIn;
	}

/**
    * Launch the application.
    */
   public static void main(String[] args) {
      // Event Queue configuration
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               @SuppressWarnings("unused")
               HomeWindow window = new HomeWindow();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   public static void setLastSearch(String lastSearch) {
      HomeWindow.lastSearch = lastSearch;
   }

   /**
    * Implements the actionListener over the JTree (Menu)
    * 
    * @param tse event
    */
   public void menuValueChanged(TreeSelectionEvent tse) {
      try {
         // System.out.println("El nodo es: " +
         // tse.getNewLeadSelectionPath().toString());
         // System.out.println(tse.getNewLeadSelectionPath().getPathCount());
         // for (Object i : tse.getNewLeadSelectionPath()){
         //
         // }
         setLastSearch(tse.getNewLeadSelectionPath().getLastPathComponent().toString());
         TreePath prueba = tse.getNewLeadSelectionPath().getParentPath();
         while (prueba.getParentPath() != null) {
            setLastSearch(getLastSearch() + " " + prueba.getLastPathComponent().toString());
            prueba = prueba.getParentPath();
            // System.out.println("setLastSearch vale:");
            // System.out.println("prueba vale:");
         }
         System.out.println("setLastSearch vale:" + lastSearch);
         // productArray = data.searchProduct(getLastSearch());
         // productArray =
         // center.replace(productArray);
         center.update();
      } catch (NullPointerException e) {
         System.err.println("se ha clicado en clothes");
         // } catch (SQLException e) {
         // e.printStackTrace();
      }
   }

   /**
    * Initialize the contents of the frame that implements the main window.
    */
   private void initialize() {
      // Define the frame
      frameRopaUltracool = new JFrame();
      frameRopaUltracool.getContentPane().setBackground(Colors.HOME_PANEL_BACKGROUND);
      frameRopaUltracool.setIconImage(Toolkit.getDefaultToolkit().getImage(
               HomeWindow.class.getResource("/photos/CompanyIcon.jpg")));
      frameRopaUltracool.setTitle("Textile Manager"); //
      // frameRopaUltracool.setExtendedState(JFrame.MAXIMIZED_BOTH); //MAXIMUM
      // AVAIVABLE SIZE
      frameRopaUltracool.setBounds(100, 100, 820, 800); // SMALL NON-FIXED
      // WINDOW
      frameRopaUltracool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // frameRopaUltracool.getContentPane().setLayout(null);
      layeredPane.setBounds(0, 0, 800, 800);
      // layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.X_AXIS));
      layeredPane.setLayout(null);
      // ADD Catalogue TAB
      ImageIcon icon = new ImageIcon(HomeWindow.class.getResource("/photos/menu_icon.png"));
      tabbedPane.addTab("Catalogue", icon, layeredPane, "Catalogue Panel");
      frameRopaUltracool.getContentPane().add(tabbedPane);
      // ADD User Info TAB
      icon = new ImageIcon(HomeWindow.class.getResource("/photos/icon_user.png"));
      tabbedPane.addTab("User", icon, new UserInfo(), "User Info");
      // frameRopaUltracool.getContentPane().add(layeredPane);
      frameRopaUltracool.setResizable(false); // NON-RESIZABLE
      // Admin buttons
      if (dataEx.userType(idUser).equals("admin")) {
         @SuppressWarnings("unused")
         AdminButtons adminButtons = new AdminButtons();
      }
      // Search bar
      textField = new JTextField();
      textField.setBounds(560, 101, 214, 20);
      layeredPane.add(textField);
      textField.setColumns(10);
      // SearchButton
      searchButton = new JButton("");
      searchButton.setIcon(new ImageIcon(HomeWindow.class.getResource("/photos/LookingFor.jpg")));
      searchButton.setBounds(770, 101, 20, 20);
      layeredPane.add(searchButton);
      // Clothes tree
      menu = new Menu();
      // Shopping cart & History
      right = new RightPanel(idUser);
      // Logo
      // TODO Choose a functionality for this button
      JButton btnNewButton = new JButton("");
      btnNewButton.setIcon(new ImageIcon(HomeWindow.class.getResource("/photos/logo.jpg")));
      btnNewButton.setBounds(105, 11, 227, 102);
      layeredPane.add(btnNewButton);
      // center panel
      center = new CenterPanel(null);
      // Superior panel
      top = new TopPanel(idUser);
      // Adds the actionListeners
      listeners();
   }

   /**
    * Method that adds the actionListeners
    */
   private void listeners() {
      // Search Button Listener
      searchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            setLastSearch(textField.getText());
            try {
               if (textField.getText() != null && !textField.getText().equals("")) {
                  System.err.println("Busqueda" + textField.getText());
                  productArray = dataEx.basicSearchProducts(getLastSearch(), "name", true);
                  // customerArray =
                  // data.searchCustomers(getLastSearch());
                  textField.setText("");
                  center.update();
               }
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               //
            }
         }
      });
      // Menu actionListener
      menu.getTree().addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
         public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
            menuValueChanged(evt);
         }
      });
      // Center actionListener
      center.getList().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
         public void valueChanged(ListSelectionEvent evt) {
            center.centerValueChanged(evt);
         }
      });
      // ShoppingCart Listener
      right.getShoppingCartList().addListSelectionListener(
               new javax.swing.event.ListSelectionListener() {
                  public void valueChanged(ListSelectionEvent evt) {
                     right.shoppingCartListChanged(evt, top);
                  }
               });
   }


}
