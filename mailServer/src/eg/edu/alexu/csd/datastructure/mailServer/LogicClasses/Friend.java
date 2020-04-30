package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.ILinkedList;
public class Friend {
	private String name;
	private ILinkedList emails;
	public Friend(String name) {
		setName(name);
		emails=new SLinkedList();
	}
	public Friend() {
		emails= new SLinkedList();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ILinkedList getEmails() {
		return emails;
	}
	public void addEmail(String mail) {
		emails.add(mail);
	}
}
