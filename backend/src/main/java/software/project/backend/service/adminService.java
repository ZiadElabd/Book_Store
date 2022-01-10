package software.project.backend.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import software.project.backend.Database.BookDAO;
import software.project.backend.Database.UserDAO;
import software.project.backend.Model.Book;
import software.project.backend.Model.User;
import software.project.backend.Model.builder.Director;
import software.project.backend.sercuirty.Singelton;
import software.project.backend.sercuirty.passwordOperations;

import java.util.List;

public class adminService {
    private Director director=new Director();
    private UserDAO userOperation=new UserDAO();
    @Autowired
    private BookDAO productOperation=new BookDAO();
    private Singelton trackingSystem=Singelton.getInstance();
    private passwordOperations passwordOperations=new passwordOperations();
    public boolean addProduct(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        Book product=(Book) director.composeModel("book",dataSent);
        return productOperation.insertProduct(product);
    }
    public boolean UpdateProduct(String sessionID,int productID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        Book product=(Book) director.composeModel("book",dataSent);
        return productOperation.updateProduct(product,productID);
    }
    public boolean deleteProduct(String sessionID,String productID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        return productOperation.deleteProduct(Integer.getInteger(productID));
    }

    public List<Book> getAllProductByCategroy(String sessionID,String categroy){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return productOperation.getProductByCategory(categroy);
    }
    public Book getProductByID(String sessionID,String productID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return productOperation.getProductByID(productID);
    }
    public User getAdminInfo(String sessionID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return userOperation.getUserSettings(userName);
    }
    public boolean updateAdminInfo(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        User admin= (User) director.composeModel("user",dataSent);
        userOperation.updateUserSettings(userName,admin);
        return true;
    }
    public boolean addAdmin(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        User secondAdmin = (User) director.composeModel("user",dataSent);
        secondAdmin.setRole(2);
        userOperation.insertUser(secondAdmin);
        return true;
    }
    public boolean deleteAdmin(String sessionID,String adminName){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
       return userOperation.deleteAdmin(adminName);
    }
    public List<String> getAllAdmins(String sessionID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return userOperation.getAdmins();

    }
    public boolean changePassword(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        String oldPassword ="";
        String newPassword="";
        if (userName==null) return false;
        try {
            JSONObject obj = new JSONObject(dataSent);
            oldPassword= passwordOperations.passswordToHash(obj.getString("oldPassword"));
            newPassword= passwordOperations.passswordToHash(obj.getString("newPassword"));
            System.out.println(oldPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(userOperation.checkSignIn(userName,oldPassword)==null) return false;
        return userOperation.changePassword(userName,newPassword);
    }


}
