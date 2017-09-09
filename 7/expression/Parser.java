package expression;
public interface Parser {
	public AbstractOperation parse(String expression) throws Exception;
}