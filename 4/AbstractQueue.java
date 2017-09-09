import java.util.function.Predicate;
import java.util.function.Function;
//Inv size >= 0, queue consists of size elements != null
public abstract class AbstractQueue implements Queue {
	protected int size;
	
	public abstract void enqueue(Object element);
	
	public abstract Object element();
	
	public abstract Object dequeue();
	
	//Pre: true
	public int size() {
		return size;
	}
	//Post: return size
	
	//Pre: true
	public boolean isEmpty() {
		return size == 0;
	}
	//Post: return size == 0
	
	public abstract void clear();
	
	protected abstract AbstractQueue makeEmpty();
	
	// Pre: true
	public AbstractQueue filter(Predicate <Object> p) {
		AbstractQueue newQueue = this.copy();
		for (int i = 0; i < size; i++) {
			if (p.test(newQueue.element())) {
				newQueue.enqueue(newQueue.element());
			}
			newQueue.dequeue();
		}
		return newQueue;
	}
	// Post: return queue of elemets satisfy the predicate
	
	// Pre: true
	public AbstractQueue map(Function <Object, Object> f) {
		AbstractQueue newQueue = this.copy();
		for (int i = 0; i < size; i++) {
			newQueue.enqueue(f.apply(newQueue.dequeue()));
		}
		return newQueue;
	}
	// Post: return queue containing the results of applying the function
	
	//Pre: true
	public Object[] toArray() {
		Object[] m = new Object[size];
		for (int i = 0; i < size; i++) {
				m[i] = this.element();
				this.enqueue(this.dequeue());
		}
		return m;
	}
	//Post: return array of elements of queue
	
	protected AbstractQueue copy() {
		AbstractQueue newQueue = this.makeEmpty();
		for (int i = 0; i < size; i++) {
			newQueue.enqueue(this.element());
			this.enqueue(this.dequeue());
		}
		return newQueue;
	}
}