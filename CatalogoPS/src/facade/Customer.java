package facade;

public class Customer {
   private int customerID;
   private String deliveringAddress;
   private String mailAddress;
   private String name;
   private String password;
   private String surname;
   private String telephone;

   /**
    * Create a Customer object
    */
   public Customer(int customerID, String password, String name, String surname,
            String deliveringAddress, String mailAddress, String telephone) {
      this.customerID = customerID;
      this.password = password;
      this.name = name;
      this.surname = surname;
      this.deliveringAddress = deliveringAddress;
      this.mailAddress = mailAddress;
      this.telephone = telephone;
   }

   public int getCustomerID() {
      return customerID;
   }

   public String getDeliveringAddress() {
      return deliveringAddress;
   }

   public String getMailAddress() {
      return mailAddress;
   }

   public String getName() {
      return name;
   }

   public String getPassword() {
      return password;
   }

   public String getSurname() {
      return surname;
   }

   public String getTelephone() {
      return telephone;
   }

   public void setCustomerID(int customerID) {
      this.customerID = customerID;
   }

   public void setDeliveringAddress(String deliveringAddress) {
      this.deliveringAddress = deliveringAddress;
   }

   public void setMailAddress(String mailAddress) {
      this.mailAddress = mailAddress;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public void setTelephone(String telephone) {
      this.telephone = telephone;
   }

   @Override
   public String toString() {
      return customerID + ", " + "'" + password + "', " + "'" + name + "', " + "'" + surname
               + "', " + "'" + deliveringAddress + "', " + "'" + mailAddress + "', " + telephone;
   }
}
