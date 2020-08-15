package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFilter;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.*;


public class InternalController implements Initializable {
	private Folder currentFolder;
	private SORTING sort;
	private Filter filter;
	private int page=1;
	@FXML
	Label pageLabel;
	@FXML
	Label folderName;
	@FXML
	private TableView<Mail> tableView;
	@FXML
	private TableColumn<Mail,String> dateColumn;
	@FXML
	private TableColumn<Mail,String> importantColumn;
	@FXML
	private TableColumn<Mail,String> fromColumn;
	@FXML
	private TableColumn<Mail,String> subjectColumn;
	@FXML
	private TableColumn<Mail,String> mailColumn;
	@FXML 
	private Circle profileImage;
	@FXML
	private Label name;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentFolder=new Folder(Main.app.user.getEmail()+'/',"Inbox");
		sort=SORTING.NEWEST;
		filter=new Filter();
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initialProfileImage(profileImage);
		name.setText(Main.app.user.getName());
		folderName.setText("Inbox");
		viewInTable(currentFolder,new Filter(),SORTING.NEWEST,page);
		setColumns();
	}
	
	
	 void setColumns() {
		dateColumn.setCellValueFactory(cellData->{
			Mail mail=cellData.getValue();
			if(mail==null) return null;
			Date date=new Date(mail.getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			return new SimpleStringProperty(dateFormat.format(date));
		});
		
		importantColumn.setCellValueFactory(cellData->{
			Mail mail=cellData.getValue();
			if(mail==null) return null;
			 return new SimpleStringProperty(Integer.toString(mail.getImportance()));
		});
		
		fromColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
		
		subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		mailColumn.setCellValueFactory(cellData->{
			Mail mail=cellData.getValue();
			if(mail==null) return null;
			String body=mail.getMailBody().replace("/n"," ");
			return new SimpleStringProperty(body);
		});
		
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
		viewInTable(currentFolder,filter,sort,1);
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
	

	
	static ObservableList<Mail> getMails(Mail[] mailsArray){
		ObservableList<Mail> mails=FXCollections.observableArrayList();
		for(int i=9;i>=0;i--) {
			if(mailsArray[i]==null) continue;
			mails.add(mailsArray[i]);
		}
		return mails;
	}
	
	
	
	 void viewInTable(Folder folder,IFilter filter,SORTING sorting,int page) {
		Main.app.setViewingOptions(folder, filter, new Sort(sorting));
		Mail[] mails=(Mail[]) Main.app.listEmails(page);
		tableView.getItems().clear();
		if(Main.app.mails.size()==0) return;
		tableView.setItems(getMails(mails));
	}
	
	
	public void folderSelected(String folder) {
		if(!currentFolder.getFolder().equals(folder)) {
			page=1;
			currentFolder.setFolder(Main.app.user.getEmail()+'/',folder);
			folderName.setText(folder);
			//TODO  : edit to add filtering 
			viewInTable(currentFolder,new Filter(),SORTING.NEWEST,page);
		}
	}
	
	// handling choosing folders
	@FXML
	public void inboxSelected() {
		folderSelected("Inbox");
	}
	@FXML
	public void sentSelected() {
		folderSelected("Sent");
	}
	@FXML
	public void trashSelected() {
		folderSelected("Trash");
	}
	@FXML
	public void draftSelected() {
		folderSelected("Draft");
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
