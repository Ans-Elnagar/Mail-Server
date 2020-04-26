package eg.edu.alexu.csd.datastructure.mailServer.LogicClasses;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IArrayBased;
import eg.edu.alexu.csd.datastructure.mailServer.Interfaces.IQueue;
public class ArrQueue implements IQueue,IArrayBased {
	//the number of exact elements in the array 
	private int size;
	private Object[] queue;
	//insert and dequeue pointers
	private int insp=0;
	private int outp=0;
	// preventing the use of empty constructor
	@SuppressWarnings("unused")
	private ArrQueue() { }
	public ArrQueue(int length) {
		queue=new Object[length];
	}
	@Override
	public void enqueue(Object item) {
		if(size==queue.length)
			throw new RuntimeException("The array is already full");
		queue[insp]=item;
		insp = (insp+1)%queue.length;
		size++;
	}
	@Override
	public Object dequeue() {
		if(isEmpty())
			throw new RuntimeException("The array is empty");
		Object element=queue[outp];
		queue[outp]=null;
		outp = (outp+1)%queue.length;
		size--;
		return element;
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
