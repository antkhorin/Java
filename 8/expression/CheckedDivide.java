package expression;
import java.math.BigInteger;
public class CheckedDivide<T extends MyNumber<T>> extends AbstractBinaryOperation<T> {
	public CheckedDivide(AbstractOperation<T> operation1, AbstractOperation<T> operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected T count(T a, T b) throws Exception {
		return a.divide(b);
	}
}