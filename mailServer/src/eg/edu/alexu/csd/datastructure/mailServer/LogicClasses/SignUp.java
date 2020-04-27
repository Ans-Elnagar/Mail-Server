package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.io.*;
import java.util.Scanner;
public class SignUp {
	/*
	 * check if the e-mail is already exists or not
	 * @param mail -> the entered mail , destination -> where the txt file is !
	 * @return true if the mail is not here
	 */
	public static boolean checkmail(String mail,File destination) {
		try(Scanner usersTxt = new Scanner(destination)){
			String read;
			while((read=usersTxt.nextLine())!=null) {
				if(mail.equals(read))
					return false;
			usersTxt.nextLine();
			}
			usersTxt.close();
		} catch (FileNotFoundException e) {
			System.out.println("a lot of try and catch , file not found");
			e.printStackTrace();
		}
		
		return true;
	}
	public static File createUserTxtAndDir(File mainFolder,Contact contact) {
		/// the user directory which named after the e-mail
		File user =new File(mainFolder,contact.getE_mail());
		user.mkdir();
		// creating user.txt
		File userTxt = new File(user,"User.txt");
		try {
			userTxt.createNewFile();
			PrintWriter printContact= new PrintWriter(userTxt);
			printContact.printf("%s\n%s\n%s\n%s\n%s\n",contact.getName(),contact.getE_mail(),
					contact.getPassword(),contact.getDate(),contact.getGender());
			printContact.close();
		} catch (IOException e) {
			System.out.println("Again , what to do here !");
			e.printStackTrace();
		}
		//creating friends.txt which contains 0 friends
		File friends = new File(user,"Friends.txt");
		try {
			friends.createNewFile();
			PrintWriter print = new PrintWriter(friends);
			print.print(0);
			print.close();
		} catch (IOException e) {
			System.out.println("Again , what to do here !");
			e.printStackTrace();
		}
		return user;
	}
		//////////////////////////////////////
	//////////////////////////////////\
	//////////////////////
	//////////
	public static void createUserFiles(File folder) {
		File[] basicFolders = new File[4];
		String[] names= {"Inbox","Sent","Draft","Trash"};
		for(int i=0;i<4;i++) {
			//creating folders
			basicFolders[i]=new File(folder,names[i]);
			basicFolders[i].mkdir();
			//creating txtFile in Folders to hold mails Info
			File msgInfo = new File(basicFolders[i],"Info.txt");
			try {
				msgInfo.createNewFile();
			} catch (IOException e) {
				System.out.println("errors every where,the file can't be made");
				e.printStackTrace();
			}
		}
			
	}
}
