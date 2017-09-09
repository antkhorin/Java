package expression;
public class CheckedSubtract extends AbstractBinaryOperation {
	public CheckedSubtract(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) throws Exception {
		if (b > 0 && a < Integer.MIN_VALUE + b || b < 0 && a > Integer.MAX_VALUE + b) {
			throw new Exception("overflow");
		} else {
			return a - b;
		}
	}
}