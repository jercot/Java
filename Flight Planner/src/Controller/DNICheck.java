package Controller;

public class DNICheck {
	public static boolean check(String c) {
		if(!c.equals("")) {
			String dniFix = c;
			int dniNum = 0;
			String checkString ="TRWAGMYFPDXBNJZSQVHLCKE";
			
			if (dniFix.length()!=10&&dniFix.length()!=9)
				return false;

			if (dniFix.length()==10)
				if (c.charAt(8) == '-') 
					dniFix = c.substring(0,8) + c.substring(9,10);
				else
					return false;

			dniNum = Integer.parseInt(dniFix.substring(0,8), 10);
			char dniLet1 = dniFix.charAt(8);

			dniLet1 = Character.toUpperCase(dniLet1);

			int dniMod = dniNum % 23;

			char dniLet2 = checkString.charAt(dniMod);

			if (dniLet1!=dniLet2) 
				return false;

			return true;
		}
		return false;
	}
}