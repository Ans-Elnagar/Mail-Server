package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.io.File;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.*;
public class Mail implements IMail {
	// the folder of the mail 
	private File mailDir;
	// sender E-mail
	private String sender;
	// list of receivers 
	public LinkedQueue receivers;
	// subject of the mail
	private String subject;
	// the time this mail received or sent
	private long time;
	// the mail body from mailBody.txt
	private String mailBody;
	// list of attachments
	public ILinkedList attachments;
	//constructor
	public Mail() {
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
}