package expression;
public class CheckedMultiply extends AbstractBinaryOperation {
	public CheckedMultiply(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) throws Exception {
		if (b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b)) {
			throw new Exception("overflow");
		} else if (b < -1 && (a > Integer.MIN_VALUE / b || a < Integer.MAX_VALUE / b)){
			throw new Exception("overflow");
		} else if (b == -1 && a == Integer.MIN_VALUE) {
			throw new Exception("overflow");
		} else {
			return a * b;
		}
	}
}