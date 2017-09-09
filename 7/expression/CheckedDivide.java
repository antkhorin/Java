package expression;
public class CheckedDivide extends AbstractBinaryOperation {
	public CheckedDivide(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) throws Exception {
		if (a == Integer.MIN_VALUE && b == -1 || b == 0) {
			throw b == 0 ? new Exception("division by zero") : new Exception("overflow");
		} else {
			return a / b;
		}
	}
}