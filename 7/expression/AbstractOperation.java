package expression;
public abstract class AbstractOperation implements TripleExpression {
	public abstract int evaluate(int x, int y, int z) throws Exception;
}