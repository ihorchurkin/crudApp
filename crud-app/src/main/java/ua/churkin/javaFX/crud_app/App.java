package ua.churkin.javaFX.crud_app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.churkin.javaFX.Entities.User;
import ua.churkin.javaFX.sqlPack.ConnectToDB;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;
    
    private MainWindowController mainWindowController;
    private AddUserFormController addUserFormController;
    
    private static ObservableList<User> list;
    public App() {
    }
    
    void showMainWindow() {
    	try {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(App.class.getResource("MainWindow.fxml"));
    	AnchorPane mainWindow = (AnchorPane)loader.load();
    	
    	MainWindowController controller = loader.getController();
    	controller.setApp(this);
    	controller.setStage(mainStage);
    	mainWindowController = controller;
    	scene = new Scene(mainWindow);
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    	}
    }
    
    boolean showAddUserForm(int idCount) {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(App.class.getResource("AddUserForm.fxml"));
    		AnchorPane addUserForm = (AnchorPane) loader.load();
    		
    		Stage addingStage = new Stage();
    		addingStage.setTitle("Add user");
    		addingStage.initModality(Modality.WINDOW_MODAL);
    		addingStage.initOwner(mainStage);
    		Scene addingScene = new Scene(addUserForm);
    		addingStage.setScene(addingScene);
    		
    		AddUserFormController controller = loader.getController();
    		controller.setStage(addingStage);
    		//controller.setIdCount(idCount);
    		addUserFormController = controller;
    		controller.setMainWindowController(mainWindowController);
    		
    		addingStage.showAndWait();
    		return controller.isOkClicked();
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }
    
    @Override
    public void start(Stage stage) throws IOException {
    	mainStage = stage;
    	try {
			list = FXCollections.observableArrayList(ConnectToDB.selectAll());
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
    	
        showMainWindow();
        stage.setScene(scene);
        stage.setTitle("CRUD App");
        stage.show();
    }
    
    static ObservableList<User> getUsers() {
    	try {
			list = FXCollections.observableArrayList(ConnectToDB.selectAll());
		} catch (SQLException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    }

    public static void main(String[] args) {
        launch();
    }

}