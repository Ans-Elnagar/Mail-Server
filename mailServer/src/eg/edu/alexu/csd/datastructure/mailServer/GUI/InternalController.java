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
	private int page;
	
	@FXML
	private Label name;
	@FXML
	Label pageLabel;
	@FXML
	Label folderName;
	
	@FXML
	TextField searchText;
	
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
	private ChoiceBox<String> sortTypeBox;
	@FXML
	private ChoiceBox<String> sortOrderBox;
	@FXML
	private ChoiceBox<String> filterBox;
	@FXML
	private ChoiceBox<String> searchBox;
	
	@FXML
	private CheckBox hasAttachmentsOnly;
	
	@FXML 
	private Circle profileImage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		page=1;
		currentFolder=new Folder(Main.app.user.getEmail()+'/',"Inbox");
		sort=SORTING.NEWEST;
		filter=new Filter();
		initialProfileImage(profileImage);
		name.setText(Main.app.user.getName());
		folderName.setText("Inbox");
		pageLabel.setText(page+" from "+countPages());
		viewInTable(currentFolder,filter,sort,page);
		setTableColumns();
		setChoiceBoxes();
		setAttachments();
	}
	
	
	void setAttachments() {
		hasAttachmentsOnly.selectedProperty().addListener((V,oldValue,newValue)->{
			page=1;
			filter.setHasAttachmentsOnly(newValue);
			viewInTable(currentFolder,filter,sort,page);
		});
	}
	
	
	void setChoiceBoxes() {
		
		sortTypeBox.getItems().addAll("Date","Subject","Importance","Sender","# of attachments");
		sortTypeBox.setValue("Date");
		sortTypeBox.getSelectionModel().selectedItemProperty().addListener((V,oldValue,newValue)-> applySortBoxes(newValue,sortOrderBox.getValue()));
		
		sortOrderBox.getItems().addAll("Descending","Ascending");
		sortOrderBox.setValue("Descending");
		sortOrderBox.getSelectionModel().selectedItemProperty().addListener((V,oldValue,newValue)-> applySortBoxes(sortTypeBox.getValue(),newValue));

		filterBox.getItems().addAll("All time","Last hour","Last day","Last week","Last month");
		filterBox.setValue("All time");
		filterBox.getSelectionModel().selectedItemProperty().addListener((V,oldValue,newValue)-> applyFilterBox(newValue));
		
		searchBox.getItems().addAll("Subject","Sender");
		searchBox.setValue("Subject");
		
	}
	
	
	void applySortBoxes(String type,String order) {
		if(order.equals("Ascending")) {
			if(type.equals("Date"))
				sort=SORTING.OLDEST;
			else if(type.equals("Subject"))
				sort=SORTING.ASCENDING_SUBJECT;
			else if(type.equals("Importance"))
				sort=SORTING.ASCENDING_IMPORTANCE;
			else if(type.equals("Sender"))
				sort=SORTING.ASCENDING_SENDER;
			else
				sort=SORTING.ASCENDING_N_ATTACH;
		}
		else {
			if(type.equals("Date"))
				sort=SORTING.NEWEST;
			else if(type.equals("Subject"))
				sort=SORTING.DESCENDING_SUBJECT;
			else if(type.equals("Importance"))
				sort=SORTING.DESCENDING_IMPORTANCE;
			else if(type.equals("Sender"))
				sort=SORTING.DESCENDING_SENDER;
			else
				sort=SORTING.DESCENDING_N_ATTACH;
		}
		
		page=1;
		viewInTable(currentFolder,filter,sort,page);
	}
	
	
	void applyFilterBox(String newFilter) {
		if(newFilter.equals("All time"))
			filter.setFromTime(filter.ALL);
		else if(newFilter.equals("Last hour"))
			filter.setFromTime(filter.ONE_HOUR);
		else if(newFilter.equals("Last day"))
			filter.setFromTime(filter.ONE_DAY);
		else if(newFilter.equals("Last week"))
			filter.setFromTime(filter.ONE_WEEK);
		else
			filter.setFromTime(filter.ONE_MONTH);

		page=1;
		viewInTable(currentFolder,filter,sort,page);
	}
	
	
	 void setTableColumns() {
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
	void searchAction() {
		if(searchText.getText().equals(""))
			Main.popUp.createAlert("Enter your search text.");
		else {
			if(searchBox.getValue().equals("Subject"))
				filter.setSubject(searchText.getText());
			else
				filter.setSender(searchText.getText());
			page=1;
			viewInTable(currentFolder,filter,sort,page);
		}
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
		ObservableList<Mail> mails=tableView.getSelectionModel().getSelectedItems();
		if(mails.size()==1) {
			try {
				Main.currentMail=mails.get(0);
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("messageView.fxml"));
				Parent root=fxmlLoader.load();
				Stage messageStage = new Stage();
				messageStage.initModality(Modality.APPLICATION_MODAL);
				messageStage.setScene(new Scene(root));
				messageStage.initStyle(StageStyle.UNDECORATED);
				messageStage.setResizable(false);
				messageStage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else {
			Main.popUp.createAlert("You should select 1 mail.");
		}
	}
	
	
	
	@FXML
	public void deleteAction() {
		ObservableList<Mail> mails=tableView.getSelectionModel().getSelectedItems();
		if(mails.size()==0)
			Main.popUp.createAlert("You should select at least 1 mail.");
		else {
			SLinkedList list=new SLinkedList();
			for(Mail mail:mails)
				list.add(mail);
			Main.app.deleteEmails(list);
		}
			
	}
	
	
	
	@FXML
	public void refreshAction() {
		page=1;
		viewInTable(currentFolder,filter,sort,page);
	}
	
	
	
	@FXML
	public void nextPageAction() {
		if(page+1<=countPages()) {
			page++;
			viewInTable(currentFolder,filter,sort,page);
		}
	}
	
	
	
	@FXML
	public void lastPageAction() {
		if(page-1>0) {
			page--;
			viewInTable(currentFolder,filter,sort,page);
		}
	}
	
	
	static int countPages() {
		int size=Main.app.mails.size();
		return ((size%10==0)?0:1)+(size/10);
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
		for(int i=0;i<10&&mailsArray[i]!=null;i++) {
			mails.add(mailsArray[i]);
		}
		return mails;
	}
	
	
	
	 void viewInTable(Folder folder,IFilter filter,SORTING sorting,int page) {
		Main.app.setViewingOptions(folder, filter, new Sort(sorting));
		Mail[] mails=(Mail[]) Main.app.listEmails(page);
		tableView.getItems().clear();
		if(Main.app.mails.size()==0) return;
		pageLabel.setText(page+" from "+countPages());
		tableView.setItems(getMails(mails));
	}
	
	
	 
	// handling choosing folders
	public void folderSelected(String folder) {
		if(!currentFolder.getFolder().equals(folder)) {
			page=1;
			currentFolder.setFolder(Main.app.user.getEmail()+'/',folder);
			folderName.setText(folder);
			
			sortTypeBox.getSelectionModel().select(0);
			sortOrderBox.getSelectionModel().select(0);
			filterBox.getSelectionModel().select(0);
			searchBox.getSelectionModel().select(0);

			filter=new Filter();
			sort=SORTING.NEWEST;
			viewInTable(currentFolder,filter,sort,page);
		}
	}
	
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



