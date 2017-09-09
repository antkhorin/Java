package expression;
public class MyByte implements MyNumber<MyByte> {
	Byte value;
	
	public MyByte(Number value) {
		this.value = value.byteValue();
	}
	
	public MyByte abs() {
		return new MyByte(((Integer)Math.abs(this.value)).byteValue());
	}
	
	public MyByte add(MyByte number) {
		return new MyByte(((Integer)(this.value + number.value)).byteValue());
	}
	
	public MyByte subtract(MyByte number) {
		return new MyByte(((Integer)(this.value - number.value)).byteValue());
	}
	
	public MyByte multiply(MyByte number) {
		return new MyByte(((Integer)(this.value * number.value)).byteValue());
	}
	
	public MyByte divide(MyByte number) {
		return new MyByte(((Integer)(this.value / number.value)).byteValue());
	}
	
	public MyByte mod(MyByte number) {
		return new MyByte(((Integer)(this.value % number.value)).byteValue());
	}
	
	public MyByte make(Integer val) {
		return new MyByte(val);
	}
	
	public Number toValue() {
		return value;
	}
}

