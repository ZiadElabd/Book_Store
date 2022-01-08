package software.project.backend.Model.builder;

import org.json.JSONException;
import org.json.JSONObject;
import software.project.backend.Model.Imodel;
import software.project.backend.Model.User;
import software.project.backend.sercuirty.passwordOperations;


public class userBuilder implements Ibuilder {
   private User user;
   private passwordOperations passwordOperations=new passwordOperations();

       private void buildUserName(String userName) {
        this.user.setUserName(userName);
       }

       private void buildFirstName(String firstName) {
        this.user.setFirstName(firstName);
       }

       private void buildLastName(String lastName) {
        this.user.setLastName(lastName);
       }

       private void buildPassword(String password) {
        if (password!=null) this.user.setPassword(passwordOperations.passswordToHash(password));
        else this.user.setPassword(null);
       }

       private void buildAddress(String Address) {
        this.user.setAddress(Address);
       }

       private void buildPhone(String phone) {
        this.user.setPhone(phone);
       }
       private void buildRole(int role){this.user.setRole(role);}

       @Override
       public boolean comopse(String sentData) {
        user = new User();
        try {
         JSONObject obj = new JSONObject(sentData);
         buildUserName(obj.getString("userName"));
         buildFirstName(obj.getString("firstName"));
         buildLastName(obj.getString("lastName"));
         buildPassword(obj.getString("password"));
         buildAddress(obj.getString("address"));
         buildPhone(obj.getString("phone"));
         buildRole(0);
        } catch (JSONException e) {
         System.out.println("user problem");
         return false;
        }
        return true;
       }

       @Override
       public Imodel getModel() {
        return this.user;
       }

}