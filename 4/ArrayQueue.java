//Inv size >= 0, queue consists of size elements != null
public class ArrayQueue extends AbstractQueue implements Queue{
	private int start;
	private int end;
	private Object[] elements;
	
	public ArrayQueue() {
		this.start = 0;
		this.end = 0;
		this.size = 0;
		this.elements = new Object[10];
	}
		
	private void ensureCapasity() {
		if ((size == elements.length - 1) || ((size > 3) && (size < elements.length / 4))) {
			Object[] m = new Object[size];
			if (end >= start) {
				for (int i = start; i < end; i++) {
					m[i - start] = elements[i];
				}
			} else {
				int j = 0;
				for (int i = start; i < elements.length; i++) {
					m[j++] = elements[i];
				}
				for (int i = 0; i < end; i++) {
					m[j++] = elements[i];
				}
			}
			if (size == elements.length - 1) {
				elements = new Object[elements.length * 2];
			} else {
				elements = new Object[elements.length / 2];
			}
			for (int i = 0; i < size; i++) {
					elements[i] = m[i];
			}
			start = 0;
			end = size;
		}
	}
	
	//Pre: element != null
	public void enqueue(Object element) {
		assert element != null;
		ensureCapasity();
		elements[end++] = element;
		size++;
		if (end == elements.length) {
			end = 0;
		}
	}
	//Post: size' = size + 1, element was added in the end of queue
	
	//Pre: size > 0
	public Object element() {
		assert size > 0;
		return elements[start];
	}
	//Post: return element at the beginning of queue
	
	//Pre: size > 0
	public Object dequeue() {
		assert size > 0;
		size--;
		Object a = elements[start++];
		if (start == elements.length) {
			start = 0;
		}
		ensureCapasity();
		return a;
		
	}
	//Post: size' = size - 1, return and delete element at the beginning of queue
	
	//Pre: true
	public void clear() {
		start = 0;
		end = 0;
		size = 0;
		elements = new Object[10];
	}
	//Post: size' = 0, queue was cleared
	
	protected ArrayQueue makeEmpty() {
		return new ArrayQueue();
	}
}