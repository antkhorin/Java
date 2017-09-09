package expression;
public abstract class AbstractBinaryOperation<T extends MyNumber<T>> extends AbstractOperation<T> {
	protected AbstractOperation<T> operation1;
	protected AbstractOperation<T> operation2;
	
	public T evaluate(Integer x, Integer y, Integer z) throws Exception{
		T a = operation1.evaluate(x, y, z);
		T b = operation2.evaluate(x, y, z);
		return count(a, b);
	}
	
	protected abstract T count(T a, T b) throws Exception;
}