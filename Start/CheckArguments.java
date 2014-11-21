package Start;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
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
		String USERNAME_PATTERN= "^[a-z A-Z 0-9_-]{3,15}$";
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
	public static boolean checkIPFormat(String address) {
		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		if ((address.matches(IPADDRESS_PATTERN)))
			return true;
		System.err.println("Not a valid IP-Address.");
		return false;
	}
	/**
	 * The function getIP fetches the ip address of your system
	 * source: http://stackoverflow.com/questions/8083479/java-getting-my-ip-address
	 * 
	 * @return ip adress
	 */
	public static String getIP() {
		  String ip;
		    try {
		        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		        while (interfaces.hasMoreElements()) {
		            NetworkInterface iface = interfaces.nextElement();
		            // filters out 127.0.0.1 and inactive interfaces
		            if (iface.isLoopback() || !iface.isUp())
		                continue;

		            Enumeration<InetAddress> addresses = iface.getInetAddresses();
		            while(addresses.hasMoreElements()) {
		                InetAddress addr = addresses.nextElement();
		                ip = addr.getHostAddress();
		        			String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		        			if ((ip.matches(IPADDRESS_PATTERN)))
		        				return ip;
		        		
		            }
		        }
	            return "";

		    } catch (SocketException e) {
		        throw new RuntimeException(e);
		    }
		
	}
	
}
