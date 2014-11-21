package Communication;

import javax.jms.Connection;

public class Mail {
	private String user;
	private Connection connection;
	
	public Mail(String user, Connection connection){
		this.user = user;
		this.connection = connection;
	}
	public void startMailbox(){
		
	}
}
