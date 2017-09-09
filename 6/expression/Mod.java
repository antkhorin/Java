package expression;
public class Mod extends AbstractBinaryOperation {
	public Mod(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) {
		return a % b;
	}
}