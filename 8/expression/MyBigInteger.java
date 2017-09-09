package expression;
import java.math.BigInteger;
public class MyBigInteger implements MyNumber<MyBigInteger> {
	BigInteger value;
	
	public MyBigInteger(Number value) {
		this.value = new BigInteger(value.toString());
	}
	
	public MyBigInteger abs() {
		return new MyBigInteger(this.value.abs());
	}
	
	public MyBigInteger add(MyBigInteger number) {
		return new MyBigInteger(this.value.add(number.value));
	}
	
	public MyBigInteger subtract(MyBigInteger number) {
		return new MyBigInteger(this.value.subtract(number.value));
	}
	
	public MyBigInteger multiply(MyBigInteger number) {
		return new MyBigInteger(this.value.multiply(number.value));
	}
	
	public MyBigInteger divide(MyBigInteger number) {
		return new MyBigInteger(this.value.divide(number.value));
	}
	
	public MyBigInteger mod(MyBigInteger number) {
		return new MyBigInteger(this.value.remainder(number.value));
	}
	
	public MyBigInteger make(Integer val) {
		return new MyBigInteger(val);
	}
	
	public Number toValue() {
		return value;
	}
}
