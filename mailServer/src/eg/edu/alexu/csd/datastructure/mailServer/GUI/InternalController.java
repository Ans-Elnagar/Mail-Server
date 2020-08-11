package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class InternalController implements Initializable {
	@FXML 
	private Circle profileImage;
	@FXML
	private Label name;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialProfileImage(profileImage);
		name.setText(Main.app.user.getName());
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
			Main.stage.setScene(new Scene(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
