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
			for(int l = 0; l < bracketList.length; l++){
				if(msg.charAt(i) == bracketList[l]){
					singleOp = msg.charAt(i);
					System.out.println("!!!Bracket detected!!!: " +singleOp);

					beforeText = msg.substring(0, i);
					System.out.println("Bracket= sending beforeText to checkString " + beforeText);
					checkString(beforeText);
					//checkEnums(Character.toString(singleOp));
					System.out.println("Bracket= Sending bracket to checkEnums " + singleOp);
					afterText =  msg.substring(i+1, msg.length());
					System.out.println("Bracket= sending afterText to checkString " + afterText);
					checkString(afterText);
					return;
				}
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
			* letter, digit, hashtag, space, or end-of-line
			*/
			while (msg.charAt(counter) != '"' &&
				(!(isLetterAZ(msg.charAt(counter)))) &&
				(!(isDigit(msg.charAt(counter)))) && (counter<msg.length()) 
				&& msg.charAt(counter) != '#' && msg.charAt(counter) != ' ') {
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

	//Husk å bytt tilbake til non-static
	private static boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
	}
	private static boolean isDigit(char c) {
		return '0'<=c && c<='9';
	}
}