/**
* Version 1
* Author: Sergio
*/

package facade;

public class ShoppingCart {
	
   private int customerID;
   private int numItems;
   private String productID;

   public ShoppingCart(int customerID, String productID, int numItems) {
      this.customerID = customerID;
      this.productID = productID;
      this.numItems = numItems;
   }
	
	/**
	* Method which returns the CustomerID.
	* @return customerID
	*/
	public int getCustomerID() {
		return customerID;
	}
	
	/**
	* Method which change the atribute 'customerID' to a new 'customerID'
	* @param is a integer greater than 0
	*/
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	/**
	* Method which returns the numItems.
	* @return numItems
	*/
	public int getNumItems() {
		return numItems;
	}
	
	/**
	* Method which change the atribute 'numItems' to a new 'numItems'
	* @param is a integer greater than 0
	*/
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	
	/**
	* Method which returns the ProductID.
	* @return ProductID
	*/
	public String getProductID() {
		return productID;
	}
	
	/**
	* Method which change the atribute 'productID' to a new 'productID'
	* @param is a integer greater than 0
	*/
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	* Method which returns a String containing the union of all atributes
	* @return String
	*/
	public String toString() {
		return customerID + ", " +
			 productID + ", " +
			 numItems;
	}
}
