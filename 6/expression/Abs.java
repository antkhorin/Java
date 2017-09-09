package expression;
public class Abs extends AbstractUnaryOperation {
	public Abs(AbstractOperation operation1) {
		this.operation1 = operation1;
	}
	
	protected int count (int a) {
		return Math.abs(a);
	}
}