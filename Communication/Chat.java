package Communication;

import javax.jms.*;

import Start.CheckArguments;
/**
 * Kommunikation mit einen Chat zur Verfuegung stellen
 * @author Melanie Goebel
 * @version 2014-11-21
 */
public class Chat implements MessageListener {
	private String username;
	private String subject;
	private Connection connection;
	private String ip;

	private Session session = null;
	private MessageConsumer consumer = null;
	MessageProducer producer = null;
	Destination destination = null;

	/**
	 * 
	 * @param username den eigenen Username
	 * @param subject das Subject
	 * @param connection die Connection
	 */
	public Chat(String username, String subject, Connection connection){
		this.username = username;
		this.subject = subject;
		this.connection = connection;
		this.ip = CheckArguments.getIP();
	}
	/**
	 * Erstellen einer Topic
	 */
	public void createTopic(){
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic(subject);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
		} catch (JMSException e) {
			System.err.println("Failed to create a Topic!");
		}
	}

	@Override
	public void onMessage(Message message) {
		TextMessage m = (TextMessage) message; // neue Nachricht erstellen
		if (message != null) {
			try {
				System.out.println(m.getText());
			} catch (JMSException e) {
				System.err.println("Something went wrong with the message.");
			}
		}
	}

	/**
	 * Sendet eine Nachricht an den Topic
	 * @param m die Nachricht
	 */
	public void sendTopicMessage(String m) {
		try {
			TextMessage message = null;
			if(m.matches("^//[a-z A-Z 0-9_-]{3,15} (is online!|is offline!)$")){
				message = session.createTextMessage(m);
			}else{
				message = session.createTextMessage(username + " [ "
						+ ip + " ]: " + m);// Format der Nachricht erstellen
			}
			producer.send(message); // Nachricht senden

		} catch (Exception e) {
			System.err.println("Failed to send message!");
		}
	}
	/**
	 * Stoppt alles fuer den Chat
	 */
	public void stopChat() {
		try {
			connection.stop();
			session.close();
			connection.close();
		} catch (Exception e) {
			System.err.println("Failed to stop Chat!");
		}
	}
}
