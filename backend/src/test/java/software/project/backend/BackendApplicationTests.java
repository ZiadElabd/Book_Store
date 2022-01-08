package software.project.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import software.project.backend.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void signupTest() {
		AuthenticationService n=new AuthenticationService();
		for (int i = 0; i <1000 ; i++) {
			assertTrue(n.signUp("{\n" +
					"   \"userName\":\"ziadaa\",\n" +
					"    \"firstName\":\"ziad\",\n" +
					"    \"lastName\":\"albana\",\n" +
					"    \"password\":\"123\",\n" +
					"    \"Address\":\"aaaaa\",\n" +
					"    \"phone\":\"11111\"\n" +
					"}"));
		}
	}
	@Test
	void checkUserTest1() {
		AuthenticationService n=new AuthenticationService();
		assertFalse(n.checkUserName("ziadaaa"));
	}
	@Test
	void checkUserTest2() {
		AuthenticationService n=new AuthenticationService();
		assertTrue(n.checkUserName("ziad123"));
	}





}
