package software.project.backend.Model;

import java.sql.Blob;
import java.util.ArrayList;



public class Book implements Imodel{
    private String ISBN;
    private String title;
    private int noOfCopies;
    private int categoryId;
    private double price;
    private String image;
    private String publicationYear;
    private int threshold;
    private int publisherId ;


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String isbn) {
        this.ISBN = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public int  getPublisherId() {
    	return publisherId ;
    }
    
    public void setPublisherId(int p) {
    	this.publisherId = p ;
    }
    
    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int c) {
        this.noOfCopies = c;
    }
    
    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int t) {
        this.threshold = t;
    }
    
    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String y) {
        this.publicationYear = y;
    }

   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

  

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
