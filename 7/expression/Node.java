package expression;
public class Node {
	boolean mul;
	boolean add;
	boolean log;
	int [] m;
	int n;
    AbstractOperation element;
	AbstractOperation block;
	AbstractOperation term;
	AbstractOperation expression;
    Node prev;

    public Node(Node prev) {
		m = new int[20];
		n = 0;
		log = false;
		add = false;
		mul = false;
        element = null;
		block = null;
		term = null;
		expression = null;
		this.prev = prev;
    }
}
