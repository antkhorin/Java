//Inv size >= 0, queue consists of size elements != null
public class LinkedQueue extends AbstractQueue implements Queue {
	private Node start;
	private Node end;
	
	public LinkedQueue() {
		size = 0;
		start = new Node(null, null);
		end = start;
		
	}
	
	//Pre: element != null
	public void enqueue(Object element) {
		assert element != null;
		end.value = element;
		Node a = end;
		end = new Node(null, null);
		a.next = end;
		size++;
	}
	//Post: size' = size + 1, element was added in the end of queue
	
	//Pre: size > 0
	public Object element() {
		assert size > 0;
		return start.value;
	}
	//Post: return element at the beginning of queue
	
	//Pre: size > 0
	public Object dequeue() {
		assert size > 0;
		Object a = start.value;
		start = start.next;
		size--;
		return a;
	}
	//Post: size' = size - 1, return and delete element in the beginning of queue
	
	//Pre: true
	public void clear() {
		size = 0;
		start = end;
	}
	//Post: size' = 0, queue was cleared
	
	protected LinkedQueue makeEmpty() {
		return new LinkedQueue();
	}
}