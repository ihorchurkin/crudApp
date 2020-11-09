package ua.churkin.javaFX.crud_app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import ua.churkin.javaFX.Entities.*;
import ua.churkin.javaFX.sqlPack.ConnectToDB;

public class MainWindowController {
	private App app;
	private Stage stage;
	
	@FXML
	private TableView<User> tabViewUsers;
	@FXML
	private TableColumn<User, Integer> tabColID;
	@FXML
	private TableColumn<User, String> tabColEmail;
	@FXML
	private TableColumn<User, String> tabColFirstName;
	@FXML
	private TableColumn<User, String> tabColLastName;
    @FXML
    private MenuItem menuItemNew;
    @FXML
    private void initialize() {
    	tabColID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    	tabColEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    	tabColFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    	tabColLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    	
    	
    	tabColEmail.setCellFactory(TextFieldTableCell.forTableColumn());
    	tabColEmail.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
    		@Override
    		public void handle(CellEditEvent<User, String> t) {
    			
    			Pattern pattern = Pattern.compile("[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+");
    			Matcher matcher = pattern.matcher(t.getNewValue());
    			if (!matcher.matches()) {
    				Alert alert = new Alert(AlertType.ERROR);
    				alert.initOwner(stage);
    				alert.setTitle("Invalid field");
    				alert.setHeaderText("Invalid field");
    				alert.setContentText("Please to correct email field");
    				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getOldValue());
    			}
    			
    			else {
    				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
    				User temp = ((User) t.getTableView().getItems().get(t.getTablePosition().getRow()));
        			try {
    					ConnectToDB.changeUser(temp);
    				} catch (SQLException | IOException | URISyntaxException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
        		}
    			}
    	});
    	
    	
    	tabColFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
    	tabColFirstName.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
    		@Override
    		public void handle(CellEditEvent<User, String> t) {
    			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstName(t.getNewValue());
    			
    			User temp = ((User) t.getTableView().getItems().get(t.getTablePosition().getRow()));
    			try {
					ConnectToDB.changeUser(temp);
				} catch (SQLException | IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	});
    	
    	
    	tabColLastName.setCellFactory(TextFieldTableCell.forTableColumn());
    	tabColLastName.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
    		@Override
    		public void handle(CellEditEvent<User, String> t) {
    			((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
    			
    			User temp = ((User) t.getTableView().getItems().get(t.getTablePosition().getRow()));
    			try {
					ConnectToDB.changeUser(temp);
				} catch (SQLException | IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	});
    }
    
    @FXML
    private void handleDelete() {
    	User temp = tabViewUsers.getSelectionModel().getSelectedItem();
    	if (temp == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.initOwner(stage);
    		alert.setHeight(300);
    		alert.setWidth(400);
    		alert.setTitle("Not selected field");
    		alert.setHeaderText("Please to select field");
    		
    		alert.showAndWait();
    	}
    	else {
    		try {
				ConnectToDB.deleteUser(temp.getId());
				tabViewUsers.setItems(App.getUsers());
			} catch (SQLException | IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    private void callAddUserForm() {
    	app.showAddUserForm(getLastId());
    }
    
    public MainWindowController() {
    	
    }
    
    public void setApp(App app) {
    	this.app = app;
    	
    	setItems(App.getUsers());
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void setItems(ObservableList<User> list) {
    	tabViewUsers.setItems(list);
    }
    
    private int getLastId() {
    	var list = tabViewUsers.getItems();
    	int size = list.size();
    	User temp = list.get(size-1);
    	return temp.getId();
    }
}
