package facade;

public class OrderRecord {
   private int ItemNumber;
   private int orderID;
   private int orderRecordID;
   private double pricePerItem;
   private int productID;

   public OrderRecord(int orderID, int orderRecordID, int productID, int ItemNumber,
            double pricePerItem) {
      this.orderID = orderID;
      this.orderRecordID = orderRecordID;
      this.productID = productID;
      this.ItemNumber = ItemNumber;
      this.pricePerItem = pricePerItem;
   }

   public int getItemNumber() {
      return ItemNumber;
   }

   public int getOrderID() {
      return orderID;
   }

   public int getOrderRecordID() {
      return orderRecordID;
   }

   public double getPricePerItem() {
      return pricePerItem;
   }

   public int getProductID() {
      return productID;
   }

   public void setItemNumber(int ItemNumber) {
      this.ItemNumber = ItemNumber;
   }

   public void setOrderID(int orderID) {
      this.orderID = orderID;
   }

   public void setOrderRecordID(int orderRecordID) {
      this.orderRecordID = orderRecordID;
   }

   public void setPricePerItem(double pricePerItem) {
      this.pricePerItem = pricePerItem;
   }

   public void setProductID(int productID) {
      this.productID = productID;
   }

   @Override
   public String toString() {
      return orderID + ", " + productID + ", " + ItemNumber;
   }
}
