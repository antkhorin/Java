package expression;
public abstract class AbstractBinaryOperation extends AbstractOperation {
	protected AbstractOperation operation1;
	protected AbstractOperation operation2;
	
	public int evaluate(int x, int y, int z) throws Exception{
		int a = operation1.evaluate(x, y, z);
		int b = operation2.evaluate(x, y, z);
		return count (a, b);
	}
	
	protected abstract int count(int a, int b) throws Exception;
}