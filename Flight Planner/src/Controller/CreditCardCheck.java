package Controller;

public class CreditCardCheck {
	public static boolean check(String check) {
		int sum = 0;
		boolean alternate = false;
		if(check.length()!=16)
			return false;
		for (int i = check.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(check.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}
}