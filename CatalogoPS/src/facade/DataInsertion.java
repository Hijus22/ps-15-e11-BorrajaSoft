package facade;

/*
 * File: DataInsertion.java
 * Version: 1.4
 * Author: Antonio Martinez
 */

/*
 * File: DataInsertion.java
 * Version: 1.3
 * Author: Sergio Lázaro
 */

/*
 * File: DataInsertion.java
 * Version: 1.2
 * Author: Sergio Lázaro
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
import gui.HomeWindow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataInsertion {
   private static final String[] customersFields = { "password", "name", "surname",
            "deliveringAddress", "mailAddress", "telephone", "customerType" };
   private static final String[] productFields = { "productTypeID", "brand", "name", "price",
            "stock" };
   private static final String[] productTypeFields = { "clothes", "colour", "style", "image",
            "size" };
   private DataAccess mda;

   /**
    * Constructor
    */
   public DataInsertion(DataAccess mda) {
      this.mda = mda;
   }

   /**
    * Deletes a row from a table of the database pointed by the primary key out.
    * 
    * @param table
    *           The table in the database.
    * @param ID
    *           The identifier of the row.
    */
   public void deleteFromTable(String table, int ID) {
      if (table.equals("Customers")) {
          mda.setQuery("DELETE FROM " + table + " WHERE customerID = " + ID);
      } else if (table.equals("Products")) {
    	  // Elimina un producto del conjunto de productos y de todos los pedidos de los clientes
          mda.setQuery("DELETE FROM shoppingCart WHERE productID = " + ID);
          mda.setQuery("DELETE FROM " + table + " WHERE productID = " + ID);
      } else if (table.equals("ProductTypes")) {
         mda.setQuery("DELETE FROM " + table + " WHERE productTypeID" + ID);
      }
   }

   /**
    * Deletes an order from the database and retrieves the stock of their products.
    * 
    * @param order
    *           The order to delete.
    * @param list
    *           The list of products in the order.
    */
   public void deleteOrder(Order order, ArrayList<ProductOrder> list) {
      mda.setQuery("DELETE FROM orderRecords WHERE orderId= " + order.getOrderID());
      mda.setQuery("DELETE FROM orders WHERE orderId= " + order.getOrderID());
      // Updates the stock for each product.
      for (ProductOrder productOrder : list) {
         mda.setQuery("UPDATE products SET stock = stock + " + productOrder.getNumItems()
                  + " WHERE productId = " + productOrder.getProductId());
      }
   }

   /**
    * Deletes the products in the shopping cart of an user.
    * 
    * @param userID
    *           The user selected.
    */
   public void deleteShoppingCart(int userID) {
      mda.setQuery("DELETE FROM shoppingcart WHERE customerId = " + userID);
   }

   /**
    * Insert data into the Customers table.
    * 
    * @param customer
    *           The data to insert.
    */
   public void insertCustomer(Customer customer) {
	  customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)));
      mda.setQuery("INSERT INTO Customers VALUES (" + customer.toString() + ")");
   }

   /**
    * Creates a customer in the database from data.
    * 
    * @param line
    *           The data needed to create a new customer. Ordered as
    *           "password,name,surname,deliveringAddress,mailAddress,telephone,customerType"
    */
   public void insertIntoCustomersFromLine(String line) {
      ArrayList<String> array = new ArrayList<String>();
      Scanner s = new Scanner(line);
      s.useDelimiter(",");
      while (s.hasNext()) {
         array.add(s.next());
      }
      s.close();

      String query = "INSERT INTO Customers(password,name,surname,deliveringAddress,"
               + "mailAddress,telephone,customerType) VALUES('";
      array.set(0, BCrypt.hashpw(array.get(0), BCrypt.gensalt(12)));
      for (int i = 0; i < array.size(); i++) {
         if (i == array.size() - 1) {
            query += array.get(i) + "')";
         } else {
            query += array.get(i) + "','";
         }
      }
      System.out.println(query);
      mda.setQuery(query);
   }

   /**
    * Creates a product in the database from data.
    * 
    * @param line
    *           The data needed to create a new product. Ordered as
    *           "productTypeID,brand,name,price,stock"
    */
   public void insertIntoProductsFromLine(String line) {
      String[] array = line.split(",");
      int productTypeID = Integer.parseInt(array[0]);
      String brand = array[1];
      String name = array[2];
      double price = Double.parseDouble(array[3]);
      int stock = Integer.parseInt(array[4]);

      String query = "INSERT INTO Products(productTypeID,brand,name,price,stock) " + "VALUES("
               + productTypeID + ",'" + brand + "','" + name + "'," + price + "," + stock + ")";
      mda.setQuery(query);
   }

   /**
    * Creates a product type in the database from data.
    * 
    * @param line
    *           The data needed to create a new product type. Ordered as
    *           "clothes,colour,style,image,size"
    */
   public void insertIntoProductTypesFromLine(String line) {
      String[] array = line.split(",");
      String clothes = array[0];
      String colour = array[1];
      String style = array[2];
      String image = array[3];
      String size = array[4];

      String query = "INSERT INTO ProductTypes(clothes,colour,style,image,size) " + "VALUES('"
               + clothes + "','" + colour + "','" + style + "','" + image + "','" + size + "')";
      mda.setQuery(query);
   }

   /**
    * Insert data into the Orders table.
    * 
    * @param order
    *           The data to insert.
    */
   public int insertOrder(Order order) throws SQLException {
      String query = "INSERT INTO Orders (customerId, date, totalPrice) VALUES ("
               + order.toStringDB() + ")";
      mda.setQuery(query);
      System.err.println(query);
      query = "SELECT O.orderId FROM orders O " + "WHERE O.customerId = " + order.getCustomerID()
               + " AND O.date = '" + order.getDate() + "'" /*
                                                            * + " AND O.totalPrice = " +
                                                            * order.getTotalPrice() + ""
                                                            */;
      System.err.println(query);
      ResultSet result = mda.getQuery(query);
      int orderId = -1;
      while (result.next()) {
         orderId = result.getInt(1);
      }
      return orderId;
   }

   /**
    * Insert data into the OrderRecords table.
    * 
    * @param orderRecord
    *           The data to insert.
    */
   public void insertOrderRecord(OrderRecord orderRecord) {
      mda.setQuery("INSERT INTO OrderRecords (orderId, productId, numItems) VALUES ("
               + orderRecord.toString() + ")");
   }

   /**
    * Insert data into the Products table.
    * 
    * @param product
    *           The data to insert.
    */
   public void insertProduct(Product product) {
      mda.setQuery("INSERT INTO Products VALUES (" + product.toString() + ")");
   }

   /**
    * Insert data into the ProductType table.
    * 
    * @param productType
    *           The data to insert.
    */
   public void insertProductType(ProductType productType) {
      mda.setQuery("INSERT INTO ProductTypes VALUES (" + productType.toString() + ")");
   }

   /**
    * Insert data into the ShoppingCarts table.
    * 
    * @param shoppingCart
    *           The data to insert.
    */
   public void insertShoppingCart(ShoppingCart shoppingCart) {
      mda.setQuery("INSERT INTO ShoppingCarts VALUES (" + shoppingCart.toString() + ")");
   }

   /**
    * Modifies a customer in the database from data.
    *   
    * @param line
    *           must contains all columns of table Customers separated by [,]
    * @param customerID
    *           must be a existing customerID of Customers table Update Customers table where
    *           customerID = [customerID] with elements contained in line
    */
   public void updateCustomersFromLine(String line, int customerID) {
      String query = "UPDATE Customers SET ";
      String[] array = line.split(",");
      array[0]=BCrypt.hashpw(array[0], BCrypt.gensalt(12));
      if (array.length == customersFields.length) {
         for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("-")) {
               query += customersFields[i] + "='" + array[i] + "' , ";
            }
         }
         query = query.substring(0, query.length() - 2);
         query += " WHERE customerID = " + customerID;
         mda.setQuery(query);
      }
   }
   
   public void updateCustomers(){
	   for (int i=1; i<200; i++){
			StringBuffer rs = new StringBuffer();
		   try {
			   Customer cust = HomeWindow.getDataEx().getCustomerInfo(i);
			   if (cust.getPassword().charAt(0)!='$'){
				   rs.append("UPDATE Customers SET password='");
				   String generatedSecuredPasswordHash = 
						   BCrypt.hashpw(cust.getPassword(), BCrypt.gensalt(12));
				   rs.append(generatedSecuredPasswordHash);
				   rs.append("' WHERE customerID = " + i);
				   mda.setQuery(rs.toString());
			   }
			   //rs.append("");
		   } catch (SQLException e) {
			//e.printStackTrace();
			break;
		   }
	   }
   }

   /**
    * Modifies a product in the database from data.
    * 
    * @param line
    *           must contains all columns of table Products separated by [,]
    * @param productID
    *           must be an existing productID of Products table Update Products table where
    *           productID = [productID] with elements contained in line
    */
   public void updateProductsFromLine(String line, int productID) {
      String query = "UPDATE Products SET ";
      String[] array = line.split(",");
      if (array.length == productFields.length) {
         for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("-")) {
               if (i == 1 || i == 2) {
                  query += productFields[i] + "='" + array[i] + "' , ";
               } else {
                  query += productFields[i] + "=" + array[i] + " , ";
               }
            }
         }
         query = query.substring(0, query.length() - 2);
         query += " WHERE productID = " + productID;
         mda.setQuery(query);
      }
   }

   /**
    * Modifies the number of items of a product in an order.
    * 
    * @param num     The number of items to add or subtract.
    * @param maxNum  The number of items previously selected.
    * @param product The product selected.
    * @param userID  The customer identification in the database.
    */
   public void updateProductStock(int num, int maxNum, Product product, int userID) {
      DataExtraction d = HomeWindow.getDataEx();
      try {
         int stock = d.searchStockByID(product.getProductID());
         mda.setQuery("UPDATE Products SET stock = " + (stock - num) + " WHERE productID = "
                  + product.getProductID());
         int cartStack = d.getStackFromCart(product.getProductID(), userID);
         if (cartStack == 0) {
            System.err.println("INSERTO PRODUCTO CON ID: " + product.getProductID());
            System.err.println("cartStack = " + cartStack);

            // Insert new product to shopping cart if is not there
            mda.setQuery("INSERT INTO ShoppingCart VALUES(" + userID + " , "
                     + product.getProductID() + " , " + num + ")");
         } else if (cartStack == -num) {
            System.err.println("BORRO PRODUCTO CON ID: " + product.getProductID());
            System.err.println("cartStack = " + cartStack + "num " + num);
            mda.setQuery("DELETE FROM ShoppingCart WHERE productID = " + product.getProductID()
                     + " AND customerID = " + userID);
         } else {
            System.err.println("ACTUALIZO PRODUCTO CON ID: " + product.getProductID());
            System.err.println("cartStack = " + cartStack + "num " + num);
            // Update a product which exists
            mda.setQuery("UPDATE ShoppingCart SET numItems = " + (maxNum + num)
                     + " WHERE productID = " + product.getProductID() + " AND customerID = "
                     + userID);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   /**
    * Modifies a product in the database from data.
    * 
    * @param line
    *           must contains all columns of table ProductsTypes separated by [,]
    * @param productTypeID
    *           must be an existing productTypeID of ProductTypes table Update ProductsTypes table
    *           where productTypeID = [productTypeID] with elements contained in line
    */
   public void updateProductTypesFromLine(String line, int productTypeID) {
      String query = "UPDATE ProductTypes SET ";
      String[] array = line.split(",");
      if (array.length == productTypeFields.length) {
         for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("-")) {
               query += productTypeFields[i] + "='" + array[i] + "' , ";
            }
         }
         query = query.substring(0, query.length() - 2);
         query += " WHERE productTypeID = " + productTypeID;
         mda.setQuery(query);
      }
   }

   /**
    * Modifies a customer in the database from data.
    * 
    * @param info The data to modify the customer.
    * @throws SQLException
    */
   public void updateUserInfo(Customer info) throws SQLException {
      String cPassword = info.getPassword();
      cPassword=BCrypt.hashpw(cPassword, BCrypt.gensalt(12));
      String query = "UPDATE Customers SET deliveringAddress = '" + info.getDeliveringAddress()
               + "', mailAddress = '" + info.getMailAddress() + "', telephone = '"
               + info.getTelephone() + "'";
      if (cPassword != null && !cPassword.equals("")) {
         query = query + ", password = '" + cPassword + "'";
      }
      query = query + " WHERE customerID = " + info.getCustomerID();
      mda.setQuery(query);
   }
}
