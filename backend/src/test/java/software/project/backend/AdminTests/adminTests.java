package software.project.backend.AdminTests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import software.project.backend.service.AuthenticationService;
import software.project.backend.service.adminService;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class adminTests {
    adminService n=new adminService();
    AuthenticationService m=new AuthenticationService();
    @Order(1)
     @Test
       void addNewAdmintest(){
           String temp="{userName:admin,password:123456}";
           JSONObject jsonReply=m.signIn(temp);
           String sessionID = null;
           try {
               sessionID=jsonReply.getString("sessionID");
           } catch (JSONException e) {
               e.printStackTrace();
           }
            temp="{firstName:ziad,lastName:Allbana,userName:admin2,password:1234567,phone:1111111111,address:alex}";
           assertTrue(n.addAdmin(sessionID,temp));
       }
    @Order(2)
    @Test
    void addNewAdmintest2(){
        String temp="{userName:admin,password:123456}";
        JSONObject jsonReply=m.signIn(temp);
        String sessionID = null;
        try {
            sessionID=jsonReply.getString("sessionID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        temp="{firstName:ziad,lastName:Allbana,userName:admin3,password:1234567,phone:1111111111,address:alex}";
        assertTrue(n.addAdmin(sessionID,temp));
    }
    @Order(3)
     @Test
    void updateAdminSetting(){
         String admin="{userName:admin,password:123456}";
         JSONObject jsonReply=m.signIn(admin);
         String sessionID = null;
         try {
             sessionID=jsonReply.getString("sessionID");
         } catch (JSONException e) {
             e.printStackTrace();
         }
         String temp="{\"userId\":1,\"userName\":\"admin\",\"firstName\":\"ziad1\",\"lastName\":\"elabd\",\"password\":null,\"address\":\"Hosh Isa\",\"phone\":\"111111\",\"role\":1}";
         assertTrue(n.updateAdminInfo(sessionID,temp));
     }
    @Order(4)
     @Test
    void getAllAdmins(){
         String admin="{userName:admin,password:123456}";
         JSONObject jsonReply=m.signIn(admin);
         String sessionID = null;
         try {
             sessionID=jsonReply.getString("sessionID");
         } catch (JSONException e) {
             e.printStackTrace();
         }
         assertEquals(n.getAllAdmins(sessionID), Arrays.asList("admin2","admin3"));
     }
    @Order(5)
    @Test
    void deleteAdmin(){
        String admin="{userName:admin,password:123456}";
        JSONObject jsonReply=m.signIn(admin);
        String sessionID = null;
        try {
            sessionID=jsonReply.getString("sessionID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertTrue(n.deleteAdmin(sessionID,"admin2"));
        assertTrue(n.deleteAdmin(sessionID,"admin3"));
    }

}
