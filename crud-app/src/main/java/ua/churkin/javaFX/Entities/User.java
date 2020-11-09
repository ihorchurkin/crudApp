package ua.churkin.javaFX.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	private int count = 0;
	
	private IntegerProperty id;
	private StringProperty email;
	private StringProperty firstName;
	private StringProperty lastName;
	
	public User() {
		//this.id = new SimpleIntegerProperty(++count);
	}
	
	public User(String email, String firstName, String lastName) {
		this.id = new SimpleIntegerProperty(++count);
		this.email = new SimpleStringProperty(email);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}
	
	public User(int id, String email, String firstName, String lastName) {
		this.id = new SimpleIntegerProperty(id);
		this.email = new SimpleStringProperty(email);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		
		++count;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getId() {
		return id.get();
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public IntegerProperty idProperty() {
		return this.id;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public StringProperty emailProperty() {
		return this.email;
	}
	
	public String getFirstName() {
		return firstName.get();
	}
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	public String getLastName() {
		return lastName.get();
	}
	
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public StringProperty lastNameProperty() {
		return this.lastName;
	}
}
