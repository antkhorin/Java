package expression;
public abstract class AbstractUnaryOperation<T extends MyNumber<T>> extends AbstractOperation<T> {
	protected AbstractOperation<T> operation1;
	
	public T evaluate(Integer x, Integer y, Integer z) throws Exception {
		T a = operation1.evaluate(x, y, z);
		return count(a);
	}
	
	protected abstract T count(T a) throws Exception;
}