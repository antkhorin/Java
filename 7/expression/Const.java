package expression;
public class Const extends AbstractOperation {
	public int value;
	
	public Const(int value) {
		this.value = value;
	}

	public int evaluate(int x, int y, int z) {
		return value;
	}
}