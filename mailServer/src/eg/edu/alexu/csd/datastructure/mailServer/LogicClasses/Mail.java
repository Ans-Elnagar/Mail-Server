package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.io.File;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;
public class Mail implements IMail {
	private File mailDir;
	private String sender;
	public LinkedQueue receivers;
	private String subject;
	private long time;
	private String mailBody;
	public ILinkedList attachments;
	private int importance;
	//constructor
	public Mail() {
		importance=0;
		setAttachments(new SLinkedList());
		receivers=new LinkedQueue();
	}
	//get subject
	public String getSubject() {
		return subject;
	}
	//set subject
	public void setSubject(String subject) {
		this.subject = subject;
	}
	//get mail directory file
	public File getMailDir() {
		return mailDir;
	}
	// set Mail Directory by taking string of path 
	public void setMailDir(String path) {
		mailDir = new File(path);
	}
	///
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public ILinkedList getAttachments() {
		return attachments;
	}
	public void setAttachments(ILinkedList attachments) {
		this.attachments = attachments;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance=importance;
	}
	
}