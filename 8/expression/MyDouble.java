package expression;
public class MyDouble implements MyNumber<MyDouble> {
	Double value;
	
	public MyDouble(Number value) {
		this.value = value.doubleValue();
	}
	
	public MyDouble abs() {
		return new MyDouble(Math.abs(this.value));
	}
	
	public MyDouble add(MyDouble number) {
		return new MyDouble(this.value + number.value);
	}
	
	public MyDouble subtract(MyDouble number) {
		return new MyDouble(this.value - number.value);
	}
	
	public MyDouble multiply(MyDouble number) {
		return new MyDouble(this.value * number.value);
	}
	
	public MyDouble divide(MyDouble number) {
		return new MyDouble(this.value / number.value);
	}
	
	public MyDouble mod(MyDouble number) {
		return new MyDouble(this.value % number.value);
	}
	
	public MyDouble make(Integer val) {
		return new MyDouble(val);
	}
	
	public Number toValue() {
		return value;
	}
}
