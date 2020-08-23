package eg.edu.alexu.csd.datastructure.mailServer.Tests;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;

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
		assertFalse(f.signup(test));
	}
	@Test
	void isAvailableTest() {
		assertFalse(FileTools.isAvailableEmail("fdgdfm"));
	}
	@Test 
	void SignIn() {
		App f=new App();
		f.signin("fdgdf@ysfl.com","44454564");
		assertEquals("44454564",f.user.getPassword());
	}
	SLinkedList prepareEmails() {
		long time = System.currentTimeMillis();
		SLinkedList emails = new SLinkedList();
		SLinkedList list;
		// creating emails
		Mail mail;
		
		mail = new Mail();
		mail.setTime(time - 1000L * 4000);
		mail.setAttachments(new SLinkedList());
		mail.setSender("Seif");
		mail.setSubject("Completing project");
		emails.add(mail);
		
		mail = new Mail();
		mail.setTime(time - 1000L * 87000);
		list = new SLinkedList();
		list.add(1);
		mail.setAttachments(list);
		mail.setSender("Ans");
		mail.setSubject("Finishing project");
		emails.add(mail);
		
		mail = new Mail();
		mail.setTime(time - 1000L * 605000);
		list = new SLinkedList();
		list.add(1);
		list.add(2);
		mail.setAttachments(list);
		mail.setSender("Samer");
		mail.setSubject("Playing PUBG");
		emails.add(mail);
		
		mail = new Mail();
		mail.setTime(time - 1000L * 2600000);
		list = new SLinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		mail.setAttachments(list);
		mail.setSender("Mr. X");
		mail.setSubject("Please finish the project");
		emails.add(mail);
		
		return emails;
	}
	@Test
	void filterTest() {
		SLinkedList emails;
		Filter filter = new Filter();
		
		
		// Testing time
		
		emails = prepareEmails();
		filter.setFromTime(Filter.ALL);
		filter.filter(emails);
		assertEquals(4 , emails.size());
		
		filter.setFromTime(Filter.ONE_MONTH);
		filter.filter(emails);
		assertEquals(3 , emails.size());
		
		filter.setFromTime(Filter.ONE_WEEK);
		filter.filter(emails);
		assertEquals(2 , emails.size());
		
		filter.setFromTime(Filter.ONE_DAY);
		filter.filter(emails);
		assertEquals(1 , emails.size());
		
		filter.setFromTime(Filter.ONE_HOUR);
		filter.filter(emails);
		assertEquals(0 , emails.size());
		
		filter.setFromTime(Filter.ALL);
		
		// Testing Attachments
		emails = prepareEmails();
		
		filter.setHasAttachmentsOnly(true);
		filter.filter(emails);
		assertEquals(3 , emails.size());
		
		filter.setHasAttachmentsOnly(false);
		
		// Testing Sender
		
		emails = prepareEmails();
		
		filter.setSender("Ans");
		filter.filter(emails);
		
		filter.setSender("");
		
		// Testing Subject 
		emails = prepareEmails();
		
		filter.setSubject("project");
		filter.filter(emails);
		assertEquals(3 , emails.size());
		
		filter.setSubject("");
	}
	
	@Test
	void sortingTest() {
		SLinkedList emails;
		Sort sort;
		emails = prepareEmails();
		
		sort = new Sort(SORTING.NEWEST);
		sort.quickSort(emails);
		assertTrue("Seif".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.OLDEST);
		sort.quickSort(emails);
		assertTrue("Mr. X".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.ASCENDING_SUBJECT);
		sort.quickSort(emails);
		assertTrue("Seif".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.DESCENDING_SUBJECT);
		sort.quickSort(emails);
		assertTrue("Mr. X".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.ASCENDING_SENDER);
		sort.quickSort(emails);
		assertTrue("Ans".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.DESCENDING_SENDER);
		sort.quickSort(emails);
		assertTrue("Seif".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.ASCENDING_N_ATTACH);
		sort.quickSort(emails);
		assertTrue("Seif".equals( ((Mail) emails.get(0)).getSender()));
		
		sort = new Sort(SORTING.DESCENDING_N_ATTACH);
		sort.quickSort(emails);
		assertTrue("Mr. X".equals( ((Mail) emails.get(0)).getSender()));
	}
//	@Test 
//	void composeRight() {
//		App f=new App();
//		Mail mail = new Mail();
//		mail.receivers.enqueue("seifgneedy@Fmail.com");
//		mail.receivers.enqueue("seifgn2@gldl.com");
//		mail.receivers.enqueue("fdgdf@ysfl.com");
//		mail.setMailBody("we\nwill\ngo\nand\nfuck\nourself");
//		mail.setTime(System.currentTimeMillis()/1000);
//		mail.setSubject("hello world");
//		mail.setSender("seifgneedy@gmail.com");
//		mail.attachments.add(new File("C:/Users/seifg/Desktop/احمد خالد توفيق/KAT2018/KAT43.pdf"));
//		assertTrue(f.compose(mail));
//	}
	
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







