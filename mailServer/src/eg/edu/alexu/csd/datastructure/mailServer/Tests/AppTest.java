package eg.edu.alexu.csd.datastructure.mailServer.Tests;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.Contact;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.FileTools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

	@Test
	void signupTest() {
		Contact test = new Contact();
		test.setEmail("fdgdf@ysfl.com");
		test.setPassword("44454564");
		test.setGender("Male");
		test.setName("Seif");
		test.setDate("212/55/555");
		App f=new App();
		assertTrue(f.signup(test));
	}
	@Test
	void isAvailableTest() {
		assertFalse(FileTools.isAvailableEmail("fdgdfm"));
	}
	@Test 
	void SignIn() {
		App f=new App();
		f.signin("fdgdf@ysfl.com","44454564");
		assertEquals("44454564",f.userContact.getPassword());
	}
}
