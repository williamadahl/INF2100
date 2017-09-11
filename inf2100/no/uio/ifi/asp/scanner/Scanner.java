package no.uio.ifi.asp.scanner;


//hhhhhhh nutt
import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private int n;
    private final int tabDist = 4;
    private Stack<Integer> theStack = new Stack<Integer>();
    private int numQuotes = 0;
    private int numSingleQuotes = 0;



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
        TokenKind kind = TokenKind.eofToken;
        Token tok = new Token(kind);
        curLineTokens.add(tok);
        Main.log.noteToken(tok);

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
      preScan(line, '"');
			checkString(line);  //INDENTS/DEDENT finished, create enums
		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));

		for (Token t: curLineTokens){
			Main.log.noteToken(t);

		}

	}
//:TODO sjekke for partal antall quoteationmarks
//:TODO fikse dedents token


  public void checkString(String msg){
    String quoteText="";
    char singleOp;
    String op;
    String beforeText="";
    String afterText="";
    String doubleOp="";
    String floatNumber="";
    char[] bracketList = {'(', ')', '[', ']','{', '}'};

    int i = 0;



    //Basecase, if nothing is sent - exit from method
    System.out.println("Message parsed : " + msg);
    if(msg == null || msg == " " || msg.length() == 0) {
      System.out.println("We are free");
      return;
    }

    /*
    * While comes through the whole line, stops at the first symbol
    */
    while((isDigit(msg.charAt(i))) || (isLetterAZ(msg.charAt(i)))){
      //If no weird symbol is found, the whole msg is sent to checkEnums
      if(i == msg.length()-1){
        System.out.println("Done, sending " + msg + " to checkEnums");
        checkEnums(msg);
        return;
      }
      i++;
    }

    if(msg.charAt(i) == '.'){
      int nextSymbol = i+1;
      if(nextSymbol == msg.length()-1){
      //  System.out.println("Naa er det slutt");
        beforeText = msg.substring(0, nextSymbol+1);
      //  singleOp = msg.charAt(nextSymbol);
      //  System.out.println("HER?" + singleOp);
        checkEnums(beforeText);
      //  checkEnums(Character.toString(singleOp));
        return;
      }
      else if(isLetterAZ(msg.charAt(nextSymbol))){
        beforeText = msg.substring(0, nextSymbol);
        singleOp = msg.charAt(nextSymbol);
        afterText = msg.substring(nextSymbol + 1, msg.length());
        System.out.println("REEEEEEEEEEEEEEEEEEEE " + afterText);


        checkEnums(beforeText);
        checkEnums(Character.toString(singleOp));
        checkString(afterText);
        return;
      }else{
        System.out.println("Naa er det slutt");
          //Presumably integers after the .
          while(isDigit(msg.charAt(nextSymbol))){
            if(nextSymbol == msg.length() -1){
              System.out.println("Naa er det slutt");
              floatNumber = msg.substring(0, nextSymbol+1);
              checkEnums(floatNumber);
              return;
            }
            nextSymbol ++;
          }
          floatNumber = msg.substring(0, nextSymbol);
          afterText = msg.substring(nextSymbol, msg.length());
          checkEnums(floatNumber);
          System.out.println("REEEEEEEEEEEEEEEEEEEE " + afterText);
          checkString(afterText);
          return;
      }
    }

    /*
    * If the symbol is a space, send everything before to checkEnums
    * Then send everything after recursively to checkString for further
    * Inspection
    */
    if(msg.charAt(i) == ' '){
      System.out.println("Kommer inn i mellomrom");
      beforeText = msg.substring(0, i);
      checkEnums(beforeText);
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

            checkEnums(quoteText);
            //End of line, don't need to do anything more
            if(j+1 > msg.length()){
              System.out.println("Quote= End of line (j+1): " + j+1);

            //Sending everything after the quotations to checkString
              return;
            }else{
              afterText = msg.substring(j+1, msg.length());
              System.out.println("Operator= AfterText to checkString: " + afterText);
              checkString(afterText);
              return;
            }
          }
        }
        return;
      }
      /*
      * If not space, we know it's a symbol
      * Need to check if the symbol is a singlequote too, if so
      * Iterate through the rest of msg to locate second singlequote
      */
      System.out.println("Moter operator: " + msg.charAt(i));
      if(msg.charAt(i) == '\''){

        for (int j = i+1; j < msg.length() ; j++) {
          /*
          * Located second quote, sending string literal to checkEnums
          * Also checking if the string literal was the last of the msg
          * If not, send the rest back to checkString
          */
          if(msg.charAt(j) == '\''){

            quoteText = msg.substring(i, j+1);
            System.out.println("Quote= Sending quoteText to checkEnums: " + quoteText);

            checkEnums(quoteText);
            //End of line, don't need to do anything more
            if(j+1 > msg.length()){
              System.out.println("Quote= End of line (j+1): " + j+1);

            //Sending everything after the quotations to checkString
              return;
            }else{
              afterText = msg.substring(j+1, msg.length());
              System.out.println("Operator= AfterText to checkString: " + afterText);
              checkString(afterText);
              return;
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
          checkEnums(Character.toString(singleOp));
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
        checkEnums(beforeText);
        singleOp = msg.charAt(i);
        System.out.println("Last= Sending singleOp to checkEnums: "+ singleOp);

        checkEnums(Character.toString(singleOp));
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
            checkEnums(Character.toString(singleOp));
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
      checkEnums(beforeText);
      op = msg.substring(i, counter);
      System.out.println("Operator= Operator sendes til checkEnums: " + op);
      checkEnums(op);
      afterText = msg.substring(counter, msg.length());
      System.out.println("Operator= AfterText sendes til checkString: " + afterText);
      checkString(afterText);
      return;
    }
  }




	public void checkEnums(String msg){
    System.out.println("dumb dumb " + msg);

			//Message is null, exiting..
		if(msg == null){
			System.out.println("msg is NULL");
			return;
		}
    if(msg.length() == 0){
      System.out.println(ANSI_RED +"Dumb fucc" + ANSI_RESET);
      return;
    }
			//If the message starts with a ", create a string litteral token
		if(msg.charAt(0) == '"'){
				//String token
			TokenKind kind = TokenKind.stringToken;
			Token tok = new Token(kind, curLineNum());
      tok.stringLit = msg;
      System.out.println( ANSI_PURPLE +"MSGMSGMSGMSGMSGMSGMSGMSG: " + tok.showInfo() + ANSI_RESET);
			curLineTokens.add(tok);
      System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
      // TokenKind kind1 = TokenKind.eofToken;
      // Token tok1 = new Token(kind1, curLineNum());
      // curLineTokens.add(tok1);

    }
    if(msg.charAt(0) == '\''){
        //String token
      msg = msg.replace('\'', '"');
      System.out.println("FNUT MESSAGE ER " + msg);
      TokenKind kind = TokenKind.stringToken;
      Token tok = new Token(kind, curLineNum());
      tok.stringLit = msg;
      System.out.println( ANSI_PURPLE +"MSGMSGMSGMSGMSGMSGMSGMSG: " + tok.showInfo() + ANSI_RESET);
      curLineTokens.add(tok);
      System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
      // TokenKind kind1 = TokenKind.eofToken;
      // Token tok1 = new Token(kind1, curLineNum());
      // curLineTokens.add(tok1);

    }
			//If the first symbol is a char, make it a nameToken
		 else if(isLetterAZ(msg.charAt(0))){
				//Name and keyword tokens
				//Find out if it's a keyword token
    //for(TokenKind t : TokenKind.values()){
        //if(msg.contains(t.toString())){

          TokenKind temp = checkToken(msg);



			if(temp != null){
				Token tok = new Token(temp, curLineNum());
				curLineTokens.add(tok);
        System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
        return;
			}else{
				System.out.println("KeywordToken not found: " + msg);

			}
				//Else, create a name token

			TokenKind kind = TokenKind.nameToken;
			Token tok = new Token(kind, curLineNum());
      tok.name = msg;

			curLineTokens.add(tok);
      System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
		}
			//If the symbol is digit, create int / float token
		else if(isDigit(msg.charAt(0))){
				// Integer and float tokens
				// Checks if the token is a float
			for(int k = 0; k < msg.length(); k++){
				if(msg.charAt(k) == '.'){
					TokenKind kind = TokenKind.floatToken;
					Token tok = new Token(kind, curLineNum());
          tok.floatLit = Double.parseDouble(msg);
					curLineTokens.add(tok);
          System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
					return;
				}
			}
				//If for doesn't hit, it's an integer token

			TokenKind kind = TokenKind.integerToken;
			Token tok = new Token(kind, curLineNum());
      tok.integerLit = Integer.parseInt(msg);
			curLineTokens.add(tok);
      System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
		}
			//It's otherwise an operator token, need to iterate through the list of
			//known operators and assign new Token on that kind
		else{
				//Operator
			TokenKind temp = checkToken(msg);
			if(temp != null){

				Token tok = new Token(temp, curLineNum());
				curLineTokens.add(tok);
        System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
			}else{
				System.out.println("OperatorToken not found");
			}
		}
	}

  private void preScan(String line, char c){
    for(int i = 0; i<line.length(); i++){
      if(line.charAt(i) == c){
        if(c == '\''){
        //  numSingleQuotes++;
        }else{
          numQuotes++;
        }
      }
    }
    if((numQuotes%2) != 0){
      scannerError("String literal not terminated!");
      System.exit(1);
    }else{
      System.out.println("Quotes looks alrighty");
    }
  }


	private TokenKind checkToken(String msg){
		for(TokenKind t : TokenKind.values()){
			if(t.toString().equals(msg)){
				return t;
			}
		}
		System.out.println("Tokenkind not found: " + msg);
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
