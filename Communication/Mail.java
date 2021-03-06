package Communication;

import java.util.Vector;

import javax.jms.*;

import Start.CheckArguments;

/**
 * Es wird eine Email Funktion fuer einen Chat implementiert
 * @author Tobi
 *
 */
public class Mail implements MessageListener{
	private String user;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	private Vector<String> emailMessage;

	public Mail(String user, Connection connection){
		this.user = user;
		this.connection = connection;
		this.emailMessage = new Vector<String>();

	}
	/**
	 * Die mailbox wird gestartet
	 */
	public void startMailbox(){
		try {
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(CheckArguments.getIP());
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			System.err.println("hehehe you did something wrong");
		}
	}
	/**
	 * Es wird eine Funktion zur verfuegung gestellt um Mails lesen zu koennen
	 */
	public void readMails() {
		try {
			consumer.setMessageListener(this);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//keine neue Nachricht
			if (emailMessage.size() == 0)
				System.out.println("No new messages.");

			//alle Nachrichten werden angezeigt
			for (int i = 0; i < emailMessage.size(); i++)
				System.out.println(emailMessage.elementAt(i));

			emailMessage.clear();
			consumer.setMessageListener(null);

		} catch (JMSException e) {
			System.err.println("Failed to check mails.");
		}
	}
	/**
	 * Eine Email wird geschrieben
	 * @param ip	Die IP-Adresse des Empfaengers
	 * @param message	Die Nachricht die gesendet wird
	 */
	public void writeMail(String ip, String message) {
		try {
			//ziel wird festgelegt
			Destination dest = session.createQueue(ip);
			//produzent wird erstellt
			MessageProducer producer = session.createProducer(dest);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//textnachricht wird erstellt
			TextMessage msg = session.createTextMessage(user + "[" + CheckArguments.getIP() + "]: " + message);
			//nachricht wird gesendet
			producer.send(msg);
			//produzent wird geschlossen
			producer.close();
			
		} catch (JMSException e) {
			System.err.println("Failed to write mail");
		}
	}

	@Override
	public void onMessage(Message msg) {
		try {
			//Textnachricht generieren
			TextMessage message = (TextMessage) msg;

			//text wird zu vector hinzugefuegt
			emailMessage.add(message.getText());

		} catch (JMSException e) {
			System.err.println("Failed to send mail");
		}
	}
}
