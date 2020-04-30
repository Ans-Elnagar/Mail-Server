package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;

public class SignUpController implements Initializable{
	@FXML private TextField txtName;
	@FXML private TextField txtEmail;
	@FXML private TextField txtPassword;
	@FXML private TextField txtYear;
	@FXML private TextField txtMonth;
	@FXML private TextField txtDay;
	@FXML private ChoiceBox<String> genderBox;
	@FXML private Button btback;
	@FXML private Button btsignUp;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderBox.getItems().addAll("Male","Female");
		genderBox.setValue("Male");
		
	}
	
}
