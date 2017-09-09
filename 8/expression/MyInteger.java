package expression;
public class MyInteger implements MyNumber<MyInteger> {
	Integer value;
	
	public MyInteger(Number value) {
		this.value = value.intValue();
	}
	
	public MyInteger abs() {
		return new MyInteger(Math.abs(this.value));
	}
	
	public MyInteger add(MyInteger number) {
		return new MyInteger(this.value + number.value);
	}
	
	public MyInteger subtract(MyInteger number) {
		return new MyInteger(this.value - number.value);
	}
	
	public MyInteger multiply(MyInteger number) {
		return new MyInteger(this.value * number.value);
	}
	
	public MyInteger divide(MyInteger number) {
		return new MyInteger(this.value / number.value);
	}
	
	public MyInteger mod(MyInteger number) {
		return new MyInteger(this.value % number.value);
	}
	
	public MyInteger make(Integer val) {
		return new MyInteger(val);
	}
	
	public Number toValue() {
		return value;
	}
}
