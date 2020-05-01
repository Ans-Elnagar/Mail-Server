package eg.edu.alexu.csd.datastructure.mailServer.GUI;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public static void main(String[] args) {
	    launch(args);
	}
}
