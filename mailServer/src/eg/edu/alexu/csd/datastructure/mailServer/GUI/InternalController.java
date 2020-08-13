package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFilter;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.*;


public class InternalController implements Initializable {
	private Folder currentFolder=new Folder("Inbox");
	private SORTING sort=SORTING.NEWEST;
	private Filter filter=new Filter();
	private int page=1;
	@FXML
	Label pageLabel;
	@FXML
	Label folderName;
	@FXML
	private TableView<Mail> tableView;
	@FXML
	private TableColumn<Mail,String> dateView;
	@FXML
	private TableColumn<Mail,String> importantView;
	@FXML
	private TableColumn<Mail,String> fromView;
	@FXML
	private TableColumn<Mail,String> subjectView;
	@FXML
	private TableColumn<Mail,String> mailView;
	@FXML 
	private Circle profileImage;
	@FXML
	private Label name;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialProfileImage(profileImage);
		name.setText(Main.app.user.getName());
		folderName.setText("Inbox");
		
		inboxSelected();
		
	}
	 
	static void initialProfileImage(Circle profile) {
		File file =new File("Users//"+Main.app.user.getEmail()+"//Profile.jpg");
		Image image = new Image(file.toURI().toString(),false);
		profile.setFill(new ImagePattern(image));
	}
	@FXML
	 void editProfile() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
			Stage editProfileStage = new Stage();
			editProfileStage.initModality(Modality.APPLICATION_MODAL);
			editProfileStage.setScene(new Scene(root));
			editProfileStage.initStyle(StageStyle.UNDECORATED);
			editProfileStage.setResizable(false);
			editProfileStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// updating profile photo and name -If changed-
		initialProfileImage(profileImage);
		name.setText(Main.app.user.getName());
	}
	
	@FXML 
	public void composeAction() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ComposeScene.fxml"));
			Stage composeStage = new Stage();
			composeStage.initModality(Modality.APPLICATION_MODAL);
			composeStage.setScene(new Scene(root));
			composeStage.initStyle(StageStyle.UNDECORATED);
			composeStage.setResizable(false);
			composeStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openAction() {
		//TODO open
	}
	@FXML
	public void deleteAction() {
		//TODO : handle delete if it's trash and move to trash otherwise
	}
	@FXML
	public void refreshAction() {
		//TODO filter
		viewInTable(currentFolder,null,sort,1);
	}
	
	// handling 
	//@FXML
	public static  void addingMailsToTable(Mail[] mails) {
		
	}
	
	@FXML
	public void nextPageAction() {
		//TODO
	}
	@FXML
	public void lastPageAction() {
		//TODO
	}
	
	@FXML
	public void signOutAction() {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
			Main.stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	static void viewInTable(Folder folder,IFilter filter,SORTING sorting,int page) {
		Main.app.setViewingOptions(folder, filter, new Sort(sorting));
		Mail[] mails=(Mail[]) Main.app.listEmails(page);
		addingMailsToTable(mails);
	}
	// handling choosing folders
	@FXML
	public void inboxSelected() {
		if(!currentFolder.getFolder().equals("Inbox")) {
			page=1;
			currentFolder.setFolder(Main.app.user+"/Inbox");
			folderName.setText("Inbox");
			//TODO  : edit to add filtering 
			viewInTable(currentFolder,null,SORTING.NEWEST,page);
		}
	}
	@FXML
	public void sentSelected() {
		if(!currentFolder.getFolder().equals("Sent")) {
			page=1;
			currentFolder.setFolder(Main.app.user+"/Sent");
			folderName.setText("Sent");
			viewInTable(currentFolder,null,SORTING.NEWEST,page);
		}
	}
	@FXML
	public void trashSelected() {
		if(!currentFolder.getFolder().equals("Trash")) {
			page=1;
			currentFolder.setFolder(Main.app.user+"/Trash");
			folderName.setText("Trash");
			viewInTable(currentFolder,null,SORTING.NEWEST,page);
		}
	}
	@FXML
	public void draftSelected() {
		if(!currentFolder.getFolder().equals("Draft")) {
			page=1;
			currentFolder.setFolder(Main.app.user+"/Draft");
			folderName.setText("Draft");
			viewInTable(currentFolder,null,SORTING.NEWEST,page);
		}
	}
	
	static int countPages() {
		int size=Main.app.mails.size();
		return ((size%10==0)?0:1)+(size/10);
	}
	
	//handling moving the stage
	double x,y;
	@FXML
    public void barPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
	@FXML
    public void barDragged(MouseEvent event) {
        Main.stage.setX(event.getScreenX() - x);
        Main.stage.setY(event.getScreenY() - y);
    }
	//Handling close and minimize buttons (ImageViews)
	@FXML
	public void closeClicked() {
		Main.stage.close();
	}
	@FXML
	public void minimizeClicked() {
		Main.stage.setIconified(true);
	}
}
