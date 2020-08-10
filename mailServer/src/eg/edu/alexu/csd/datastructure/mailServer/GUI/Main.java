package eg.edu.alexu.csd.datastructure.mailServer.GUI;
import java.io.File;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static Stage stage;
	public static App app;
	public static PopUp popUp = new PopUp();
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		File file =new File("icons/Logo.png");
		Image image = new Image(file.toURI().toString(),false);
		primaryStage.getIcons().add(image);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	public static void main(String[] args) {
	    launch(args);
	}
}
