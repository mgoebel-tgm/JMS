package Start;
/**
 * Helfende Methoden zur Uberpruefung von Argumenten
 * @author Melanie Goebel
 * @version 2014-11-18
 */
public class CheckArguments {
	
	/**
	 * Ueberprueft ob es eine gueltige IP-Adresse ist (vom Format her)
	 * @param address die zu pruefende adresse
	 * @return ob es gueltig ist
	 */
	public static boolean checkIP(String address) {
		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		if ((address.matches(IPADDRESS_PATTERN)))
			return true;
		return false;
	}
}
