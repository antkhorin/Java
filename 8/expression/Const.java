package expression;
import java.math.BigInteger;
public class Const<T extends MyNumber<T>> extends AbstractOperation<T> {
	T value;

	public Const(T value) {
		this.value = value;
	}
	
	public T evaluate(Integer x, Integer y, Integer z) throws Exception {
		return value;
	}
}