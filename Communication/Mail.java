package Communication;

import java.util.Vector;

import javax.jms.*;

import Start.CheckArguments;

/**
 * @author Tobi
 *
 */
public class Mail implements MessageListener{
	private String user;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	private Vector<String> m_emailMessage;

	public Mail(String user, Connection connection){
		this.user = user;
		this.connection = connection;

	}
	public void startMailbox(){
		try {
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue(CheckArguments.getIP());

			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			System.err.println("hehehe you did something wrong");
		}
	}
	public void readMails() {
		try {
			consumer.setMessageListener(this);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//keine neue Nachricht
			if (m_emailMessage.size() == 0)
				System.out.println("No new messages..");

			//alle Nachrichten werden angezeigt
			for (int i = 0; i < m_emailMessage.size(); i++)
				System.out.println(m_emailMessage.elementAt(i));

			m_emailMessage.clear();
			consumer.setMessageListener(null);

		} catch (JMSException e) {
		}
	}
	@Override
	public void onMessage(Message msg) {
		try {
			//Textnachricht generieren
			TextMessage message = (TextMessage) msg;

			//text wird zu vector hinzugefuegt
			m_emailMessage.add(message.getText());

		} catch (JMSException e) {
		}
	}
}
