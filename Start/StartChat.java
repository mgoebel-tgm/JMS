package Start;

import java.io.*;

import org.apache.activemq.*;

import javax.jms.*;

/**
 * Starten eines Chats und das Parameterhandling
 * @author Melanie Goebel
 * @version 2014-11-14
 */
public class StartChat {
	private static String ip;
	private static String username;
	
	public static void main (String[] args){
		
			InputStreamReader in = new InputStreamReader(System.in);
			BufferedReader r = new BufferedReader(in);
			String input = "";
<<<<<<< HEAD
			try {
				boolean validIP = false;
				boolean validName = false;
				while(validIP == false || validName == false){
					System.out.print("IP Address: ");
					ip = r.readLine();
					validIP = CheckArguments.checkIP(ip);
					System.out.print("Username: ");
					username = r.readLine();
					validName = CheckArguments.checkUsername(username);
				}
				
			} catch (IOException e) {
				System.err.println("Something is wrong with input/output while giving username and ip-address");
			}
			connect(ip,username);
			System.out.println("Hello "+username+"! \n if you need help enter help");
=======
			String[] inputSplit;
			System.out.println("Hello! \n if you need help enter --help");
>>>>>>> 2af12cd85e68779a7544bb7a8f149fc281432531
			while(true){
				try {
					input = r.readLine();
				} catch (IOException e1) {
					System.out.println("Something is wrong with input/output !");
				}
				inputSplit = input.split(" ");
				switch(inputSplit[0]){
				case "help":
					System.out.println("chat <ip-address> <username> <chatroom> \n"
							+ "mail <ip-address> <nachricht> \n"
							+ "mailbox"
							+ "exit");
					break;

				case "mail":
					System.out.println("No email");
					//TODO: Tobi Mail implementieren
					break;
				case "chat":
					System.out.println("No chat..");
					//TODO: Melly Chat implementieren
					break;
				case "mailbox":
					System.out.println("No mailbox");
					//TODO:Tobi Mailbox implementieren
					break;
				case "exit":
					System.out.println("Bye!");
					System.exit(0);
				}
			}
	}
	public static Connection connect(String user, String ip){
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, ActiveMQConnection.DEFAULT_PASSWORD,"failover://tcp://" + ip + ":61616");
		Connection connection = null;
		//create Connection
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			System.err.println("Failed to connect");
		}
		return connection;
	}
	
}