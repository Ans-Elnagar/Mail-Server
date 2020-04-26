package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IPriorityQueue;
public class PriorityQueue implements IPriorityQueue {
	private class Node{
		private Object element;
		private int key;
		private Node next;
		public Node(Object element,int key,Node next) {
			this.setElement(element);
			this.setKey(key);
			this.setNext(next);
		}
		public Object getElement() {
			return element;
		}
		public void setElement(Object element) {
			this.element = element;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
	}
	private Node min;
	private int size;
	@Override
	public void insert(Object item, int key) {
		Node v=new Node(item,key,null);
		if(isEmpty())
			min=v;
		else {
			if(v.getKey()<min.getKey()) {
				v.setNext(min);
				min=v;
			}
			else {
				Node curr=min.getNext(),prev=min;
				while(curr!=null) {
					if(v.getKey()<curr.getKey()) {
						v.setNext(curr);
						prev.setNext(v);
						break;
					}
					prev=curr;
					curr=curr.getNext();
				}
				if(curr==null)
					prev.setNext(v);
			}
		}
		size++;
	}

	@Override
	public Object removeMin() {
		if(isEmpty())
			throw new RuntimeException("The queue is empty");
		Object element = min.getElement();
		min=min.getNext();
		size--;
		return element;
	}

	@Override
	public Object min() {
		return min.getElement();
	}

	@Override
	public boolean isEmpty() {
		return (size==0);
	}

	@Override
	public int size() {
		return size;
	}

}
