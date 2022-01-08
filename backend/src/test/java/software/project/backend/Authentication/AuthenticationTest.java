package software.project.backend.Authentication;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import software.project.backend.service.AuthenticationService;
import software.project.backend.service.adminService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationTest {
    AuthenticationService n=new AuthenticationService();
    adminService m=new adminService();
    @Order(1)
    @Test
    void adminSignIn1(){
        String temp="{userName:admin,password:123456}";
        assertNotEquals(n.signIn(temp),null);
    }
    @Order(2)
    @Test
    void adminSignIn2(){
        String temp="{userName:admn,password:123456}";
        assertEquals(n.signIn(temp),null);
    }
    @Order(3)
    @Test
    void adminSignIn3(){
        String temp="{userName:admn,password:1236}";
        assertEquals(n.signIn(temp),null);
    }
    @Order(4)
    @Test
    void userSignUp(){
        String temp="{\"firstName\":\"user1\",\"lastName\":\"user\",\"userName\":\"usertest\",\"password\":\"123456\",\"phone\":\"1111\",\"address\":\"alex\"}";
        assertTrue(n.signUp(temp));
    }
    @Order(5)
    @Test
    void userDelete(){
        String admin="{userName:admin,password:123456}";
        JSONObject jsonReply=n.signIn(admin);
        String sessionID = null;
        try {
            sessionID=jsonReply.getString("sessionID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        m.deleteAdmin(sessionID,"usertest");
    }
}
