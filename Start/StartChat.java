package Start;

import java.io.*;

/**
 * Starten eines Chats und das Parameterhandling
 * @author Melanie Goebel
 * @version 2014-11-14
 */
public class StartChat {
	public static void main (String[] args){
		if(args.length == 1 && CheckArguments.checkIP(args[0])==true){
			InputStreamReader in = new InputStreamReader(System.in);
			BufferedReader r = new BufferedReader(in);
			String input = "";
			String[] inputSplit;
			System.out.println("Hello! \n if you need help enter --help");
			while(true){
				try {
					input = r.readLine();
				} catch (IOException e1) {
					System.out.println("Error: IO");
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
		}else{
			System.err.println("Gueltige IP-Adresse bitte mit eingeben!!!");
		}
	}
}