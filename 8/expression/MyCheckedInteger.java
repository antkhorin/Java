package expression;
public class MyCheckedInteger implements MyNumber<MyCheckedInteger> {
	Integer value;
	
	public MyCheckedInteger(Number value) {
		this.value = value.intValue();
	}
	
	public MyCheckedInteger abs() throws Exception {
		if (this.value == Integer.MIN_VALUE) {
			throw new Exception("overflow");
		}
		return new MyCheckedInteger(Math.abs(this.value));
	}
	
	public MyCheckedInteger add(MyCheckedInteger number) throws Exception {
		Integer a = this.value;
		Integer b = number.value;
		if (b > 0 && a > Integer.MAX_VALUE - b || b < 0 && a < Integer.MIN_VALUE - b) {
			throw new Exception("overflow");
		}
		return new MyCheckedInteger(a + b);
	}
	
	public MyCheckedInteger subtract(MyCheckedInteger number) throws Exception {
		Integer a = this.value;
		Integer b = number.value;
		if (b > 0 && a < Integer.MIN_VALUE + b || b < 0 && a > Integer.MAX_VALUE + b) {
			throw new Exception("overflow");
		}
		return new MyCheckedInteger(a - b);
	}
	
	public MyCheckedInteger multiply(MyCheckedInteger number) throws Exception {
		Integer a = this.value;
		Integer b = number.value;
		if (b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b)) {
			throw new Exception("overflow");
		} else if (b < -1 && (a > Integer.MIN_VALUE / b || a < Integer.MAX_VALUE / b)){
			throw new Exception("overflow");
		} else if (b == -1 && a == Integer.MIN_VALUE) {
			throw new Exception("overflow");
		}
		return new MyCheckedInteger(a * b);
	}
	
	public MyCheckedInteger divide(MyCheckedInteger number) throws Exception {
		Integer a = this.value;
		Integer b = number.value;
		if (a == Integer.MIN_VALUE && b == -1 || b == 0) {
			throw b == 0 ? new Exception("division by zero") : new Exception("overflow");
		}	
		return new MyCheckedInteger(a / b);
	}
	
	public MyCheckedInteger mod(MyCheckedInteger number) throws Exception {
		Integer a = this.value;
		Integer b = number.value;
		if (a == Integer.MIN_VALUE && b == -1 || b == 0) {
			throw b == 0 ? new Exception("division by zero") : new Exception("overflow");
		}
		return new MyCheckedInteger(a % b);
	}
	
	public MyCheckedInteger make(Integer val) {
		return new MyCheckedInteger(val);
	}
	
	public Number toValue() {
		return value;
	}
}
