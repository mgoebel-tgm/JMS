package Start;
/**
 * Helfende Methoden zur Uberpruefung von Argumenten
 * @author Melanie Goebel
 * @version 2014-11-18
 */
public class CheckArguments {
	
	/**
	 * Ueberprueft ob es ein "gueltiger" Username ist (3-15 Zeichen ( a-z, 0-9, _ , -)
	 * @param name der ueberpruefende Name
	 * @return ob es gueltig ist
	 */
	public static boolean checkUsername(String name){
		String USERNAME_PATTERN= "^[a-z0-9_-]{3,15}$";
		if(name.matches(USERNAME_PATTERN))
			return true;
		System.err.println("Not a valid username, it need to have 3-15 characters (include numbers, underline, minus)");
		return false;
	}
	/**
	 * Ueberprueft ob es eine gueltige IP-Adresse ist (vom Format her)
	 * @param address die zu pruefende adresse
	 * @return ob es gueltig ist
	 */
	public static boolean checkIP(String address) {
		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		if ((address.matches(IPADDRESS_PATTERN)))
			return true;
		System.err.println("Not a valid IP-Address.");
		return false;
	}
	
	
}
