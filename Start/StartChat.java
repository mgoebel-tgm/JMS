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
	private static boolean chat;

	public static void main (String[] args){
		BufferedReader r = Start();
		while(true){
			String message = readInput(r);
			if(chat == true){
				chatObj.sendTopicMessage(username + " is online!");
				ChatMenu(message);
			}else
				Menu(message);
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
				System.out.print("IP Address of ActiveMQ: ");
				ip = r.readLine();
				validIP = CheckArguments.checkIPFormat(ip);
				System.out.print("Username: ");
				username = r.readLine();
				validName = CheckArguments.checkUsername(username);
			}

		} catch (IOException e) {
			System.err.println("Something is wrong with input/output while giving username and ip-address");
		}
		connect(username,ip);
		System.out.println("Hello "+username+" your IP is "+CheckArguments.getIP()+"! \n if you need help enter help");
		String[] inputSplit;
		return r;
	}
	public static String readInput(BufferedReader r){
		String input = " ";
		String[] inputSplit;
		try {
			input = r.readLine();
			Menu(input);
		} catch (IOException e1) {
			System.out.println("Something is wrong with input/output !");
		}
		return input;
		
	}
	public static void Menu(String message){
		String[] messageSplit = message.split(" ");
		switch(message){
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
			if(messageSplit.length == 2){
				chatObj = new Chat(username, messageSplit[1], connection);
				chatObj.createTopic();
				chat = true;
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
	public static void ChatMenu(String message){
		switch(message){
		case "/exit":
			chat = false;
			chatObj.sendTopicMessage(username + " is offline!");
			chatObj.stopChat();
			break;
		case "/mail":
			System.out.println("No mail");
			//TODO Mail implementieren
			break;
		case "/mailbox":
			System.out.println("No mail");
			//TODO Mail implementieren
			break;
		default:
			chatObj.sendTopicMessage(message);
		}
	}

	public static Connection connect(String user, String ip){
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