package expression;
public class CheckedParser implements Parser {
	public static void main(String[] args) throws Exception {
		CheckedParser p = new CheckedParser();
		try {
			System.out.println(p.parse(args[0]).evaluate(0, 0, 0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	Node node;
	final byte negate = 1;
	final byte abs = 2;
	final byte sqrt = 3;
	
	public AbstractOperation parse(String s) throws Exception {
		node = new Node(null);
		s += " ";
		int i = 0;
		int b = 0;
		while(i < s.length()) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				int j = i;
				while (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
					i++;
				}
				try {
					if (node.m[node.n] == negate) {
						node.element = new Const(Integer.parseInt("-" + s.substring(j, i)));
						node.n--;
					} else {
						node.element = new Const(Integer.parseInt(s.substring(j, i)));
					}
				} catch (NumberFormatException e) {
					throw new Exception("overflow");
				}
				makeElement();
			} else if (s.charAt(i) >= 'x' && s.charAt(i) <= 'z') {
				node.element = new Variable("" + s.charAt(i++));
				makeElement();
			} else if (s.charAt(i) == '-' && node.element == null) {
				node.m[++node.n] = negate;
				i++;
			} else if (i + 3 < s.length() && s.substring(i, i + 3).equals("abs")) {
				node.m[++node.n] = abs;
				i += 3;
			} else if (i + 4 < s.length() && s.substring(i, i + 4).equals("sqrt")) {
				node.m[++node.n] = sqrt;
				i += 4;
			} else if (i + 2 < s.length() && (s.substring(i, i + 2).equals("**") || s.substring(i, i + 2).equals("//"))) {
				makeBlock();
				node.log = s.substring(i, i + 2).equals("**") ? false : true;
				i += 2;
			}
			else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
				makeTerm();
				node.mul = s.charAt(i++) == '*' ? true : false;
			} else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				makeExpression();
				node.add = s.charAt(i++) == '+' ? true : false;
			} else if (s.charAt(i) == '(') {
				node = new Node(node);
				i++;
				b++;
			} else if (s.charAt(i) == ')') {
				if (--b < 0) {
					throw new Exception("parsing error");
				}
				makeExpression();
				node.prev.element = node.expression;
				node = node.prev;
				makeElement();
				i++;
			} else if (s.charAt(i) == ' ' || s.charAt(i) == '\t') {
				i++;
				if (i == s.length()) {
					break;
				}
			} else {
				throw new Exception("parsing error");
			}
		}
		if (b > 0) {
			throw new Exception("parsing error");
		}
		makeExpression();
		return node.expression;
	}
	
	private void makeElement() {
		while (node.n > 0) {
			if (node.m[node.n] == negate) {
				node.element = new CheckedNegate(node.element);
			} else if (node.m[node.n] == abs) {
				node.element = new CheckedAbs(node.element);
			} else {
				node.element = new CheckedSqrt(node.element);
			}
			node.n--;
		}
	}
	
	private void makeBlock() throws Exception {
		if (node.element == null) {
			throw new Exception("parsing error");
		}
		if (node.block == null) {
			node.block = node.element;
		} else {
			node.block = node.log ? new CheckedLog(node.block, node.element) : new CheckedPow(node.block, node.element);
		}
		node.element = null;
	}
	
	private void makeTerm() throws Exception {
		makeBlock();
		if (node.term == null) {
			node.term = node.block;
		} else {
			node.term = node.mul ? new CheckedMultiply(node.term, node.block) : new CheckedDivide(node.term, node.block);
		}
		node.block = null;
	}
	
	private void makeExpression() throws Exception {
		makeTerm();
		if (node.expression == null) {
			node.expression = node.term;
		} else {
			node.expression = node.add ? new CheckedAdd(node.expression, node.term) : new CheckedSubtract(node.expression, node.term);
		}
		node.term = null;
	}
}