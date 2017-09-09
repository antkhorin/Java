package expression;
public class Square extends AbstractUnaryOperation {
	public Square(AbstractOperation operation1) {
		this.operation1 = operation1;
	}
	
	protected int count (int a) {
		return a * a;
	}
}