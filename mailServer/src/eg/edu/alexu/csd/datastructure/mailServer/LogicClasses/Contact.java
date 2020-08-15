package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;

import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;
public class Contact implements IContact {
	private String name;
	private String email;
	private String password;
	private String date;
	private String gender;
	public ILinkedList friends;
	public Contact () {
		friends = new SLinkedList();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email.toString();
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
