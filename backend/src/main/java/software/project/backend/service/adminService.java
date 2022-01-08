package software.project.backend.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import software.project.backend.Database.ProductDAO;
import software.project.backend.Database.UserDAO;
import software.project.backend.Model.Product;
import software.project.backend.Model.User;
import software.project.backend.Model.builder.Director;
import software.project.backend.sercuirty.Singelton;
import software.project.backend.sercuirty.passwordOperations;

import java.util.List;

public class adminService {
    private Director director=new Director();
    private UserDAO userOperation=new UserDAO();
    @Autowired
    private ProductDAO productOperation=new ProductDAO();
    private Singelton trackingSystem=Singelton.getInstance();
    private passwordOperations passwordOperations=new passwordOperations();
    public boolean addProduct(String sessionID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        Product product=(Product) director.composeModel("product",dataSent);
        return productOperation.insertProduct(product);
    }
    public boolean UpdateProduct(String sessionID,int productID,String dataSent){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        Product product=(Product) director.composeModel("product",dataSent);
        return productOperation.updateProduct(product,productID);
    }
    public boolean deleteProduct(String sessionID,String productID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return false;
        return productOperation.deleteProduct(0);
    }

    public List<Product> getAllProductByCategroy(String sessionID,String categroy){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return productOperation.getProductByCategory(categroy);
    }
    public Product getProductByID(String sessionID,int productID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        return productOperation.getProductByID(productID);
    }
    public User getAdminInfo(String sessionID){
        String userName=trackingSystem.checkAcess(sessionID);
        if (userName==null) return null;
        System.out.println("admin-"+userName);
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
