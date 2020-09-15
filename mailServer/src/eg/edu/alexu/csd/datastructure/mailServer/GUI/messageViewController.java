package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.Mail;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class messageViewController implements Initializable {
	@FXML
	private  BorderPane boarderPane;
	
	@FXML
	Label subjectLabel;
	@FXML
	Label timeLabel;
	@FXML
	Label importanceLabel;
	@FXML
	Label attachLabel;
	
	@FXML
	TextArea fromText;
	@FXML
	TextArea toText;
	@FXML
	TextArea mailText;
	
	Stage stage;
	Mail mail;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mail=Main.currentMail;
		// labels
		subjectLabel.setText(mail.getSubject());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		timeLabel.setText(dateFormat.format(new Date(mail.getTime())));
		importanceLabel.setText(new Integer(mail.getImportance()).toString());
		attachLabel.setText(new Integer(mail.getAttachments().size()).toString());
		//Text area 
		fromText.setEditable(false);
		fromText.setText(mail.getSender());
		toText.setEditable(false);
		String to ="";
		for(int i=0;i<mail.receivers.size();i++) {
			String current=(String)mail.receivers.dequeue();
			if(current.equals(Main.app.user.getEmail()))
				to+="Me"+" , ";
			else
				to+=current+" , ";
			mail.receivers.enqueue(current);
		}
		toText.setText(to);
		mailText.setEditable(false);
		mailText.setText(mail.getMailBody().replace("/n", "\n"));
		
	}
	
	@FXML
	void viewAttachsAction() {
		
	}
	@FXML
	void forwordAction() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ComposeScene.fxml"));
			stage=((Stage)boarderPane.getScene().getWindow());
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	   stage=((Stage)boarderPane.getScene().getWindow());
       stage.setX(event.getScreenX() - x);
       stage.setY(event.getScreenY() - y);
    }
	//Handling close and minimize buttons (ImageViews)
	@FXML
	public void closeClicked() {
		((Stage)boarderPane.getScene().getWindow()).close();
	}
	@FXML
	public void minimizeClicked() {
		((Stage)boarderPane.getScene().getWindow()).setIconified(true);
	}

}
