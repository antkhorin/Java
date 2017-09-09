package expression;
public interface TripleExpression<T extends MyNumber<T>> {
	public T evaluate(Integer x, Integer y, Integer z) throws Exception;
}