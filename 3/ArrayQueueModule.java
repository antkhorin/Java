//Inv size >= 0, elements[start..end] != null
public class ArrayQueueModule {
	public static void main(String[] args) {
		ArrayQueueModule.enqueue(3);
		ArrayQueueModule.push(4);
		System.out.println(ArrayQueueModule.peek());
	}
	
	private static int start = 0;
	private static int end = 0;
	private static int size = 0;
	private static Object[] elements = new Object[10];
	
	public static void ensureCapasity() {
		if ((size < 3) || !((size == elements.length - 1) || (size < elements.length / 4))) {
			return;
		} else {
			Object[] m = new Object[elements.length];
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
	public static void enqueue(Object element) {
		assert element != null;
		ensureCapasity();
		elements[end++] = element;
		size++;
		if (end == elements.length) {
			end = 0;
		}
	}
	//Post: size' = size + 1, element добавлен в конец очереди
	
	//Pre: element != null
	public static void push(Object element) {
		assert element != null;
		ensureCapasity();
		if (start == 0) {
			start = elements.length;
		}
		elements[--start] = element;
		size++;
	}
	//Post: size' = size + 1, element добавлен в начало очереди
	
	//Pre: size > 0
	public static Object element() {
		assert size > 0;
		return elements[start];
	}
	//Post: return первый элемент в очереди
	
	//Pre: size > 0
	public static Object peek() {
		assert size > 0;
		if (end == 0) {
			return elements[elements.length - 1];
		} else {
			return elements[end - 1];
		}
	}
	//Post: return последний элемент в очереди
	
	//Pre: size > 0
	public static Object dequeue() {
		assert size > 0;
		size--;
		Object a = elements[start++];
		if (start == elements.length) {
			start = 0;
		}
		ensureCapasity();
		return a;
		
	}
	//Post: size' = size - 1, return первый элемент в очереди и удален из очереди
	
	//Pre: size > 0
	public static Object remove() {
		assert size > 0;
		size--;
		if (end == 0) {
			end = elements.length;
		}
		Object a = elements[--end];
		ensureCapasity();
		return a;
	}
	//Post:size' = size - 1, return первый элемент в очереди и удален из очереди
	
	//Pre: true
	public static int size() {
		return size;
	}
	//Post: return size
	
	//Pre: true
	public static boolean isEmpty() {
		return size == 0;
	}
	//Post: return size == 0
	
	//Pre: true
	public static void clear() {
		start = 0;
		end = 0;
		size = 0;
	}
	//Post: size == 0, start == 0, end == 0 
	
	//Pre: size > 0
	public static Object[] toArray() {
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
		return m;
	}
	//Post: return массив из элементов очереди по порядку
}