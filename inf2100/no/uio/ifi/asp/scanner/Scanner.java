package no.uio.ifi.asp.scanner;

//hhhhhhh nutt
import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private int n;
    private final int tabDist = 4;
    private Stack<Integer> theStack = new Stack<Integer>();


    public Scanner(String fileName) {
	     curFileName = fileName;
	      indents[0] = 0;  numIndents = 1;

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }


    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	}
	return false;
    }

	/***
	* Receives a string, loops over each character
	* If string is empty, return ' '
	* Returns the first non-blank
	***/
	public char findFirstNonBlank (String temp){
    n = 0;
		for(int i = 0; i<temp.length(); i++){
      if(i == temp.length()-1){
      return ' ';
      }
			else if(temp.charAt(i) != ' '){
        return temp.charAt(i);
      }
			else{
        n++;
        continue;
      }
		}
    return ' ';
}
	/***
	* Reads one line at the time, ignoring comments & empty lines
	* Adding and clearing curLineTokens for each method call
	* Pushing / popping indents and dedents
	* Creating token-objects according to enum classes
	***/
	private void readNextLine() {
		Scanner lineshaver;
		curLineTokens.clear();


	// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

	//-- Must be changed in part 1:
		String result = expandLeadingTabs(line);
  //  System.out.println("Result" + result);
    if(result == null || result.equals("")){
      return;
    }

		char hashtag = findFirstNonBlank(result);
		if (hashtag == '#'){
			return;
		}
		else if (hashtag == ' '){
			return;
		}
		else{ //Line contains tokens
			numIndents = findIndent(line);
			//numIndents = numIndents / 4;
			if (theStack.isEmpty()){
				theStack.push(numIndents);
			}
			else if (numIndents > theStack.peek()){
				theStack.push(numIndents);
				curLineTokens.add(new Token(indentToken, curLineNum()));
			}
			//Hva hvis indents er like?
			else {
				while (n < theStack.peek()){
					theStack.pop();
					curLineTokens.add(new Token(dedentToken, curLineNum()));
				}
				if (n != theStack.peek()){
					scannerError("Indeteringsfeil");
				}
			}
			checkString(line);  //INDENTS/DEDENT finished, create enums
		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));

		for (Token t: curLineTokens){
			Main.log.noteToken(t);

		}
	}

	public void checkString (String msg){
		String regex = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
		msg = msg.replaceAll(regex, "");
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
			//System.out.println("We are free");
			return;
		}
		for(int i = 0; i<msg.length(); i++){
			if(msg.charAt(i) == '"'){
				quoteCount++;
			}
		}
		if(quoteCount % 2 != 0){
			System.out.println("String literal not terminated");
			System.exit(1);
		}

		//System.out.println("Message parsed : " + msg);
		//Loop through msg
		for(int i = 0; i<msg.length(); i++){
			//Checking if character is equal to ", if so
			//Loop again to find the next "
			//Now we know it's a string literal, and we can send it to checkEnum
			//We then send everything after the string recursively.
			if(msg.charAt(i) == '"'){
				for (int j = i+1; j < msg.length() ; j++) {
					if(msg.charAt(j) == '"'){
						quoteText = msg.substring(i, j+1);
						//System.out.println("Sending quoteText: " + quoteText);
						//checkEnum(quoteText);

						//End of line, don't need to do anything more
						if(j+1 > msg.length()){
							//System.out.println("End of line (j+1): " + j+1);
						//Sending everything after the quotations to checkString
						}else{
							afterOpText = msg.substring(j+1, msg.length());
							//System.out.println("AfterOpText: " + afterOpText);
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
					//System.out.println("Met hashtag or end, sending shit");
					beforeOpText = msg.substring(0, i);
					checkString(beforeOpText);
					return;
				}
				//If the char is something else
				//System.out.println("Moter operator: " + msg.charAt(i));
				//If the char is last of msg, send everything before the char
				//back recursively and and operator to checkEnum
				for(int l = 0; l < bracketList.length; l++){
					if(msg.charAt(i) == bracketList[l]){
						singleOp = msg.charAt(i);
						//System.out.println("Bracket detected : Try send: " +singleOp);
						//checkEnum(singleOp);
						beforeOpText = msg.substring(0, i);
						checkString(beforeOpText);
						afterOpText =  msg.substring(i+1, msg.length());
						checkString(afterOpText);
						return;

					}
				}

				if(i == msg.length()-1){
					beforeOpText = msg.substring(0, i);
					singleOp = msg.charAt(i);
					//System.out.println("Before: " + beforeOpText);
					//System.out.println("Last operator: " + singleOp);
					checkString(beforeOpText);
					//checkEnum(op)
					return;
				}
				/* Undo if problem surfaces up
				if(i+1 == msg.length()){
					System.out.println("End of line: (i+1): " + (i+1));
					System.out.println("Sending Op to checkEnum: " + msg.charAt(i));
					return;
				}
				*/
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
							//System.out.println("Bracket detected : hello: " +singleOp);
							//checkEnum(singleOp);
								beforeOpText = msg.substring(0, counter);
								checkString(beforeOpText);
								afterOpText =  msg.substring(counter, msg.length());
								checkString(afterOpText);
								return;
							}
						}

						counter++;
					}

					//System.out.println("Etter while: ");
					//Concatinate operators if found, else single
					op = msg.substring(i, counter);
					//System.out.println("OP ER : " + op);
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
						//System.out.println("Sending this : " + beforeOpText);
						checkString(beforeOpText);
						return;
					}
					//Symbol # isn't found in the msg, sending as usual
					else{
						beforeOpText = msg.substring(0, i);
						afterOpText = msg.substring(counter, msg.length());
					//System.out.println("Sending beforeOpText: " + beforeOpText);
					//System.out.println("Sending operator: " + op);
					//System.out.println("Sending afterOpText: " + afterOpText);
					//checkEnum(singleOp);
					//checkEnum(beforeOpText);
						checkString(afterOpText);
						return;
					}

				}
				/*
				if ((msg.charAt(i+1) == '"') ||
					((isDigit(msg.charAt(i+1))) ||
						(isLetterAZ(msg.charAt(i+1))))){

					singleOp = msg.charAt(i);
					beforeOpText = msg.substring(0, i);
					afterOpText = msg.substring(i+1, msg.length());
					System.out.println("Sending singleOp: " + singleOp);
					System.out.println("Sending beforeOpText: " + beforeOpText);
					System.out.println("Sending afterOpText: " + afterOpText);
					//checkEnum(singleOp);
					//checkEnum(beforeOpText);
					checkString(afterOpText);
					return;
				}else{
						beforeOpText = msg.substring(0, i);
						doubleOp = msg.substring(i, i+2);
						afterOpText = msg.substring(i+2, msg.length());
						System.out.println("Sending doubleOp: " + doubleOp);
						System.out.println("Sending beforeOpText: " + beforeOpText);
						System.out.println("Sending afterOpText: " + afterOpText);
						//checkEnum(doubleOp);
						//checkEnum(beforeOptext);
						checkString(afterOpText);
						return;
				}
				*/
			}else{
				continue;
			}
		}
		//System.out.println("Status green, sending string to checkEnum: " + msg);
		//checkEnum(msg);
	}




	public void checkEnums(String msg){
			//TODO: Need to use .toString();

			//Message is null, exiting..
		if(msg == null){
			System.out.println("msg is NULL");
			return;
		}
			//If the message starts with a ", create a string litteral token
		if(msg.charAt(0) == '"'){
				//String token
      curLineTokens.add(new Token(indentToken,curLineNum()));
			TokenKind kind = TokenKind.stringToken;
			Token tok = new Token(kind, curLineNum());
      tok.stringLit = msg;
			curLineTokens.add(tok);
		}
			//If the first symbol is a char, make it a nameToken
		else if(isLetterAZ(msg.charAt(0))){
				//Name and keyword tokens
				//Find out if it's a keyword token
			TokenKind temp = checkToken(msg);

			if(temp != null){
				Token tok = new Token(temp, curLineNum());
				curLineTokens.add(tok);

			}else{
				System.out.println("KeywordToken not found");
			}
				//Else, create a name token
			TokenKind kind = TokenKind.nameToken;
			Token tok = new Token(kind, curLineNum());
      tok.name = msg;
      Main.log.noteToken(tok);
			curLineTokens.add(tok);
		}
			//If the symbol is digit, create int / float token
		else if(isDigit(msg.charAt(0))){
				// Integer and float tokens
				// Checks if the token is a float
			for(int k = 0; k < msg.length(); k++){
				if(msg.charAt(k) == '.'){
					TokenKind kind = TokenKind.floatToken;
					Token tok = new Token(kind, curLineNum());
          tok.floatLit = Float.parseFloat(msg);
					curLineTokens.add(tok);
					return;
				}
			}
				//If for doesn't hit, it's an integer token
			TokenKind kind = TokenKind.integerToken;
			Token tok = new Token(kind, curLineNum());
      tok.integerLit = Integer.parseInt(msg);
			curLineTokens.add(tok);
		}
			//It's otherwise an operator token, need to iterate through the list of
			//known operators and assign new Token on that kind
		else{
				//Operator
			TokenKind temp = checkToken(msg);
			if(temp != null){
				Token tok = new Token(temp, curLineNum());
				curLineTokens.add(tok);
			}else{
				System.out.println("OperatorToken not found");
			}
		}
	}



	private TokenKind checkToken(String msg){
		for(TokenKind t : TokenKind.values()){
			if(t.toString().equals(msg)){
				return t;
			}
		}
		System.out.println("Tokenkind not found");
		return null;
	}

    public int curLineNum() {
	     return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }

    private String expandLeadingTabs(String s) {
      if(s == null){
        return "";
      }
	      String newS = "";
	       for (int i = 0;  i < s.length();  i++) {
	          char c = s.charAt(i);
	           if (c == '\t') {
		             do {
		                 newS += " ";
		                } while (newS.length()%tabDist != 0);
	            } else if (c == ' ') {
		            newS += " ";
	           } else {
		          newS += s.substring(i);
		     break;
	    }
	}
	 return newS;
  }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }
}
