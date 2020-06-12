package eg.edu.alexu.csd.datastructure.mailServer.GUI;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.App;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class SignUpController implements Initializable{
	@FXML private TextField txtName;
	@FXML private TextField txtEmail;
	@FXML private PasswordField txtPassword;
	@FXML private ChoiceBox<Integer> dayBox;
	@FXML private ChoiceBox<Integer> monthBox;
	@FXML private ChoiceBox<Integer> yearBox;
	@FXML private ChoiceBox<String> genderBox;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderBox.getItems().addAll("Male","Female");
		genderBox.setValue("Male");
		int currentYear = ((int) (System.currentTimeMillis()/(1000.0*3600*24*365.25)+0.5))+1970;
		for(int i=currentYear-100; i<=currentYear; i++)
			yearBox.getItems().add(i);
		yearBox.setValue(currentYear);
		for(int i=1; i<=12; i++)
			monthBox.getItems().add(i);
		monthBox.setValue(1);
		for(int i=1; i<=31; i++)
			dayBox.getItems().add(i);
		dayBox.setValue(1);
	}
	public void signUpClicked(){
		String message = "";
		String name = txtName.getText().trim();
		if(name.isEmpty())
			message += "- Enter a valid name such as \"Name nickname\"\n";
		if(!validLetters(name,""))
			message += "- Name contains invalid symbols\n";
		String email = txtEmail.getText().trim().toLowerCase();
		if(email.isEmpty())
			message += "- Enter a valid email such as \"example@example.com\"\n";
		if(isInvalidEmail(email))
			message += "- Email has invald form.\n        Please use letters as:\n            a-z A-Z 0-9 @ .\n";
		String password = txtPassword.getText();
		if(password.isEmpty())
			message += "- Password Field is empty.\n        Please enter a password\n";
		if(!isValidDate(yearBox.getValue(),monthBox.getValue(),dayBox.getValue()))
			message += "- The selected birthday is not a true date.\n";
		if(message.isEmpty()) {
			App.userContact.setName(name);
			App.userContact.setEmail(email);
			App.userContact.setPassword(password);
			App.userContact.setDate(dayBox.getValue()+"/"+monthBox.getValue()+"/"+yearBox.getValue());
			App.userContact.setGender(genderBox.getValue());
			if(Main.app.signup(App.userContact)) {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("InternalScene.fxml"));
					Scene scene = new Scene(root);
					Main.stage.setScene(scene);
				} catch (IOException e) {}
				return;
			}else
				message += "- Someone is using this email, please use another one\n";
		}
		Main.popUp.createAlert(message);
	}
	
	/**
	 * Validates that the given email is in correct form
	 * @param email
	 * The given email in the email text field after removing leading and trailing spaces
	 * and turn all upper case letters to lower case 
	 * @return
	 * false if the email form is incorrect and true if correct
	 */
	boolean isInvalidEmail(String email) {
		int len = email.length();
		// a @ b . c at least 5 letters
		if(len<5)
			return true;
		// if it doesn't contain @ or .
		if( !email.contains("@") || !email.contains("."))
			return true;
		// if it has .. or first letter or last letter is .
		if(email.contains("..") || email.contains("@.") || email.contains(".@") 
				|| email.charAt(0)=='.' || email.charAt(0) == '@'
				|| email.charAt(len-1) == '.' || email.charAt(len-1) == '@')
			return true;
		//if there is more than one @
		if(email.indexOf('@') != email.lastIndexOf('@'))
			return true;
		//if contains invalid letters
		if(!validLetters(email,"@."))
			return true;
		return false;
	}
	/**
	 * Checks if email contains characters other than a-z,A-Z 0-9 and the String in except.
	 * @param word
	 * the word to check it.
	 * @param except
	 * other acceptable letters.
	 * @return
	 * true if the word doesn't contain any unacceptable character.
	 * false if it contains any unacceptable character.
	 */
	boolean validLetters(String word, String except) {
		int len = word.length();
		for(int i=0; i<len; i++) {
			if( !Character.isLetterOrDigit(word.charAt(i)) && !except.contains(word.charAt(i)+""))
				return false;
		}
		return true;
	}
	/**
	 * Check if the selected date is valid or not
	 * @param year
	 * the selected year
	 * @param month
	 * the selected month
	 * @param day
	 * the selected day
	 * @return
	 * true if the date is valid
	 * false if it is not
	 */
	boolean isValidDate(int year,int month,int day){
		String date="";
		if(month<10)
			date += "0" + month;
		else
			date += month;
		date += "/";
		if(day<10)
			date += "0" + day;
		else
			date += day;
		date += "/"+year;
	    //preferred date format
	    SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
	    form.setLenient(false);
	    try{
	        form.parse(date); 
	    }catch (ParseException e){
	        return false;
	    }
	    return true;
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
	public void closeClicked() {
		Main.stage.close();
	}
	public void minimizeClicked() {
		Main.stage.setIconified(true);
	}
	public void backClicked() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (IOException e) {}
	}
}
