package expression;
public class Variable extends AbstractOperation {
	public String name;
	
	public Variable(String name) {
		this.name = name;
	}

	public int evaluate(int x, int y, int z) {
		if (name.equals("x")) {
			return x;
		} else if (name.equals("y")) {
			return y;
		} else if (name.equals("z")) {
			return z;
		}
		return 0;
	}
}