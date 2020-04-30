package eg.edu.alexu.csd.datastructure.mailServer.GUI;
import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.*;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class SignInController implements Initializable {
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtPassword;
	@FXML private Button btSignIn;
	@FXML private Button btSignUp;
	@FXML private ImageView closeIcon;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Clearing all fields
		/*txtEmail.clear(); txtPassword.clear();
		txtSignName.clear(); txtSignEmail.clear();
		txtSignPassword.clear(); txtSignDateYear.clear();
		txtSignDateMonth.clear(); txtSignDateDay.clear();*/
		
		
	}
	IApp app = new App();
	@FXML
	public void signInAction() {
		String email = txtEmail.getText().trim().toLowerCase();
		String password = txtPassword.getText();
		if(email.isEmpty() || password.isEmpty()) {
			
		}
		if(app.signin(email, password)) {
			
		} else {
			
		}
	}
	//Handling close and minimise buttons (ImageViews)
	public void closeClicked() {
		((Stage)txtEmail.getScene().getWindow()).close();
	}
	public void minimizeClicked() {
		((Stage)txtEmail.getScene().getWindow()).setIconified(true);
	}
}
