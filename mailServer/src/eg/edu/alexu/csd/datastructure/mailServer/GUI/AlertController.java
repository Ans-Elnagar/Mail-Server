package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AlertController implements Initializable {
	@FXML private Label lblMessage;
	private Stage stage;
	public void setMessage(String message) {
		lblMessage.setText(message);
	}
	public void closeClicked() {
		((Stage)lblMessage.getScene().getWindow()).close();
	}
	//Drag code
	double x,y;
	@FXML
    public void pressed(MouseEvent event) {
		stage = (Stage) lblMessage.getScene().getWindow();
        x = event.getSceneX();
        y = event.getSceneY();
    }
	@FXML
    public void dragged(MouseEvent event) {
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
