package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;

import java.io.*;
import java.util.Scanner;
public class App implements IApp {
	public Contact user= new Contact();
	public SLinkedList mails=new SLinkedList();
	@Override
	public boolean signin(String email, String password) {
		File users = new File("Users/users.txt");
		try (
				Scanner input = new Scanner(users);
		){
			while(input.hasNextLine()) {
				String currentEmail = input.nextLine();
				String currentPassword = input.nextLine();
				if(email.equals(currentEmail)) {
					if(password.equals(currentPassword)) {
						//load contact data from the his user file into contact object
						FileTools.setContactInfo(currentEmail,user);
						return true;
					}
					else
						return false;
				}
			}
		} catch (FileNotFoundException e) { }
		return false;
	}

	@Override
	public boolean signup(IContact contact) {
		//Validation of every input is in GUI classes
		//create users folder and users.txt if it's not created
		Contact data = (Contact) contact;
		File users = new File("Users/users.txt");
		if( ! users.exists() )
			FileTools.createFile(users);
 		if(FileTools.isAvailableEmail(data.getEmail()))
			return false;
		//first userFolder and user.txt and friends.txt which hold contact Info
		File userFolder = FileTools.createUserTxtAndDir(data);
 		//create folders and text files
 		FileTools.createUserFiles(userFolder);
 		FileTools.addEmailAndPass(data.getEmail(), data.getPassword());
 		//adding default profile pic
 		FileTools.copyFiles(new File("icons/Profile.jpg"),userFolder);
 		return true;
	}

	@Override
	public void setViewingOptions(Folder folder, IFilter filter, ISort sort) {
		mails=FileTools.loadMailsToList(folder);
		Filter currentFilter = (Filter) filter;
		currentFilter.filter(mails);
		Sort currentSort=(Sort) sort;
		currentSort.quickSort(mails);
	}

	@Override
	public IMail[] listEmails(int page) {
		Mail[] mailPage=new Mail[10];
		int start =10*(page-1);
		for(int i=0,j=start;i<10&&j<mails.size();i++,j++)
			mailPage[i]=(Mail) mails.get(j);
		return mailPage;
	}

	@Override
	public void deleteEmails(ILinkedList mails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		
		
	}

	@Override
	public boolean compose(IMail email) {
		Mail mail= (Mail) email;
		// first , check that all receivers are real and there's no repeat 
		// and not sending to himself
		String all="";
		for(int i=0;i<mail.receivers.size();i++) {
			String curr=(String)mail.receivers.dequeue();
			if((!FileTools.isAvailableEmail(curr))||all.contains(curr)||curr.equals(mail.getSender()))
				return false;
			all+=curr+" ";
			mail.receivers.enqueue(curr);
		}
		// why ? IDK
		all=null;
		// then saving this mail as folder at sent folder
		FileTools.createMailFiles(mail,mail.getSender(),"Sent");
		// then saving this mail Files also at receivers Folders
		for(int i=0;i<mail.receivers.size();i++) {
			FileTools.createMailFiles(mail,(String) mail.receivers.peek(),"Inbox");
			mail.receivers.enqueue(mail.receivers.dequeue());
		}
		/////////////////////////////
		return true;
	}

}
