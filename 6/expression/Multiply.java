package expression;
public class Multiply extends AbstractBinaryOperation {
	public Multiply(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) {
		return a * b;
	}
}