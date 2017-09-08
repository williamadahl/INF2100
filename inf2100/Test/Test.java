import java.io.*;
import java.util.*;

public class Test{
	public static void main(String[] args)throws Exception {
		File testFile = new File("file.txt");
		Scanner scan = new Scanner(testFile);
		String line = scan.nextLine();
		checkString(line);
	}

	public static void checkString(String msg){
		String quoteText="";
		char singleOp;
		String op;
		String beforeText="";
		String afterText="";
		String doubleOp="";
		char[] bracketList = {'(', ')', '[', ']','{', '}'};
		int quoteCount = 0;

		//Basecase, if nothing is sent - exit from method
		System.out.println("Message parsed : " + msg);
		if(msg == null || msg == " " || msg.length() == 0) {
			System.out.println("We are free");
			return;
		}
		int i = 0;

		/*
		* While comes through the whole line, stops at the first symbol
		*/
		while((isDigit(msg.charAt(i))) || (isLetterAZ(msg.charAt(i)))){
			//If no weird symbol is found, the whole msg is sent to checkEnums
			if(i == msg.length()-1){
				System.out.println("Done, sending " + msg + " to checkEnums");
				//checkEnums(msg)
				return;
			}	
			i++;
		}
		/*
		* If the symbol is a space, send everything before to checkEnums
		* Then send everything after recursively to checkString for further
		* Inspection 
		*/
		if(msg.charAt(i) == ' '){
			System.out.println("Kommer inn i mellomrom");
			beforeText = msg.substring(0, i);
			System.out.println("Mellomrom= BeforeText sendes til checkEnums: " + beforeText);
			afterText = msg.substring(i+1, msg.length());
			System.out.println("Mellomrom= AfterText sendes til checkString: " + afterText);
			checkString(afterText);
			return;
		}else{	
			/*
			* If not space, we know it's a symbol
			* Need to check if the symbol is a quote, if so
			* Iterate through the rest of msg to locate second quote
			*/
			System.out.println("Moter operator: " + msg.charAt(i));
			if(msg.charAt(i) == '"'){
				for (int j = i+1; j < msg.length() ; j++) {
					/*
					* Located second quote, sending string literal to checkEnums
					* Also checking if the string literal was the last of the msg
					* If not, send the rest back to checkString
					*/
					if(msg.charAt(j) == '"'){
						quoteText = msg.substring(i, j+1);
						System.out.println("Quote= Sending quoteText to checkEnums: " + quoteText);
						//checkEnums(quoteText);
						//End of line, don't need to do anything more
						if(j+1 > msg.length()){
							System.out.println("Quote= End of line (j+1): " + j+1);
						//Sending everything after the quotations to checkString
							return;
						}else{
							afterText = msg.substring(j+1, msg.length());
							System.out.println("Operator= AfterText to checkString: " + afterText);
							checkString(afterText);
						}
					}
				}
				return;
			}
			/*
			* If the symbol is a hashtag, send everything before it to checkString
			* Ignore everything behind #
			*/
			if(msg.charAt(i) == '#'){
				System.out.println("Met hashtag or end, sending shit");
				beforeText = msg.substring(0, i);
				System.out.println("Hashtag= Sending beforeText to checkString: " + beforeText);
				checkString(beforeText);
				return;
			}
			/*
			* If operator was last on the line, send everything before to checkString
			* Send the operator to checkEnums
			*/
			int counter = i+1;
			if(counter == msg.length()){
				System.out.println("LAST ON THIS LINE");
				beforeText = msg.substring(0, i);
				System.out.println("Last= Sending beforeText to checkEnums: "+ beforeText);
				singleOp = msg.charAt(i);
				System.out.println("Last= Sending singleOp to checkEnums: "+ singleOp);
				
				//checkEnums(singleOp);
				return;
			}
			/*
			* If any of the if-s above didn't activate,
			* Loop through msg starting from the operator's position
			* In order to see if the operator is several combined.
			* However, the while-loop terminates immediately if it meets an
			* letter, digit, hashtag, or end-of-line
			*/
			while (msg.charAt(counter) != '"' &&
				(!(isLetterAZ(msg.charAt(counter)))) &&
				(!(isDigit(msg.charAt(counter)))) && (counter<msg.length()) 
				&& msg.charAt(counter) != '#') {
				/*
				* If a bracket is found, send everything before the operator (i) to
				* checkString, the operator (i) to checkEnum, and everything
				* after the operator recursively to checkString
				*/
				for(int l = 0; l < bracketList.length; l++){
					if(msg.charAt(counter) == bracketList[l]){
						singleOp = msg.charAt(counter);
						System.out.println("!!!Bracket detected!!!: " +singleOp);

						beforeText = msg.substring(0, counter);
						System.out.println("Bracket= sending beforeText to checkString " + beforeText);
						checkString(beforeText);
						//checkEnums(Character.toString(singleOp));
						System.out.println("Bracket= Sending bracket to checkEnums " + singleOp);
						afterText =  msg.substring(counter+1, msg.length());
						System.out.println("Bracket= sending afterText to checkString " + afterText);
						checkString(afterText);
						return;
					}
				}
				counter++;
			}

			/*
			* Concatinate operators if there found, else send only single
			* Send text before the operator(s) to checkEnums
			* Send text after the operator(s) to checkString
			*/
			System.out.println("========Etter while=========");
			//Concatinate operators if found, else single
			beforeText = msg.substring(0, i);
			System.out.println("Operator= BeforeText sendes til checkEnums: " + beforeText);
			op = msg.substring(i, counter);
			System.out.println("Operator= Operator sendes til checkEnums: " + op);
			afterText = msg.substring(counter, msg.length());
			System.out.println("Operator= AfterText sendes til checkString: " + afterText);
			checkString(afterText);
			return;
		}
	}
/*
	public void checkString (String msg){
		String quoteText="";
		char singleOp;
		String op;
		String beforeOpText="";
		String afterOpText="";
		String doubleOp="";
		char[] bracketList = {'(', ')', '[', ']','{', '}'};
		int quoteCount = 0;

		//Basecase, if nothing is sent - exit from method
		if(msg == null || msg == " "){
			System.out.println("We are free");
			return;
		}
		System.out.println("Message parsed : " + msg);
		//Loop through msg
		for(int i = 0; i<msg.length(); i++){
			//Checking if character is equal to ", if so
			//Loop again to find the next "
			//Now we know it's a string literal, and we can send it to checkEnums
			//We then send everything after the string recursively.
			if(msg.charAt(i) == '"'){
				for (int j = i+1; j < msg.length() ; j++) {
					if(msg.charAt(j) == '"'){
						quoteText = msg.substring(i, j+1);
						System.out.println("Sending quoteText to checkEnums: " + quoteText);
						checkEnums(quoteText);

						//End of line, don't need to do anything more
						if(j+1 > msg.length()){
							System.out.println("End of line (j+1): " + j+1);
						//Sending everything after the quotations to checkString
						}else{
							afterOpText = msg.substring(j+1, msg.length());
							System.out.println("AfterOpText to checkString: " + afterOpText);
							checkString(afterOpText);
						}
					}
				}
				return;
			//Chack if the character is an operator, or a bracket. If so, we need to handle it
			}else if((!isLetterAZ(msg.charAt(i))) && (!(isDigit(msg.charAt(i))))) {
				// If the char is a '#' we only send the text previously before the #
				// to checkString
				if(msg.charAt(i) == '#'){
					System.out.println("Met hashtag or end, sending shit");
					beforeOpText = msg.substring(0, i);
					System.out.println("Sending b4opText to checkString " + beforeOpText);
					checkString(beforeOpText);
					return;
				}
				//If the char is something else
				System.out.println("Moter operator: " + msg.charAt(i));
				//If the char is last of msg, send everything before the char
				//back recursively and and operator to checkEnums
				for(int l = 0; l < bracketList.length; l++){
					if(msg.charAt(i) == bracketList[l]){
						singleOp = msg.charAt(i);
						System.out.println("Bracket detected, sending to checkEnums, " +singleOp);
						beforeOpText = msg.substring(0, i);
						System.out.println("Sending b4text to checkString: " +beforeOpText);
						checkString(beforeOpText);
						checkEnums(Character.toString(singleOp));
						afterOpText =  msg.substring(i+1, msg.length());
						System.out.println("Sending afterText to checkString " +afterOpText);
						checkString(afterOpText);
						return;

					}
				}

				if(i == msg.length()-1){
					beforeOpText = msg.substring(0, i);
					singleOp = msg.charAt(i);
					System.out.println("Beforetext sent to checkString: " + beforeOpText);
					System.out.println("Last operator sent to checkEnums: " + singleOp);
					checkString(beforeOpText);
					//checkEnums(op);
					checkEnums(Character.toString(singleOp));
					return;
				}
				//Not end of line, need to check if more operators next to it
				else{
					int counter = i+1;
					//Checking if any additional operators is present next to the
					//Current operators
					while (msg.charAt(counter) != '"' &&
						(!(isLetterAZ(msg.charAt(counter)))) &&
						(!(isDigit(msg.charAt(counter)))) && (counter<msg.length())) {

						for(int l = 0; l < bracketList.length; l++){
							if(msg.charAt(counter) == bracketList[l]){
								singleOp = msg.charAt(counter);
								System.out.println("Bracket detected : hello: " +singleOp);

								beforeOpText = msg.substring(0, counter);
								checkString(beforeOpText);
								checkEnums(Character.toString(singleOp));
								afterOpText =  msg.substring(counter+1, msg.length());
								checkString(afterOpText);
								return;
							}
						}

						counter++;
					}

					System.out.println("Etter while: ");
					//Concatinate operators if found, else single
					op = msg.substring(i, counter);
					System.out.println("OP ER : " + op);
					//Checking if the symbol # is present inside the msg
					boolean foundHash = false;
					int k;
					//If the symbol is found, use k as a pointer
					for(k = 0; k < op.length(); k++){
						if(op.charAt(k) == '#'){
							foundHash = true;
							break;
						}
					}
					//Symbol is found, discard everything after #
					//Send everything before the symbol to checkString
					if(foundHash){
						beforeOpText = msg.substring(0,i+k);
						System.out.println("Sending this to checkString: " + beforeOpText);
						checkString(beforeOpText);
						return;
					}
					//Symbol # isn't found in the msg, sending as usual
					else{
						beforeOpText = msg.substring(0, i);
						afterOpText = msg.substring(counter, msg.length());
						System.out.println("Sending operator to checkEnums: " + op);
						System.out.println("Sending beforeOpText to checkEnums: " + beforeOpText);
						System.out.println("Sending afterOpText to checkString: " + afterOpText);
						checkEnums(beforeOpText);
						checkEnums(op);
						checkString(afterOpText);
						return;
					}

				}

			}else{
				continue;
			}
		}
		System.out.println("Status green, sending string to checkEnums: " + msg);
		checkEnums(msg);
	}
*/

	//Husk å bytt tilbake til non-static
	private static boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
	}
	private static boolean isDigit(char c) {
		return '0'<=c && c<='9';
	}
}