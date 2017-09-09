package expression;
public class Node {
	boolean mod;
	boolean mul;
	boolean add;
	boolean shift;
    AbstractOperation element;
	AbstractOperation term;
	AbstractOperation block;
	AbstractOperation expression;
	int[] m;
	int n;
    Node prev;

    public Node(Node prev) {
		m = new int[20];
		n = 0;
		mod = false;
		mul = false;
		add = false;
		shift = false;
        element = null;
		term = null;
		block = null;
		expression = null;
		this.prev = prev;
    }
}
