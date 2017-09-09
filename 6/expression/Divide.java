package expression;
public class Divide extends AbstractBinaryOperation {
	public Divide(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) {
		return a / b;
	}
}