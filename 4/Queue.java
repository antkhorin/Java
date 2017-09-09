import java.util.function.Predicate;
import java.util.function.Function;
public interface Queue {
    void enqueue(Object element);
    Object element();
    Object dequeue();
    int size();
    boolean isEmpty();
    void clear();
	AbstractQueue filter(Predicate <Object> p);
	AbstractQueue map(Function <Object, Object> f);
	Object[] toArray();
}
