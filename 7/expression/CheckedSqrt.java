package expression;
public class CheckedSqrt extends AbstractUnaryOperation {
	public CheckedSqrt(AbstractOperation operation1) {
		this.operation1 = operation1;
	}
	
	protected int count (int a) throws Exception {
		if (a < 0) {
			throw new Exception("sqrt from number < 0");
		} else {
			int l = -1;
			int r = 46341;
			int m;
			while (l + 1 < r) {
				m = (l + r) / 2;
				if (m * m <= a) {
					l = m;
				} else {
					r = m;
				}
			}
			return l;
		}
	}
}