//Inv size >= 0, elements[start..end] != null
public class ArrayQueueADT {
	public static void main(String[] args) {
	}
	
	private int start = 0;
	private int end = 0;
	private int size = 0;
	private Object[] elements = new Object[10];
	
	public static void ensureCapasity(ArrayQueueADT queue) {
		if ((queue.size < 3) || !((queue.size == queue.elements.length - 1) || (queue.size < queue.elements.length / 4))) {
			return;
		} else {
			Object[] m = new Object[queue.elements.length];
			if (queue.end >= queue.start) {
				for (int i = queue.start; i < queue.end; i++) {
					m[i - queue.start] = queue.elements[i];
				}
			} else {
				int j = 0;
				for (int i = queue.start; i < queue.elements.length; i++) {
					m[j++] = queue.elements[i];
				}
				for (int i = 0; i < queue.end; i++) {
					m[j++] = queue.elements[i];
				}
			}
			if (queue.size == queue.elements.length - 1) {
				queue.elements = new Object[queue.elements.length * 2];
			} else {
				queue.elements = new Object[queue.elements.length / 2];
			}
			for (int i = 0; i < queue.size; i++) {
					queue.elements[i] = m[i];
			}
			queue.start = 0;
			queue.end = queue.size;
		}
	}
	
	//Pre: element != null
	public static void enqueue(ArrayQueueADT queue, Object element) {
		assert element != null;
		ensureCapasity(queue);
		queue.elements[queue.end++] = element;
		queue.size++;
		if (queue.end == queue.elements.length) {
			queue.end = 0;
		}
	}
	//Post: size' = size + 1, element добавлен в конец очереди
	
	//Pre: element != null
	public static void push(ArrayQueueADT queue, Object element) {
		assert element != null;
		ensureCapasity(queue);
		if (queue.start == 0) {
			queue.start = queue.elements.length;
		}
		queue.elements[--queue.start] = element;
		queue.size++;
	}
	//Post: size' = size + 1, element добавлен в начало очереди
	
	//Pre: size > 0
	public static Object element(ArrayQueueADT queue) {
		assert queue.size > 0;
		return queue.elements[queue.start];
	}
	//Post: return первый элемент в очереди
	
	//Pre: size > 0
	public static Object peek(ArrayQueueADT queue) {
		assert queue.size > 0;
		if (queue.end == 0) {
			return queue.elements[queue.elements.length - 1];
		} else {
			return queue.elements[queue.end - 1];
		}
	}
	//Post: return последний элемент в очереди
	
	//Pre: size > 0
	public static Object dequeue(ArrayQueueADT queue) {
		assert queue.size > 0;
		queue.size--;
		Object a = queue.elements[queue.start++];
		if (queue.start == queue.elements.length) {
			queue.start = 0;
		}
		ensureCapasity(queue);
		return a;
		
	}
	//Post: size' = size - 1, return первый элемент в очереди и удален из очереди
	
	//Pre: size > 0
	public static Object remove(ArrayQueueADT queue) {
		assert queue.size > 0;
		queue.size--;
		if (queue.end == 0) {
			queue.end = queue.elements.length;
		}
		Object a = queue.elements[--queue.end];
		ensureCapasity(queue);
		return a;
	}
	//Post:size' = size - 1, return первый элемент в очереди и удален из очереди
	
	//Pre: true
	public static int size(ArrayQueueADT queue) {
		return queue.size;
	}
	//Post: return size
	
	//Pre: true
	public static boolean isEmpty(ArrayQueueADT queue) {
		return queue.size == 0;
	}
	//Post: return size == 0
	
	//Pre: true
	public static void clear(ArrayQueueADT queue) {
		queue.start = 0;
		queue.end = 0;
		queue.size = 0;
	}
	//Post: size == 0, start == 0, end == 0 
	
	//Pre: size > 0
	public static Object[] toArray(ArrayQueueADT queue) {
		Object[] m = new Object[queue.size];
		if (queue.end >= queue.start) {
			for (int i = queue.start; i < queue.end; i++) {
				m[i - queue.start] = queue.elements[i];
			}
		} else {
			int j = 0;
			for (int i = queue.start; i < queue.elements.length; i++) {
				m[j++] = queue.elements[i];
			}
			for (int i = 0; i < queue.end; i++) {
				m[j++] = queue.elements[i];
			}
		}
		return m;
	}
	//Post: return массив из элементов очереди по порядку
}