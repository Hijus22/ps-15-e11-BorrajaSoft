package facade;

/*
 * File: DataInsertion.java
 * Version: 1.2
 * Author: Antonio Martinez
 */

/*
 * File: DataInsertion.java
 * Version: 1.1
 * Author: Antonio Martinez
 */

/*
 * File: DataInsertion.java
 * Version: 1.0
 * Author: Sergio Lázaro
 */

import database.BCrypt;
import database.DataAccess;
 

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataExtraction {
   private DataAccess mda;

   /**
    * Constructor.
    */
   public DataExtraction(DataAccess mda) {
      this.mda = mda;
   }

   /**
    * Search products in the database based on the name and the given options. If alpha is not one
    * of the correct options, it orders by name by default.
    * 
    * @param query
    *           The query executed in the database.
    * @param orderBY
    *           Must be 'price' or 'alpha'
    * @param asc
    *           If true, orders ASCENDANT else orders DESCENDENT.
    * @return A list of products.
    * @throws SQLException
    */
   public ArrayList<Product> basicSearchProducts(String query, String orderBy, boolean asc)
            throws SQLException {
      ArrayList<Product> productArray = new ArrayList<Product>();
      // Avoid SQL injection
      if (!(orderBy.equalsIgnoreCase("price") || orderBy.equalsIgnoreCase("name"))) {
         orderBy = "name";
      }
      String type = "desc";
      if (asc) {
         type = "asc";
      }
      ResultSet result = mda.getQuery("SELECT * FROM Products WHERE name " + "LIKE '%" + query
               + "%' ORDER BY " + orderBy + " " + type);
      // Buscamos en la tabla Prenda a ver si esta la busqueda del cliente.
      while (result.next()) {
         int productID = result.getInt("productID");
         int productTypeID = result.getInt("productTypeID");
         String brand = result.getString("brand");
         String name = result.getString("name");
         double price = result.getDouble("price");
         int stock = result.getInt("stock");
         Product clothes = new Product(productID, productTypeID, brand, name, price, stock);
         productArray.add(clothes);
      }
      System.out.println("basicSearchProducts ends.");
      return productArray;
   }

   /**
    * Returns the information of a customer based on his user id. 
    * 
    * @param userID The user id in the database.
    * @return All the information of the user stored in the database.
    * @throws SQLException
    */
   public Customer getCustomerInfo(int userID) throws SQLException {
      ResultSet result = mda.getQuery("SELECT * FROM Customers WHERE customerID = " + userID);
      Customer customer = null;
      while (result.next()) {
         int customerID = result.getInt("customerID");
         String password = result.getString("password");
         String name = result.getString("name");
         String surname = result.getString("surname");
         String deliveringAddress = result.getString("deliveringAddress");
         String mailAddress = result.getString("mailAddress");
         String telephone = result.getString("telephone");
         customer = new Customer(customerID, password, name, surname, deliveringAddress,
                  mailAddress, telephone);
      }
      if (customer == null)
         throw new SQLException();
      return customer;
   }

   /**
    * Returns the orderId of a customer.
    * 
    * @param customerID
    *           The id of the customer searched
    * @return A list with the complete order history of the customer.
    * @throws SQLException
    */
   public ArrayList<Product> getOrderRecord(int customerID) throws SQLException {
      ArrayList<Product> array = new ArrayList<Product>();
      ResultSet result = mda.getQuery("SELECT p.productID , p.productTypeID , p.name , p.brand , "
               + "p.price , p.stock FROM Products p , Orders o1, OrderRecords o2 "
               + "WHERE p.productID = o2.productID AND o2.orderID = o1.orderID AND "
               + "o1.customerID = " + customerID + " limit 20");
      while (result.next()) {
         int productID = result.getInt("productID");
         int productTypeID = result.getInt("productTypeID");
         String brand = result.getString("brand");
         String name = result.getString("name");
         double price = result.getDouble("price");
         int stock = result.getInt("stock");
         array.add(new Product(productID, productTypeID, brand, name, price, stock));
      }
      System.out.println("getOrderRecord ends.");
      return array;
   }

   /**
    * Returns all the orders of a customer.
    * 
    * @param customerId The id of the customer.
    * @return A list of orders.
    * @throws SQLException
    */
   public ArrayList<Order> getOrders(int customerId) throws SQLException {
      ArrayList<Order> array = new ArrayList<Order>();
      ResultSet result = mda.getQuery("SELECT o.orderId, o.customerId, o.date, o.totalPrice"
               + "	FROM Orders O WHERE customerId = " + customerId);
      while (result.next()) {
         int orderId = result.getInt("orderId");
         String date = result.getString("date");
         double totalPrice = result.getDouble("totalPrice");
         Order o = new Order(orderId, customerId, date, totalPrice);
         array.add(o);
      }
      System.out.println("getOrderRecord ends.");
      return array;
   }

   /**
    * Returns the path of the image of a product.
    * 
    * @param productTypeID The id of the product.
    * @return Path of the image of productType which has productTypeID or default no image path if
    *         an error occurs
    */
   public String getProductPath(int productTypeID) {
      try {
         ResultSet result = mda.getQuery("SELECT image FROM ProductTypes WHERE productTypeID = "
                  + productTypeID);
         String path = "/photos/no_image.jpg";
         while (result.next()) {
            path = result.getString("image");
         }
         return path;
      } catch (SQLException e) {
         return "/photos/no_image.jpg";
      }
   }

   /**
    * Returns all the products of an order.
    * 
    * @param order The order searched.
    * @return A list of products.
    * @throws SQLException
    */
   public ArrayList<ProductOrder> getProductsFromOrder(Order order) throws SQLException {
      ArrayList<ProductOrder> productArray = new ArrayList<ProductOrder>();
      ResultSet result = mda
               .getQuery("SELECT ors.productID, ors.numItems, p.brand, p.name, p.price "
                        + "FROM orderrecords ors, products p "
                        + "WHERE ors.productId = p.productId AND ors.orderId = " + order.getOrderID());
      // Buscamos en la tabla Prenda a ver si esta la busqueda del cliente.
      while (result.next()) {
         int productID = result.getInt("productID");
         int numItems = result.getInt("numItems");
         String brand = result.getString("brand");
         String name = result.getString("name");
         double price = result.getDouble("price");
         ProductOrder po = new ProductOrder(productID, numItems, brand, name, price);
         productArray.add(po);
      }
      System.out.println("getProductsFromOrder ends.");
      return productArray;
   }

   /**
    * Returns all the types of the products.
    * 
    * @return A list with the styles.
    * @throws SQLException
    */
   public ArrayList<String> getProductType() throws SQLException {
      ArrayList<String> array = new ArrayList<String>();
      ResultSet result = mda.getQuery("SELECT DISTINCT clothes FROM ProductTypes");
      while (result.next()) {
         String cloth = result.getString("clothes");
         array.add(cloth);
      }
      System.out.println("getProductType ends.");
      return array;
   }

   /**
    * Returns the shopping cart of a customer.
    * 
    * @param customerID
    *           The customer searched.
    * @return A list with products.
    * @throws SQLException
    */
   public ArrayList<Product> getShoppingCart(int customerID) throws SQLException {
      ArrayList<Product> array = new ArrayList<Product>();
      ResultSet result = mda.getQuery("SELECT p.productID , p.productTypeID , p.name , p.brand , "
               + "p.price , p.stock, sc.numItems FROM Products p , ShoppingCart sc "
               + "WHERE p.productID = sc.productID AND sc.customerID = " + customerID);
      while (result.next()) {
         int productID = result.getInt("productID");
         int productTypeID = result.getInt("productTypeID");
         String brand = result.getString("brand");
         String name = result.getString("name");
         double price = result.getDouble("price");
         int stock = result.getInt("numItems");
         Product aux = new Product(productID, productTypeID, brand, name, price, stock);
//         aux.setCuantity(result.getInt("numItems"));
         array.add(aux);
      }
      System.out.println("getShoppingCart ends.");
      return array;
   }

   /**
    * Returns the number of items of a product in the shopping cart of an user.
    * 
    * @param productID The product searched.
    * @param customerID The id of the customer in the database.
    * @return An integer with the number of items.
    */
   public int getStackFromCart(int productID, int customerID) {
      int stack = 0;
      try {
         ResultSet result = mda.getQuery("SELECT numItems FROM ShoppingCart WHERE productID = "
                  + productID + " AND customerID = " + customerID);
         while (result.next()) {
            stack = result.getInt("numItems");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return stack;
   }

   /**
    * Returns the style of a product.
    * 
    * @param clothes
    *           The product.
    * @return The style
    * @throws SQLException
    */
   public ArrayList<String> getStyleProduct(String clothes) throws SQLException {
      ArrayList<String> array = new ArrayList<String>();
      ResultSet result = mda.getQuery("SELECT DISTINCT style FROM ProductTypes "
               + "WHERE clothes = '" + clothes + "'");
      while (result.next()) {
         String style = result.getString("style");
         array.add(style);
      }
      System.out.println("getStyleProduct ends.");
      return array;
   }

   /**
    * Returns a list with all the products under a threshold. 
    * 
    * @param threshold The threshold searched.
    * @return A list of products.
    */
   public ArrayList<String> getWarnings(int threshold) {
      ArrayList<String> array = new ArrayList<String>();
      try {
         ResultSet result = mda.getQuery("SELECT DISTINCT p.productID , p.name , p.stock "
                  + "FROM Products p WHERE p.stock < " + threshold);
         while (result.next()) {
            array.add(result.getInt("productID") + " - " + result.getString("name") + " - "
                     + result.getInt("stock"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return array;
   }

   /**
    * Check the username and password into the Customers table.
    * 
    * @param username
    *           Customer username.
    * @param password
    *           Customer password.
    * 
    * @return customerID If exists a customer with that username and password. -1 Otherwise.
    * @throws SQLException
    */
   public int login2(String mailAddress, String password) throws SQLException {
      int idUser = -1;
      ResultSet result = mda.getQuery("SELECT customerID, mailAddress, password FROM Customers"
               + " WHERE mailAddress = '" + mailAddress + "' AND password = '" + password + "'");
      String mailAddressAux = "";
      String passwordAux = "";
      while (result.next()) {
         mailAddressAux = result.getString("mailAddress");
         passwordAux = result.getString("password");
         if (mailAddressAux.equalsIgnoreCase(mailAddress) && passwordAux.equalsIgnoreCase(password)) {
            idUser = result.getInt("customerID");
         }
      }
      System.out.println("login ends.");
      return idUser;
   }
   
   public int login(String mailAddress, String password) throws SQLException {
	   int idUser = -1;
	      ResultSet result = mda.getQuery("SELECT customerID, mailAddress, password FROM Customers"
	               + " WHERE mailAddress = '" + mailAddress + "'");
	      String mailAddressAux = "";
	      String passwordAux = "";
	      while (result.next()) {
	         mailAddressAux = result.getString("mailAddress");
	         passwordAux = result.getString("password");	         
	         if (BCrypt.checkpw(password, passwordAux)){
	         	idUser = result.getInt("customerID");
	         } else{
	        	 
	         }
	      }
	      System.out.println("login ends.");
	      return idUser;
   }

   /**
    * Search customers in the database.
    * 
    * @param query
    *           The query to search in the database.
    * @return A list with customers.
    * @throws SQLException
    */
   public ArrayList<Customer> searchCustomers(String query) throws SQLException {
      ArrayList<Customer> CustomerArray = new ArrayList<Customer>();
      ResultSet result = mda.getQuery("SELECT * FROM Customers WHERE name LIKE '%" + query + "%'");
      mda.getQuery("SELECT * FROM Customers WHERE mailAddress LIKE '%" + query + "%'");
      // Buscamos en la tabla Trabajador a ver si esta la busqueda del cliente.
      while (result.next()) {
         int customerID = result.getInt("customerID");
         String password = result.getString("password");
         String name = result.getString("password");
         String surname = result.getString("password");
         String deliveringAddress = result.getString("password");
         String mailAddress = result.getString("password");
         String telephone = result.getString("telephone");
         Customer customer = new Customer(customerID, password, name, surname, deliveringAddress,
                  mailAddress, telephone);
         CustomerArray.add(customer);
      }
      System.out.println("Fin lookingForTrabajadors");
      return CustomerArray;
   }

   /**
    * Search products in the database from its name.
    * 
    * @param query The query searched.
    * @return A list of products.
    * @throws SQLException
    */
   public ArrayList<Product> searchProduct(String query) throws SQLException {
      ArrayList<Product> productArray = new ArrayList<Product>();
      ResultSet result = mda.getQuery("SELECT * FROM Products WHERE name " + "LIKE '%" + query
               + "%'");
      // Buscamos en la tabla Prenda a ver si esta la busqueda del cliente.
      while (result.next()) {
         int productID = result.getInt("productID");
         int productTypeID = result.getInt("productTypeID");
         String brand = result.getString("brand");
         String name = result.getString("name");
         double price = result.getDouble("price");
         int stock = result.getInt("stock");
         Product clothes = new Product(productID, productTypeID, brand, name, price, stock);
         productArray.add(clothes);
      }
      System.out.println("basicSearchProducts ends.");
      return productArray;
   }

   /**
    * Returns the stock of a given product.
    * 
    * @param productID The id of the product.
    * @return the number of items of the product.
    * @throws SQLException
    *            if no product with that productID is found
    */
   public int searchStockByID(int productID) throws SQLException {
      ResultSet result = mda.getQuery("SELECT stock FROM Products WHERE productID = " + productID);
      int stock = 0;
      while (result.next()) {
         stock = result.getInt("stock");
      }
      return stock;
   }

   /**
    * Returns the type of a customer.
    * 
    * @param customerID The id of the searched customer.
    * @return "user" if its a normal user.
    *         "admin" if its a super user.
    */
   public String userType(int customerID) {
      String type = "user";

      try {
         ResultSet result = mda.getQuery("SELECT customerType FROM Customers WHERE customerID = "
                  + customerID);
         while (result.next()) {
            type = result.getString("customerType");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return type;
   }
}
