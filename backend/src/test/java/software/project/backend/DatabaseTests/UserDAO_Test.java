package software.project.backend.DatabaseTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import software.project.backend.Database.UserDAO;
import software.project.backend.Model.User;
import software.project.backend.Model.builder.Director;
import software.project.backend.sercuirty.passwordOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAO_Test {
    private final Director director = new Director();
    private final passwordOperations passwordOPS = new passwordOperations();
    private UserDAO userDAO = new UserDAO();

    @Order(1)
    @Test
    void resetUsersTest() {
        System.out.println(userDAO.deleteAllCustomers());
    }

    @Order(2)
    @Test
    void insertUserTest() {
        String dataSent = "{\n" +
                "   \"userName\":\"ziadElabd\",\n" +
                "    \"firstName\":\"ziad\",\n" +
                "    \"lastName\":\"elabd\",\n" +
                "    \"password\":\"123\",\n" +
                "    \"address\":\"Alex\",\n" +
                "    \"phone\":\"123456\"\n" +
                "}";
        User user = (User) director.composeModel("user",dataSent);
        assertTrue(userDAO.insertUser(user));
    }

    @Order(3)
    @Test
    void isUserNameExistTest() {
        assertTrue(userDAO.isUserNameExist("ziadElabd"));
    }

    @Order(4)
    @Test
    void checkSignInTest1() {
        String userName = "admin";
        String password = passwordOPS.passswordToHash("123456");
        User user = userDAO.checkSignIn(userName, password);
        assertEquals(user.getRole(),1);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
    }

    @Order(4)
    @Test
    void checkSignInTest2() {
        String userName = "ziadElabd";
        String password = passwordOPS.passswordToHash("123");
        User user = userDAO.checkSignIn(userName, password);
        assertEquals(user.getRole(),0);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
    }


    @Order(5)
    @Test
    void changePasswordTest1() {
        String userName = "ziadElabd";
        String password = passwordOPS.passswordToHash("123456");
        assertTrue(userDAO.changePassword(userName, password));
        User user = userDAO.checkSignIn(userName, password);
        assertEquals(user.getRole(),0);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
    }

    @Order(6)
    @Test
    void getAdminsTest(){
        List<String> admins = userDAO.getAdmins();
        for(String admin : admins){
            System.out.println(admin);
        }
    }

//    @Test
//    void deleteAdminTest(){
//        assertTrue(userDAO.deleteAdmin("ziad"));
//    }

    @Order(7)
    @Test
    void updateUserSettingsTest(){
        String dataSent = "{\n" +
                "   \"userName\":\"ziadElabd\",\n" +
                "    \"firstName\":\"zezo\",\n" +
                "    \"lastName\":\"elabd\",\n" +
                "    \"password\":\"123\",\n" +
                "    \"address\":\"Alex\",\n" +
                "    \"phone\":\"123456\"\n" +
                "}";
        User user = (User) director.composeModel("user",dataSent);
        assertTrue(userDAO.updateUserSettings("ziadElabd", user));
    }

}
