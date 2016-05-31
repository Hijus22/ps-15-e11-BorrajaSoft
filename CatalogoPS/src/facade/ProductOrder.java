package facade;

public class ProductOrder {
   private String brand;
   private String name;
   private int numItems;
   private double price;
   private int productId;

   public ProductOrder(int productId, int numItems, String brand, String name, double price) {
      this.productId = productId;
      this.numItems = numItems;
      this.brand = brand;
      this.name = name;
      this.price = price;
   }

   public String getBrand() {
      return brand;
   }

   public String getName() {
      return name;
   }

   public int getNumItems() {
      return numItems;
   }

   public double getPrice() {
      return price;
   }

   public int getProductId() {
      return productId;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setNumItems(int numItems) {
      this.numItems = numItems;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public void setProductId(int productId) {
      this.productId = productId;
   }

   public String toString() {
      return productId + " - " + brand + " - " + name + " - " + price + " - " + numItems;
   }
}
