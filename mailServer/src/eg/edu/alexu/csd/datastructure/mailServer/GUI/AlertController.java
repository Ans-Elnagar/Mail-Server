package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertController implements Initializable {
	@FXML private Label lblMessage;
	public void setMessage(String message) {
		lblMessage.setText(message);
	}
	public void closeClicked() {
		((Stage)lblMessage.getScene().getWindow()).close();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
