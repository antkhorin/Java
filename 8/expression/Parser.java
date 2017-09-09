package expression;
public interface Parser<T extends MyNumber<T>> {
	public AbstractOperation<T> parse(String expression, T def) throws Exception;
}