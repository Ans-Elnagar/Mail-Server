package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.io.*;
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
			createFile(new File( folder.getPath() + "/" + name + "/info.txt"));
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
}
