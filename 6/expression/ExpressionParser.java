package expression;
public class ExpressionParser implements Parser {
	public static void main(String[] args) {
		// ExpressionParser block = new ExpressionParser();
		// System.out.println(block.parse(args[0]).evaluate(0));
	}
	
	Node node = new Node(null);
	final byte unaryMinus = 1;
	final byte abs = 2;
	final byte square = 3;
	
	public AbstractOperation parse(String s) {
		s += " ";
		int i = 0;
		while(i < s.length()) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				int number = 0;
				while (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
					number = number * 10 + s.charAt(i++) - 48;
				}
				node.element = new Const(number);
				makeElement();
			} else if (s.charAt(i) >= 120 && s.charAt(i) <= 122) {
				node.element = new Variable("" + s.charAt(i++));
				makeElement();
			} else if ((s.charAt(i) == '-' || (i + 2 < s.length() && s.substring(i, i + 3).equals("abs")) || (i + 5 < s.length() && s.substring(i, i + 6).equals("square"))) && node.element == null) {
				if (s.charAt(i) == '-') {
					node.m[++node.n] = unaryMinus;
					i++;
				} else if (s.substring(i, i + 3).equals("abs")) {
					node.m[++node.n] = abs;
					i += 3;
				} else {
					node.m[++node.n] = square;
					i += 6;
				}
			} else if (s.charAt(i) == '*' || s.charAt(i) == '/' || (i + 3 <= s.length() && s.substring(i, i + 3).equals("mod"))) {
				makeTerm();
				if (s.charAt(i) == '*') {
					node.mul = true;
				} else if (s.charAt(i) == '/'){
					node.mul = false;
				} else {
					node.mod = true;
					i += 2;
				}
				i++;
			} else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				makeBlock();
				node.add = s.charAt(i++) == '+' ? true : false;
			} else if (i + 2 <= s.length() && (s.substring(i, i + 2).equals("<<") || s.substring(i, i + 2).equals(">>"))) {
				makeExpression();
				node.shift = s.substring(i++, ++i).equals("<<") ? false : true;
			} else if (s.charAt(i) == '(') {
				node = new Node(node);
				i++;
			} else if (s.charAt(i) == ')') {
				makeExpression();
				node.prev.element = node.expression;
				node = node.prev;
				makeElement();
				i++;
			} else i++;
		}
		makeExpression();
		return node.expression;
	}
	
	private void makeElement() {
		while (node.n > 0) {
			if (node.m[node.n] == square) {
				node.element = new Square(node.element);
			} else if (node.m[node.n] == abs) {
				node.element = new Abs(node.element);
			} else {
				node.element = new Subtract(new Const(0), node.element);
			}
			node.n--;
		}
	}
	
	private void makeTerm() {
		if (node.term == null) {
			node.term = node.element;
		} else {
			if (node.mod) {
				node.term = new Mod(node.term, node.element);
			} else {
				node.term = node.mul ? new Multiply(node.term, node.element) : new Divide(node.term, node.element);
			}
		}
		node.mod = false;
		node.element = null;
	}
	
	private void makeBlock() {
		makeTerm();
		if (node.block == null) {
			node.block = node.term;
		} else {
			node.block = node.add ? new Add(node.block, node.term) : new Subtract(node.block, node.term);
		}
		node.term = null;
	}
	
	private void makeExpression() {
		makeBlock();
		if (node.expression == null) {
			node.expression = node.block;
		} else {
			node.expression = node.shift ? new RightShift(node.expression, node.block) : new LeftShift(node.expression, node.block);
		}
		node.block = null;
	}
}