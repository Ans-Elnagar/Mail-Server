package eg.edu.alexu.csd.datastructure.mailServer.Tests;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.Contact;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {

	@Test
	void signupTest() {
		App app = new App();
		Contact user = new Contact();
		assertEquals(false, app.signin("an@g.com","He125"));
	}

}
