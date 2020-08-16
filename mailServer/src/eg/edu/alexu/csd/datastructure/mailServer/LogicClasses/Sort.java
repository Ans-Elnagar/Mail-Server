package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import java.util.Comparator;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.ISort;
public class Sort implements ISort {
	private SORTING type;
	private Comparator<Mail> cmp=new Compare();
	public void quickSort(SLinkedList mails) {
		Stack stack = new Stack();
		stack.push(0);
		stack.push(mails.size()-1);
		while(!stack.isEmpty()) {
			int end=(int)stack.pop();
			int start=(int)stack.pop();
			if(start>=end)
				continue;
			int p=partitioning(mails,start,end);
			stack.push(start);
			stack.push(p-1);
			stack.push(p+1);
			stack.push(end);
		}
	}
	private int partitioning(SLinkedList mails,int start,int end) {
		Mail pivot=(Mail)mails.get(end);
		int lower=start;
		for(int j=lower;j<mails.size();j++) {
			int comp=cmp.compare((Mail)mails.get(j), pivot);
			if(comp<0) {
				swap(mails,lower,j);
				lower++;
			}
		}
		swap(mails,lower,end);
		return lower;
	}
	private void swap(SLinkedList mails,int i,int j) {
		Mail temp=(Mail) mails.get(i);
		mails.set(i, mails.get(j));
		mails.set(j, temp);
	}
	public Sort() {
		type=SORTING.NEWEST;
	}
	public Sort(SORTING type) {
		this.type=type;
	}
	public void setType(SORTING type) {
		this.type=type;
	}
	private class Compare implements Comparator<Mail>{
		@Override
		public int compare(Mail first, Mail second) {
			if(type==SORTING.NEWEST)
				return  compNum(first.getTime(), second.getTime());
			if(type==SORTING.OLDEST)
				return  compNum(second.getTime(), first.getTime());
			if(type==SORTING.ASCENDING_IMPORTANCE)
				return  compNum(first.getImportance(),second.getImportance());
			if(type==SORTING.DESCENDING_IMPORTANCE)
				return  compNum(second.getImportance(),first.getImportance());
			if(type==SORTING.ASCENDING_SUBJECT)
				return compString(first.getSubject(),second.getSubject());
			if(type==SORTING.DESCENDING_SUBJECT)
				return compString(second.getSubject(),first.getSubject());
			if(type==SORTING.ASCENDING_SENDER)
				return compString(first.getSender(),second.getSender());
			if(type==SORTING.DESCENDING_SENDER)
				return compString(second.getSender(),first.getSender());
			if(type==SORTING.ASCENDING_N_ATTACH)
				return compNum(first.attachments.size(),second.attachments.size());
			if(type==SORTING.DESCENDING_N_ATTACH)
				return compNum(second.attachments.size(),first.attachments.size());
			return 0;
		}
		private int compNum(long num1,long num2) {
			if(num1>num2)
				return -1;
			else if(num1<num2)
				return 1;
			return 0;
		}
		private int compString(String str1,String str2) {
			for(int i=0;i<str1.length()&&i<str2.length();i++) {
				int ch1=str1.charAt(i),ch2=str2.charAt(i);
				if(ch1>ch2)
					return 1;
				if(ch1<ch2)
					return -1;
			}
			return compNum(str1.length(),str2.length());
		}
	}
	
	public static void main(String[] args) {
		 SLinkedList mails = new SLinkedList();
		 //2288'4' 1580'1' 2545'6' 1886'2' 2333'5' 2815'7' 1950'3' 
		 String[] names = {"ahmed_mohamed@gmail.com","baraah@yahoo.com","esraa_mahmoud@hotmail.com","ans_gomaa@hmail.com", 
				  "seif_gneedy@hotmail.com","zAmericanEnglish@hotmail.com","zezoElaraghi@A2OJ.com"};
		 //n=7
		 String[] subjects= {"welcome to egypt","fuck egypt","arab countries is good",
				 "hello world","five Pistols","s*x education","just fuck mezo"};
		 long[] times= {15,2,3,40,25,60,75};
		 for(int i=0;i<names.length;i++) {
			 Mail mail=new Mail();
			 mail.setTime(times[i]);
			 SLinkedList attachs = new SLinkedList();
			 LinkedQueue receive = new LinkedQueue();
			 for(int j=i;j<names.length;j++) {
				 attachs.add(names[j]);
				 receive.enqueue(names[j]);
			 }
			 mail.receivers=receive;
			 mail.setAttachments(attachs);
			 mail.setSender(names[i]);
			 mail.setSubject(subjects[i]);
			 mail.setImportance((i%2==0)?0:i);
			 mails.add(mail);
		 }
	     Sort sort = new Sort(SORTING.NEWEST);
	     sort.quickSort(mails);
	     for(int i=0;i<mails.size();i++) {
	    	 Mail mail = (Mail)mails.get(i);
	    	 System.out.println(mail.getSubject()+"\t"+mail.getSender()+"\t"+mail.getImportance()+
	    			 "\t"+mail.attachments.size());
	     }
	}
}
