package expression;
public class MyFloat implements MyNumber<MyFloat> {
	Float value;
	
	public MyFloat(Number value) {
		this.value = value.floatValue();
	}
	
	public MyFloat abs() {
		return new MyFloat(Math.abs(this.value));
	}
	
	public MyFloat add(MyFloat number) {
		return new MyFloat(this.value + number.value);
	}
	
	public MyFloat subtract(MyFloat number) {
		return new MyFloat(this.value - number.value);
	}
	
	public MyFloat multiply(MyFloat number) {
		return new MyFloat(this.value * number.value);
	}
	
	public MyFloat divide(MyFloat number) {
		return new MyFloat(this.value / number.value);
	}
	
	public MyFloat mod(MyFloat number) {
		return new MyFloat(this.value % number.value);
	}
	
	public MyFloat make(Integer val) {
		return new MyFloat(val);
	}
	
	public Number toValue() {
		return value;
	}
}
