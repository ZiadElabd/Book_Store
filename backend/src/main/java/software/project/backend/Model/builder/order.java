package software.project.backend.Model.builder;

import org.json.JSONException;
import org.json.JSONObject;

public class order {
    private  int orderID;
    private int noOfCopies;
    private String isbn;
    public order(){}
    public order(String sentData){
        try {
            JSONObject obj = new JSONObject(sentData);
            this.setIsbn(obj.getString("isbn"));
            this.setNoOfCopies(obj.getInt("noOfCopies"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
