import java.util.Scanner;

public class SumLongHex {
	public static void main(String[] args) {
		long sum = 0;
		String str;
        for (int i = 0; i < args.length; i++) {
			Scanner s1 = new Scanner(args[i]);
			while (s1.hasNext()) {
				if (s1.hasNextLong()) {
					sum += s1.nextLong();
				} else {
					str = s1.next();
					if ((str.charAt(0) != '0') || ((str.charAt(1) != 'x') && (str.charAt(1) != 'X'))) {
						System.out.println("Invalid Data");
						return;
					}
					str = str.substring(2);
					try {
						Long.parseUnsignedLong(str, 16);
					} catch (NumberFormatException e) {
						System.out.println("Invalid Data");
						return;
					}
					sum += Long.parseUnsignedLong(str, 16);
				}
			}
		}
		System.out.println(sum);
    }
}
