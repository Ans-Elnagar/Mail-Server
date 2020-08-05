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
				return 1;
			else if(num1<num2)
				return -1;
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
}
