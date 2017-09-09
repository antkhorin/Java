package expression;
public class Node <T extends MyNumber<T>>{
	byte mul;
	boolean add;
	int [] m;
	int n;
    AbstractOperation<T> element;
	AbstractOperation<T> term;
	AbstractOperation<T> expression;
    Node<T> prev;

    public Node(Node<T> prev) {
		m = new int[20];
		n = 0;
		add = false;
		mul = 0;
        element = null;
		term = null;
		expression = null;
		this.prev = prev;
    }
}
