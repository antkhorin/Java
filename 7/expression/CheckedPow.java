package expression;
public class CheckedPow extends AbstractBinaryOperation {
	public CheckedPow(AbstractOperation operation1, AbstractOperation operation2) {
		this.operation1 = operation1;
		this.operation2 = operation2;
	}
	
	protected int count (int a, int b) throws Exception {
		if (b < 0 || a == 0 && b == 0) {
			throw new Exception("pow error");
		} else {
			int r = 1;
			while (b > 0) {
				if (b % 2 == 1) {
					r = new CheckedMultiply(null, null).count(a, r);
				}
				if (b > 1) {
					a = new CheckedMultiply(null, null).count(a, a);
				}
				b >>= 1;
			}
			return r;
		}
	}
}