package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class messageViewController implements Initializable {
	@FXML
	private  BorderPane boarderPane;
	Stage stage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
