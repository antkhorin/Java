public class BinarySearchSpan {
	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println("Invalid Data");
		} else {
			int i = search(args, Integer.parseInt(args[0]), 0, args.length);
			int left = i, right = args.length, j, key = Integer.parseInt(args[0]);
			//Inv: args[left] >= key, args[right] < key
			while (left + 1 < right) {
				j = left + (right - left) / 2;
				if (key <= Integer.parseInt(args[j])){
					left = j;
				} else {
					right = j;
				}
			}
			System.out.println(i + " " + (left - i));
			
		}
	}
	//Pre: args.length > 0, args состоит из чисел и упорядочен по невозрастанию, по одному числу в строке
	//left + 1 <= right
	private static int search(String[] args, int key, int left, int right) {
		if (left + 1 == right){
			return left;
		} else {
			int i = left + (right - left) / 2;
			if (key < Integer.parseInt(args[i])) {
				i = search(args, key, i, right);
			} else {
				i = search(args, key, left, i);
			}
			return i;
		}
	}
	//Post: left + 1 == right, args[i] <= args[0]
}