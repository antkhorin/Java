package expression;
public interface MyNumber <T extends MyNumber<T>> {
	public abstract T abs() throws Exception;
	
	public abstract T add(T number) throws Exception;
	
	public abstract T subtract(T number) throws Exception;
	
	public abstract T multiply(T number) throws Exception;
	
	public abstract T divide(T number) throws Exception;
	
	public abstract T mod(T number) throws Exception;
	
	public abstract T make(Integer val);
	
	public abstract Number toValue();
}
