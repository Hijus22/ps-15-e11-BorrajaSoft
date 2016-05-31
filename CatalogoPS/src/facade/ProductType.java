/**
* Version 1
* Author: Sergio
*/

package facade;

public class ProductType {
	
   private int productTypeID;
   private String clothes;
   private String colour;
   private String style;
   private String image;
   private String size;

   public ProductType(int productTypeID, String clothes, String colour, String style, String image, String size) {
      this.productTypeID = productTypeID;
      this.clothes = clothes;
      this.colour = colour;
      this.style = style;
      this.image = image;
      this.size = size;
   }
	
	/**
	* Method which returns the ProductType id.
	* @return ProductTypeID
	*/
   	public int getProductTypeID() {
		return productTypeID;
	}
	
	/**
	* Method which change the atribute 'productTypeID' to a new 'productTypeID'
	* @param is a integer greater than 0
	*/
	public void setProductTypeID(int productTypeID) {
		this.productTypeID = productTypeID;
	}
	
	/**
	* Method which returns a String containing the clothes.
	* @return clothes
	*/
	public String getClothes() {
		return clothes;
	}
	
	/**
	* Method which change the atribute 'clothes' to a new 'clothes'
	* @param is a string distinct to null or empty string
	*/
	public void setClothes(String clothes) {
		this.clothes = clothes;
	}
	
	/**
	* Method which returns a String containing the colour.
	* @return colour
	*/
	public String getColour() {
		return colour;
	}
	
	/**
	* Method which change the atribute 'colour' to a new 'colour'
	* @param is a string distinct to null or empty string
	*/
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	/**
	* Method which returns a String containing the style.
	* @return style
	*/
	public String getStyle() {
		return style;
	}
	
	/**
	* Method which change the atribute 'style' to a new 'style'
	* @param is a string distinct to null or empty string
	*/
	public void setStyle(String style) {
		this.style = style;
	}
	
	/**
	* Method which returns a String containing the path file of the image.
	* @return image
	*/
	public String getImage() {
		return image;
	}
	
	/**
	* Method which change the atribute 'image' to a new 'image' path
	* @param is a string distinct to null or empty string
	*/
	public void setImage(String image) {
		this.image = image;
	}

	/**
	* Method which returns a String containing the cloth size.
	* @return size
	*/
	public String getSize() {
		return size;
	}
	
	/**
	* Method which change the atribute 'size' to a new 'size'
	* @param is a string distinct to null or empty string
	*/
	public void setSize(String size) {
		this.size = size;
	}
	
   /**
   * Method which returns a String containing the union of all atributes
   * @return String
   */
   public String toString() {
      return productTypeID + "," +
             "'" + clothes + "', " +
             "'" + colour + "', " +
             "'" + style + "', " +
             "'" + image + "', " +
             "'" + size + "'";
   }
}
