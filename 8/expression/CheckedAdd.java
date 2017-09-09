package expression;
import java.math.BigInteger;
public class CheckedAdd<T extends MyNumber<T>> extends AbstractBinaryOperation<T> {
	public CheckedAdd(AbstractOperation<T> operation1, AbstractOperation<T> operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}

	protected T count(T a, T b) throws Exception {
		return a.add(b);
	}
}