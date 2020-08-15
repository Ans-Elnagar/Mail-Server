package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
public class FileTools {
	public static File createFile(File file) {
		createFolder(file.getParentFile());
		try {
			file.createNewFile();
			return file;
		}catch(IOException e) {
			return null;
		}
	}
	public static boolean createFolder(File folder) {
		return folder.mkdir();
	}
	/////////////////////////////////////////////////////////////////////////
	/*
	 * check if the e-mail is already exists or not
	 * @param mail -> the entered mail , destination -> where the text file is !
	 * @return true if the mail is not here
	 */
	public static boolean isAvailableEmail(String email) {
		try( Scanner input = new Scanner(new File("Users/users.txt"));){
			while(input.hasNextLine()) {
				String currentEmail = input.nextLine();
				
				input.nextLine();// Don't need the password
				if(email.equals(currentEmail))
					return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static File createUserTxtAndDir(Contact contact) {
		/// the user directory which named after the e-mail
		File userFolder =new File("Users/"+contact.getEmail());
		createFolder(userFolder);
		// creating user.txt
		File user = createFile(new File(userFolder.getPath() + "/user.txt"));
		File friends = createFile(new File(userFolder.getPath() + "/friends.txt"));
		try (
				PrintWriter userOutput = new PrintWriter(user);
				PrintWriter friendsOutput = new PrintWriter(friends);
			) {
			userOutput.println(   contact.getName()+"\n"
								+ contact.getEmail() + "\n"
								+ contact.getPassword() + "\n"
								+ contact.getDate() + "\n"
								+ contact.getGender());
			friendsOutput.print(0);
		} catch (FileNotFoundException e) { }
		return userFolder;
	}
		//////////////////////////////////////
	//////////////////////////////////
	// friends.txt{
	//  #no of friends
	//	name of the friend
	//	#no of e-mails he have
	//  every email in single line
	// }
	//////////////////////
	//////////
	public static void createUserFiles(File folder) {
		String[] names= {"Inbox","Sent","Draft","Trash"};
		for(String name: names) {
			//creating txtFile in Folders to hold mails Info
			createFile(new File( folder.getPath() + "/" + name + "/indexFile.txt"));
		}
	}
	
	public static void addEmailAndPass(String email, String pass) { 
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Users/users.txt", true)); 
			out.write(email+"\n"+pass+"\n");
			out.close();
		} catch (IOException e) { } 
	}
	//////////////////////
	/////////////////////
	// getContactInfo from user.txt and friends.txt file and save it in the contact object
	public static void setContactInfo(String mail,Contact contact) {
		try (Scanner user = new Scanner(new File("Users/"+mail+"/user.txt"));
			Scanner friends = new Scanner(new File("Users/"+mail+"/friends.txt"));){
			// loading user main info from user.txt 
			contact.setName(user.nextLine());
			contact.setEmail(user.nextLine());
			contact.setPassword(user.nextLine());
			contact.setDate(user.nextLine());
			contact.setGender(user.nextLine());
			//loading user friends from friends.txt
			int noOfFriends= Integer.parseInt(friends.next());
			while(noOfFriends-->0) {
				// creating a friend object and fill it's info
				Friend friend = new Friend(friends.next());
				int noOfEmails = Integer.parseInt(friends.next());
				while(noOfEmails-->0) 
					friend.addEmail(friends.next());
				// adding this friend to the friends list 
				contact.friends.add(friend);
			}
		} catch (FileNotFoundException e) {
			return;
		}	
	}
	///////////////////
	/*
	 indexFile{
		 //every line with one mail info 
		 subject time senderMail #no of receivers "receivers mails one by one" #no of attachments "names of attachments"
		 //mail Folder of every mail named after "subject time"
		 //mail body is on dest+subject+" "+time+mailBody.txt
	 }*/
	public static void createMailFiles(Mail mail,String user,String folder) {
		//creating mail main folder
		File mailFolder = new File("Users/"+user+"/"+folder+"/"+
				mail.getSubject()+" "+mail.getTime());
		mailFolder.mkdir();
		//adding info to indexFile.txt
		writingInFile(new File(mailFolder.getParent()+"/indexFile.txt"),indexFileString(mail,user));
		// create mailBody.txt and append the message
		writingInFile(new File(mailFolder.getPath()+"/mailBody.txt"),mail.getMailBody());
		// copying attachments to its folder
		File attachFolder = new File(mailFolder.getPath()+"/Attachments");
		attachFolder.mkdir();
		for(int i=0;i<mail.attachments.size();i++) {
			copyFiles((File) mail.attachments.get(i),attachFolder);
		}
		
	}
	/// a method to write in files
	public static void writingInFile(File main,String text) {
		//creating the file if not exist
		if(!main.exists())
			createFile(main);
		// writing in the file
		 try(BufferedWriter out = new BufferedWriter(new FileWriter(main.getPath(), true));) {
			 out.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	// creating the string required to be added to index file
	public static String indexFileString(Mail mail,String additionUser) {
		String result=mail.getSubject()+","+mail.getTime()+","+mail.getSender()+",";
			result+=mail.receivers.size()+",";
		for(int i=0;i<mail.receivers.size();i++) {
			String receiver =(String) mail.receivers.dequeue();
			result+=receiver+",";
			mail.receivers.enqueue(receiver);
		}
		// then attachments names
		result+=mail.attachments.size();
		for(int i=0;i<mail.attachments.size();i++)
			result+=","+((File)mail.attachments.get(i)).getName();
		result+=","+mail.getImportance();
		return result+"\n";
	}
	//////////////////
	//////////////////
	////Copying Files///
	//////////////////
	public static void copyFiles(File required,File destFolder) {
		Path to = Paths.get(destFolder.getPath()+"/"+required.getName());
		try {
			Files.copy(required.toPath(), to);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static SLinkedList loadMailsToList(Folder folder) {
		SLinkedList mails=new SLinkedList();
		try{
			Scanner in=new Scanner(new File(folder.getPath()+"/indexFile.txt"));
			while(in.hasNext()) {
				String [] line=in.nextLine().split(",");
				if(line.length<2)
					break;
				Mail mail=new Mail();
				mail.setSubject(line[0]);
				mail.setTime(Long.parseLong(line[1]));
				mail.setSender(line[2]);
				int noOfReceivers=Integer.parseInt(line[3]);
				LinkedQueue receivers=new LinkedQueue();
				int i=4;
				for(int j=0;j<noOfReceivers;j++) 
					receivers.enqueue(line[i++]);
				mail.receivers=receivers;
				SLinkedList attachs=new SLinkedList();
				int noOfAttachs=Integer.parseInt(line[i++]);
				for(int j=0;j<noOfAttachs;j++)
					attachs.add(line[i++]);
				mail.setAttachments(attachs);
				mail.setImportance(Integer.parseInt(line[i]));
				mail.setMailBody(readFile(folder.getPath()+"/"+mail.getSubject()+" "+mail.getTime()+"/mailBody.txt"));
				mail.setMailDir(folder.getFolder());
				mails.add(mail);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mails;
	}
	public static String readFile(String path) {
		String result="";
		try(Scanner input =new Scanner(new File(path));){
			while(input.hasNextLine())
				result+=input.nextLine()+"/n";
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}




