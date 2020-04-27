package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;
//importing in static way to use the methods without making an object
import static eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.SignUp.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class App implements IApp {
	@Override
	public boolean signin(String email, String password) {
		boolean signed=false;
		FileReader read=null;
		BufferedReader br=null;
		try {
			read = new FileReader("Users\\user.txt");
			br = new BufferedReader(read);
			String mail,pass;
			while((mail=br.readLine())!=null) {
				pass=br.readLine();
				if(email==mail&&password==pass) {
					signed=true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				read.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return signed;
	}

	@Override
	public boolean signup(IContact contact) {
		//Validation of every input is in GUI classes
		//create users folder and users.txt if it's not created 
		File mainFolder= new File("Users");
		mainFolder.mkdir();
		File usersInfo = new File(mainFolder,"users.txt");
		try {
			usersInfo.createNewFile();
		} catch (IOException e) {
			System.out.println("Error occured while creating the file");
			e.printStackTrace();
		}
 		if(!checkmail(((Contact) contact).getE_mail(),usersInfo))
			return false;
		//first userFolder and user.txt and friends.txt which hold contact Info
		File userFolder = createUserTxtAndDir(mainFolder,(Contact) contact);
 		//create folders and txt files
 		createUserFiles(userFolder);
 		return true;
	}

	@Override
	public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMail[] listEmails(int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmails(ILinkedList mails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean compose(IMail email) {
		// TODO Auto-generated method stub
		return false;
	}

}
