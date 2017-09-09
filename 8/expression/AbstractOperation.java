package expression;
public abstract class AbstractOperation<T extends MyNumber<T>> implements TripleExpression<T> {
	public abstract T evaluate(Integer x, Integer y, Integer z) throws Exception;
}