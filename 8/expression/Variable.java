package expression;
import java.math.BigInteger;
public class Variable<T extends MyNumber<T>> extends AbstractOperation<T> {
	public String name;
	T def;
	
	public Variable(String name, T def) {
		this.name = name;
		this.def = def; 
	}

	public T evaluate(Integer x, Integer y, Integer z) {
		if (name.equals("x")) {
			return def.make(x);
		} else if (name.equals("y")) {
			return def.make(y);
		} else {
			return def.make(z);
		}
	}
}