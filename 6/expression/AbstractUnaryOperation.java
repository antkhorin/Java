package expression;
public abstract class AbstractUnaryOperation extends AbstractOperation {
	protected AbstractOperation operation1;
	
	public int evaluate(int x) {
		int a = operation1.evaluate(x);
		return count(a);
	}
	
	public int evaluate(int x, int y, int z) {
		int a = operation1.evaluate(x, y, z);
		return count(a);
	}
	
	protected abstract int count(int a);
}