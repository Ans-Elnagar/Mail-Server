package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;

import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IFilter;

public class Filter implements IFilter {
	
	// when choosing display mails from :
	public static final int ALL = -1;
	public static final int ONE_HOUR = 0;
	public static final int ONE_DAY = 1;
	public static final int ONE_WEEK = 2;
	public static final int ONE_MONTH = 3;
	public static final int[] timeDifferences = {3600, 86400, 604800, 2592000};
	private int fromTime = -1;
	private boolean hasAttachmentsOnly;
	private String sender;
	private String subject;
	
	public Filter() {}
	
	public Filter(int fromTime) {
		this.fromTime = fromTime;
	}
	public Filter(int fromTime, boolean hasAttachmentsOnly) {
		this.fromTime = fromTime;
		this.hasAttachmentsOnly = hasAttachmentsOnly;
	}
	public Filter(int fromTime, String sender, String subject) {
		this.fromTime = fromTime;
		if( sender == null )
			sender = "";
		this.sender = sender;
		if( subject == null )
			subject = "";
		this.subject = subject;
	}
	
	public Filter(int fromTime, String sender, String subject, boolean hasAttachmentsOnly) {
		this.fromTime = fromTime;
		if( sender == null )
			sender = "";
		this.sender = sender;
		if( subject == null )
			subject = "";
		this.subject = subject;
		this.hasAttachmentsOnly = hasAttachmentsOnly;
	}
	
	public void setFromTime(int fromTime) {
		this.fromTime = fromTime;
	}
	public void setHasAttachmentsOnly(boolean hasAttachmentsOnly) {
		this.hasAttachmentsOnly = hasAttachmentsOnly;
	}
	public void setSender(String sender) {
		if( sender == null )
			sender = "";
		this.sender = sender;
	}
	public void setSubject(String subject) {
		if( subject == null )
			subject = "";
		this.subject = subject;
	}
	
	
	public void filter(SLinkedList mails) {
		int size = mails.size();
		Mail mail;
		long currentTime = System.currentTimeMillis();
		for(int i=0; i<size; i++) {
			mail = (Mail)mails.get(i);
			if( isInTime(mail.getTime(),fromTime, currentTime) ) {
				if( (hasAttachmentsOnly && mail.attachments.size() > 0) || ( ! hasAttachmentsOnly ) ) {
					if(mail.getSender().contains(sender)) {
						if(mail.getSubject().contains(subject)) {
							continue;
						}
					}
				}
			}
			mails.remove(i);
			i--;
			size--;
		}
	}
	private boolean isInTime(long time, int fromTime, long currentTime) {
		if(fromTime == -1)
			return true;
		if( timeDifferences[fromTime] >  (int) ((currentTime-time)/1000) )
			return true;
		return false;
	}
	
}
