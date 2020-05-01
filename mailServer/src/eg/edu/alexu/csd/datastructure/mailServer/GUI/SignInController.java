package eg.edu.alexu.csd.datastructure.mailServer.GUI;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class SignInController implements Initializable {
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtPassword;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.app = new App();
	}
	@FXML
	/**
	 * When sign in button is clicked 
	 * if the email and password are correct then changes the scene to InternalScene
	 * if not then shows an designed alert Stage with a helping message
	 */
	public void signInClicked() {
		String email = txtEmail.getText().trim().toLowerCase();
		String password = txtPassword.getText();
		if(email.isEmpty() || password.isEmpty()) {
			Main.popUp.createAlert("Fill empty fields please.");
		}else if(Main.app.signin(email, password)) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("InternalScene.fxml"));
				Scene scene = new Scene(root);
				Main.stage.setScene(scene);
			} catch (IOException e) {}
		} else {
			Main.popUp.createAlert("Wrong email or password.\nTry again or create new account.");
		}
	}
	/**
	 * Changes the scene from sign in to sign up when sign up button is clicked.
	 * Does nothing if the file SignUpScene.fxml is missing.
	 */
	public void signUpClicked() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SignUpScene.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (IOException e) {}
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
