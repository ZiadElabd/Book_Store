package software.project.backend.Model.builder;

import org.json.JSONException;
import org.json.JSONObject;
import software.project.backend.Model.Book;
import software.project.backend.Model.Imodel;



public class bookBuilder implements Ibuilder {
    private Book bookModel;
    private void buildISBN(String ISBN){this.bookModel.setIsbn(ISBN);}
    private void buildTitle(String title){this.bookModel.setTitle(title);}
    private void buildnoOfCopies(int noOfCopies){this.bookModel.setNoOfCopies(noOfCopies);}
    private void buildcategoryName(String categoryName){this.bookModel.setCategoryName(categoryName);}
    private void buildPrice(double price){this.bookModel.setPrice(price);}
    private void buildImage(String image){this.bookModel.setImage(image);}
    private void buildpublicationYear(String publicationYear){this.bookModel.setPublicationYear(publicationYear);}
    private void buildthreshold(int threshold){this.bookModel.setThreshold(threshold);}
    private void buildpublisherName(String publisherName){this.bookModel.setPublisherName(publisherName);}
    @Override
    public boolean comopse(String sentData) {
        this.bookModel=new Book();
        try {
            JSONObject obj = new JSONObject(sentData);
            buildISBN(obj.getString("isbn"));
            buildTitle(obj.getString("title"));
            buildnoOfCopies(obj.getInt("noOfCopies"));
            buildcategoryName(obj.getString("categoryName"));
            buildPrice(obj.getDouble("price"));
            buildImage(obj.getString("image"));
            buildpublicationYear(obj.getString("publicationYear"));
            buildthreshold(obj.getInt("threshold"));
            buildpublisherName(obj.getString("publisherName"));
        } catch (JSONException e) {
            System.out.println("book problem");
            return false;
        }
        return true;
    }

    @Override
    public Imodel getModel() {
        return this.bookModel;
    }
}
