package facade;

public class Order {
   private int customerID;
   private String date;
   private int orderID;
   private double totalPrice;

   public Order(int orderID, int customerID, String date, double totalPrice) {
      this.orderID = orderID;
      this.customerID = customerID;
      this.date = date;
      this.totalPrice = totalPrice;
   }

   public int getCustomerID() {
      return customerID;
   }

   public String getDate() {
      return date;
   }

   public int getOrderID() {
      return orderID;
   }

   public double getTotalPrice() {
      return totalPrice;
   }

   public void setCustomerID(int customerID) {
      this.customerID = customerID;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public void setOrderID(int orderID) {
      this.orderID = orderID;
   }

   public void setTotalPrice(double totalPrice) {
      this.totalPrice = totalPrice;
   }

   public String toString() {
      return orderID + " - " + date + " - " + totalPrice;
   }

   public String toStringDB() {
      return customerID + ", " + "'" + date + "', " + totalPrice;
   }
}
