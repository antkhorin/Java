package expression;
public abstract class AbstractUnaryOperation extends AbstractOperation {
	protected AbstractOperation operation1;
	
	public int evaluate(int x, int y, int z) throws Exception {
		int a = operation1.evaluate(x, y, z);
		return count(a);
	}
	
	protected abstract int count(int a) throws Exception;
}