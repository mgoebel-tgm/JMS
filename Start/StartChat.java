package Start;

import java.io.*;

import org.apache.activemq.*;

import javax.jms.*;

import Communication.*;
import Communication.Chat;

/**
 * Starten eines Chats und das Parameterhandling
 * @author Melanie Goebel, Tobias Perny
 * @version 2014-11-14
 */
public class StartChat {
	private static String ip;
	private static String username;
	private static Connection connection;
	private static Chat chatObj;
	private static Mail mailObj;

	public static void main (String[] args){
		BufferedReader r = Start();
		while(true){
			Menu(r);
		}
	}
	
	public static BufferedReader Start(){
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader r = new BufferedReader(in);
		String input = "";

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
		connect(username,ip);
		System.out.println("Hello "+username+"! \n if you need help enter help");
		String[] inputSplit;
		return r;
	}
	
	public static void Menu(BufferedReader r){
		String input = " ";
		String[] inputSplit;
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
			if(inputSplit.length == 2){
				chatObj = new Chat(username, inputSplit[1], connection);
				chatObj.createTopic();
			}else{
				System.err.println("Topic mit eingeben! chat <topic>");
			}
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

	public static Connection connect(String user, String ip){
		System.out.println(ip);
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,"failover://tcp://" + ip + ":61616");
		connection = null;

		try {
			//create Connection
			connection = connectionFactory.createConnection();
			connection.start();
			mailObj = new Mail(username, connection);
			mailObj.startMailbox();
		} catch (JMSException e) {
			System.err.println("Failed to connect");
		}
		return connection;
	}

}