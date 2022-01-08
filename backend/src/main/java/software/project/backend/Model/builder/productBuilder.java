package software.project.backend.Model.builder;

import org.json.JSONException;
import org.json.JSONObject;
import software.project.backend.Model.Imodel;
import software.project.backend.Model.Product;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;


public class productBuilder implements Ibuilder {
    private Product productModel;
    private void buildName(String name){this.productModel.setName(name);}
    private void buildCategoryName(String categoryName){this.productModel.setCategoryName(categoryName);}
    private void buildDescription(String description){this.productModel.setDescription(description);}
    private void buildPrice(double price){this.productModel.setPrice(price);}
    private void buildQuantity(int quantity){this.productModel.setQuantity(quantity);}
    private void buildDiscount(double discount){this.productModel.setDiscount(discount);}
    private void buildImage(String image){
      /*  Blob b = null;
        try {
            byte[] decodedByte = Base64.getDecoder().decode(image);
             b= new SerialBlob(decodedByte);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        this.productModel.setImage(image);
    }
    @Override
    public boolean comopse(String sentData) {
        this.productModel=new Product();
        try {
            JSONObject obj = new JSONObject(sentData);
            buildName(obj.getString("name"));
            buildCategoryName(obj.getString("categoryName"));
            buildDescription(obj.getString("description"));
            buildPrice(obj.getDouble("price"));
            buildQuantity(obj.getInt("quantity"));
            buildDiscount(obj.getDouble("discount"));
            buildImage(obj.getString("image"));
        } catch (JSONException e) {
            System.out.println("product problem");
            return false;
        }
        return true;
    }

    @Override
    public Imodel getModel() {
        return this.productModel;
    }
}
