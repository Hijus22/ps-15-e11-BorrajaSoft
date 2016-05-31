package facade;

public class Company {
   private int companyID;
   private String direction;
   private String mail;
   private String name;
   private String password;
   private int telephone;

   /**
    * Create a Company object
    */
   public Company(int idPyme, String nombre, String direccion, String mail, int telefono,
            String password) {
      this.companyID = idPyme;
      this.name = nombre;
      this.direction = direccion;
      this.mail = mail;
      this.telephone = telefono;
      this.password = password;
   }

   public int getCompanyID() {
      return companyID;
   }

   public String getDirection() {
      return direction;
   }

   public String getMail() {
      return mail;
   }

   public String getName() {
      return name;
   }

   public String getPassword() {
      return password;
   }

   public int getTelephone() {
      return telephone;
   }

   public void setCompanyID(int companyID) {
      this.companyID = companyID;
   }

   public void setDirection(String direction) {
      this.direction = direction;
   }

   public void setMail(String mail) {
      this.mail = mail;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setTelephone(int telephone) {
      this.telephone = telephone;
   }

   @Override
   public String toString() {
      return companyID + "\t" + name + "\t" + direction + "\t" + mail + "\t" + telephone + "\t"
               + password;
   }
}
