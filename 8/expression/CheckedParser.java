package expression;
import java.math.BigInteger;
public class CheckedParser<T extends MyNumber<T>> implements Parser<T> {
	public static void main(String[] args) throws Exception {
		// CheckedParser p = new CheckedParser();
		// try {
			// System.out.println(p.parse(args[0]).evaluate(0, 0, 0));
		// } catch (Exception e) {
			// System.out.println(e.getMessage());
		// }
	}
	
	Node<T> node;
	final byte negate = 1;
	final byte abs = 2;
	final byte square = 3;
	
	public AbstractOperation<T> parse(String s, T def) throws Exception {
		node = new Node<T>(null);
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
					String str = s.substring(j, i);
					if (node.m[node.n] == negate) {
						str = "-" + str;
						node.n--;
					}
					node.element = new Const<T>(def.make(Integer.parseInt(str)));
				} catch (NumberFormatException e) {
					throw new Exception("overflow");
				}
				makeElement();
			} else if (s.charAt(i) >= 'x' && s.charAt(i) <= 'z') {
				node.element = new Variable<T>("" + s.charAt(i++), def);
				makeElement();
			} else if (s.charAt(i) == '-' && node.element == null) {
				node.m[++node.n] = negate;
				i++;
			} else if (i + 3 < s.length() && s.substring(i, i + 3).equals("abs") && node.element == null) {
				node.m[++node.n] = abs;
				i += 3;
			} else if (i + 6 < s.length() && s.substring(i, i + 6).equals("square") && node.element == null) {
				node.m[++node.n] = square;
				i += 6;
			} else if (i + 3 < s.length() && s.substring(i, i + 3).equals("mod") || s.charAt(i) == '*' || s.charAt(i) == '/') {
				makeTerm();
				if (s.charAt(i) == '*') {
					node.mul = 1;
				} else if (s.charAt(i) == '/') {
					node.mul = 2;
				} else {
					i += 2;
					node.mul = 3;
				}
				i++;
			} else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				makeExpression();
				node.add = s.charAt(i++) == '+' ? true : false;
			} else if (s.charAt(i) == '(') {
				node = new Node<T>(node);
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
				node.element = new CheckedSubtract<T>(new CheckedSubtract<T>(node.element, node.element), node.element);
			} else if (node.m[node.n] == abs) {
				node.element = new CheckedAbs<T>(node.element);
			} else {
				node.element = new CheckedMultiply<T>(node.element, node.element);
			}
			node.n--;
		}
	}
	
	private void makeTerm() throws Exception {
		if (node.element == null) {
			throw new Exception("parsing error");
		}
		if (node.term == null) {
			node.term = node.element;
		} else {
			if (node.mul == 1) {
				node.term = new CheckedMultiply<T>(node.term, node.element);
			} else if (node.mul == 2) {
				node.term = new CheckedDivide<T>(node.term, node.element);
			} else {
				node.term = new CheckedMod<T>(node.term, node.element);
			}
		}
		node.element = null;
	}
	
	private void makeExpression() throws Exception {
		makeTerm();
		if (node.expression == null) {
			node.expression = node.term;
		} else {
			node.expression = node.add ? new CheckedAdd<T>(node.expression, node.term) : new CheckedSubtract<T>(node.expression, node.term);
		}
		node.term = null;
	}
}