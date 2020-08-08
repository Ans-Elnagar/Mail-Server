package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUp {
	public void createAlert(String message) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("AlertScene.fxml"));
			Parent root = loader.load();
			AlertController controller = loader.getController();
			controller.setMessage(message);
			Scene scene = new Scene(root);
			Stage alertStage = new Stage();
			alertStage.initModality(Modality.APPLICATION_MODAL);
			alertStage.setScene(scene);
			alertStage.initStyle(StageStyle.UNDECORATED);
			alertStage.setResizable(false);
			alertStage.show();
		}catch (IOException e) {}
	}
}
