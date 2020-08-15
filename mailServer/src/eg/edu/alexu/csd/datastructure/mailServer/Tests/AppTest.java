package eg.edu.alexu.csd.datastructure.mailServer.Tests;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class AppTest {

//	@Test
//	void signupTest() {
//		Contact test = new Contact();
//		test.setEmail("fdgdf@ysfl.com");
//		test.setPassword("44454564");
//		test.setGender("Male");
//		test.setName("Seif");
//		test.setDate("212/55/555");
//		App f=new App();
//		assertTrue(f.signup(test));
//	}
//	@Test
//	void isAvailableTest() {
//		assertFalse(FileTools.isAvailableEmail("fdgdfm"));
//	}
//	@Test 
//	void SignIn() {
//		App f=new App();
//		f.signin("fdgdf@ysfl.com","44454564");
//		assertEquals("44454564",f.user.getPassword());
//	}
	@Test 
	void composeRight() {
		App f=new App();
		Mail mail = new Mail();
		mail.receivers.enqueue("seifgneedy@fmail.com");
//		mail.receivers.enqueue("seifgn2@gldl.com");
//		mail.receivers.enqueue("fdgdf@ysfl.com");
		mail.setMailBody("we\nwill\ngo\nand\nfuck\nourselvessflsdjfklsdjfklsjdklfjskldjfklasjdklfjklasdjflkjdsklfjkasdljf");
		mail.setTime(System.currentTimeMillis());
		mail.setSubject("hello world");
		mail.setSender("ans@fmail.com");
		//mail.attachments.add(new File("C:/Users/seifg/Desktop/احمد خالد توفيق/KAT2018/KAT43.pdf"));
		assertTrue(f.compose(mail));
	}
	/*@Test 
	void mailFiles() {
		Mail mail = new Mail();
		mail.setMailBody("we\nwill\ngo\nand\nfuck\nourself");
		mail.setTime(System.currentTimeMillis()/1000);
		mail.setSubject("hello world");
		mail.receivers.enqueue("seifgn2@gldl.com");
		mail.receivers.enqueue("seifgneedy@gmail.com");
		mail.attachments.add(new File("Users/users.txt"));
		FileTools.createMailFiles(mail, "seifgneedy@Fmail.com", "Sent");
	}*/
}







