package expression;
import java.math.BigInteger;
public class GenericTabulator implements Tabulator {
	public Object[][][] tabulate(String mode, String expression, Integer x1, Integer x2, Integer y1, Integer y2, Integer z1, Integer z2) throws Exception {
		Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
		AbstractOperation<? extends MyNumber<?>> expr;
		if (mode.equals("i")) {
			MyCheckedInteger def = new MyCheckedInteger(x1);
			expr = new CheckedParser<MyCheckedInteger>().parse(expression, def);
		} else if (mode.equals("d")) {
			MyDouble def = new MyDouble(x1.doubleValue());
			expr = new CheckedParser<MyDouble>().parse(expression, def);
		} else if (mode.equals("bi")) {
			MyBigInteger def = new MyBigInteger(new BigInteger(x1.toString()));
			expr = new CheckedParser<MyBigInteger>().parse(expression, def);
		} else if (mode.equals("b")) {
			MyByte def = new MyByte(x1.byteValue());
			expr = new CheckedParser<MyByte>().parse(expression, def);
		} else if (mode.equals("f")) {
			MyFloat def = new MyFloat(x1.floatValue());
			expr = new CheckedParser<MyFloat>().parse(expression, def);
		} else {
			MyInteger def = new MyInteger(x1);
			expr = new CheckedParser<MyInteger>().parse(expression, def);
		}
		for (int i = 0; i <= x2 - x1; i++) {
			for (int j = 0; j <= y2 - y1; j++) {
				for (int k = 0; k <= z2 - z1; k++) {
					try {
						result[i][j][k] = expr.evaluate(x1 + i, y1 + j, z1 + k).toValue();
					} catch (Exception e) {
						result[i][j][k] = null;
					}
				}
			}
		}
		return result;
	}
}
