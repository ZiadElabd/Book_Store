package software.project.backend.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import software.project.backend.Database.BookDAO;
import software.project.backend.Database.UserDAO;
import software.project.backend.Model.Book;
import software.project.backend.Model.User;
import software.project.backend.Model.builder.Director;
import software.project.backend.sercuirty.Singelton;
import software.project.backend.sercuirty.passwordOperations;

import java.util.List;

public class userService {
    private Director director=new Director();
    private UserDAO userOperation=new UserDAO();
    @Autowired
    private BookDAO productOperation=new BookDAO();
    private Singelton trackingSystem=Singelton.getInstance();
    private passwordOperations passwordOperations=new passwordOperations();
    public List<Book> search(String sessionID, String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        String cateogry="";
        String type="";
        String searchText="";
        if (userName==null) return null;
        try {
            JSONObject obj = new JSONObject(dataSent);
            cateogry=obj.getString("categoryName");
            type=obj.getString("type");
            searchText=obj.getString("searchText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (type=="ISBN"){
            return productOperation.searchWithISBN(searchText,cateogry);
        }else if (type=="Title"){
            return productOperation.searchWithTitle(searchText,cateogry);
        }else if(type=="Author"){
            return productOperation.searchWithAuthorName(searchText,cateogry);
        }
        else {
            return productOperation.searchWithpublisherName(searchText,cateogry);
        }
    }
    public boolean addToCart(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        String

        try {
            JSONObject obj = new JSONObject(dataSent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
