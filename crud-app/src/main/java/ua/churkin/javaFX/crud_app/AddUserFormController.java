package ua.churkin.javaFX.crud_app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.churkin.javaFX.Entities.User;
import ua.churkin.javaFX.sqlPack.ConnectToDB;

public class AddUserFormController {
	@FXML
	TextField txtFieldEmail;
	@FXML
	TextField txtFieldFirstName;
	@FXML
	TextField txtFieldLastName;
	@FXML
	Button btnOk;
	@FXML
	Button btnCancel;
	
	private boolean okClicked = false;
	private Stage thisStage;
	private User newUser;
	private MainWindowController mainWindowController;
	
	//private int idCount = 0;
	
	/*
	 * public void setIdCount(int idCount) { this.idCount = idCount; }
	 */
	
	public void setMainWindowController(MainWindowController controller) {
		mainWindowController = controller;
	}
	
	@FXML
	private void clickOK() {
		
		if (inputValidation()) {
			newUser = new User(txtFieldEmail.getText(), txtFieldFirstName.getText(), txtFieldLastName.getText());
			//list.add(newUser);
			try {
				ConnectToDB.addUser(newUser);
				mainWindowController.setItems(App.getUsers());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			okClicked = true;
			
			thisStage.close();
		}
	}
	@FXML
	private void clickCancel() {
		thisStage.close();
	}
	
	public void setStage(Stage stage) {
		this.thisStage = stage;
	}
	
	public boolean isOkClicked() {
		return this.okClicked;
	}
	
	private boolean inputValidation() {
		String errorMessage = "";
		Pattern pattern = Pattern.compile("[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+");
		Matcher matcher = pattern.matcher(txtFieldEmail.getText());
		if (txtFieldEmail.getText() == null || txtFieldEmail.getText().length() == 0 || !matcher.matches()) {
			errorMessage += "No valid email!\n";
		}
		if (txtFieldFirstName.getText() == null || txtFieldFirstName.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (txtFieldLastName.getText() == null || txtFieldLastName.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		
		if (errorMessage.length() == 0)
			return true;
		else {
			Alert alertWindow = new Alert(AlertType.ERROR);
			alertWindow.initOwner(thisStage);
			alertWindow.setHeight(300);
			alertWindow.setWidth(400);
			alertWindow.setTitle("Invalid Fields");
			alertWindow.setHeaderText("Please correct invalid fields");
			alertWindow.setContentText(errorMessage);
			
			alertWindow.showAndWait();
			
			return false;
		}
	}
}
