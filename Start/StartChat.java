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
		chat = false;
		BufferedReader r = Start();
		while(true){
			String message;
			if(chat == true){
				message = readInput(r);
				ChatMenu(message);
			}else{
				message = readInput(r);
				Menu(message);
			}
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
		} catch (IOException e1) {
			System.out.println("Something is wrong with input/output !");
		}
		return input;

	}
	public static void Menu(String message){
		String[] messageSplit = message.split(" ");
		switch(messageSplit[0]){
		case "help":
			System.out.println(" _______________________________________\n\n Commands:\n"
					+ " chat <chatroom> \n"
					+ " mail <ip-address> <nachricht> \n"
					+ " mailbox \n"
					+ " exit \n _______________________________________");
			break;
		case "mail":
			if(messageSplit.length >= 3 && CheckArguments.checkIPFormat(messageSplit[1]) == true){
				String nachricht = "";
				for (int i = 2; i < messageSplit.length; i++) {
					nachricht += messageSplit[i] + " ";
				}
				mailObj.writeMail(messageSplit[1], nachricht);
			}else{
				System.err.println("Wrong IP-Adress or Message");
			}
			break;
		case "chat":
			if(messageSplit.length == 2){
				chatObj = new Chat(username, messageSplit[1], connection);
				chatObj.createTopic();
				chat = true;
				chatObj.sendTopicMessage("//"+ username + " is online!");
			}else{
				System.err.println("Topic mit eingeben! chat <topic>");
			}
			break;
		case "mailbox":
			mailObj.readMails();
			break;
		case "exit":
			System.out.println("Bye!");
			System.exit(0);
		}
	}
	public static void ChatMenu(String message){
		String[] messageSplit = message.split(" ");
		switch(messageSplit[0]){
		case "/exit":
			chat = false;
			chatObj.sendTopicMessage("//"+username + " is offline!");
			chatObj.stopChat();
			System.out.println("You leave the chat.");
			break;
		case "/mail":
			if(messageSplit.length >= 3 && CheckArguments.checkIPFormat(messageSplit[1]) == true){
				String nachricht = "";
				for (int i = 2; i < messageSplit.length; i++) {
					nachricht += messageSplit[i] + " ";
				}
				mailObj.writeMail(messageSplit[1], nachricht);
			}else{
				System.err.println("Wrong IP-Adress or Message");
			}
			break;
		case "/mailbox":
			mailObj.readMails();
			break;
		case "/help":
			System.out.println(" _________\n \n Commands: \n  /mail \n /mailbox \n /exit \n _________");
			break;
		default:
			chatObj.sendTopicMessage(message);
		}
	}

	public static Connection connect(String user, String ip){
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, ActiveMQConnection.DEFAULT_PASSWORD,"failover://tcp://" + ip + ":61616");
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