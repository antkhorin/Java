public class Sum {
	public static void main(String[] args) {
		ArrayQueue queue = new ArrayQueue();
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
		}
		Object[] m = queue.toArray();
		int sum = 0;
		for (int i = 0; i < queue.size(); i++) {
			sum += (int)m[i];
		}
		System.out.println(sum);
	}
}