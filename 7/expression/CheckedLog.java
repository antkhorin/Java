package expression;
public class CheckedLog extends AbstractBinaryOperation {
	public CheckedLog(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) throws Exception {
		if (a <= 0 || b <= 0 || b == 1) {
			throw new Exception("log error");
		} else {
			int r = a;
			int i = 0;
			while (r >= b) {
				r /= b;
				i++;
			}
			return i;
		}
	}
}