package no.uio.ifi.asp.scanner;
/***
 .--' |
/___^ |     .--.
   ) |    /    \
  /  |  /`      '.
 |   '-'    /     \
 \         |      |\
  \    /   \      /\|
   \  /'----`\   /
   |||       \\ |
   ((|        ((|
   |||        |||
  //_(       //_(


@author lucasp - Lucas Paruch
@author williada - William Arild Dahl
@version 11. Sep 2017

***/
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
  * Else, return the first non-blank symbol
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
        while(!theStack.empty()){

          if(theStack.pop() > 0){
            TokenKind dedent = TokenKind.dedentToken;
            Token dedentTok = new Token(dedent);
            curLineTokens.add(dedentTok);
            Main.log.noteToken(dedentTok);
          }
        }

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
    /*
    * Hashtag gets the first symbol on a line
    * Check if whole line is comment or consist of blanks
    */
    char hashtag = findFirstNonBlank(result);
    if (hashtag == '#'){
      return;
    }
    else if (hashtag == ' '){
      return;
    }
    //Line contains tokens, continuing
    else{
      numIndents = findIndent(line);
      if (theStack.isEmpty()){
        theStack.push(numIndents);
      }
      else if (numIndents > theStack.peek()){
        theStack.push(numIndents);
        curLineTokens.add(new Token(indentToken, curLineNum()));
      }
      else {
        if(n < theStack.peek()){
          while (n < theStack.peek()){
            theStack.pop();
            curLineTokens.add(new Token(dedentToken, curLineNum()));
          }
        }
        if (n != theStack.peek()){
          scannerError("Indeteringsfeil");
        }
      }
      preScan(line, '"');
      addDedent();
      checkString(line);  //INDENTS/DEDENT finished, creating enums
    }
    // Terminate line:
    curLineTokens.add(new Token(newLineToken,curLineNum()));
    for (Token t: curLineTokens){
      Main.log.noteToken(t);
    }
  }

  /*
  * Recursive method, splitting line into correct pieces
  * Recursively splitting until no more pieces are found
  * Creatin tokens by sending to checkEnums method
  */
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
    //System.out.println("Message parsed : " + msg);
    if(msg == null || msg == " " || msg.length() == 0) {
    //  System.out.println("We are free");
      return;
    }

    /*
    * While comes through the whole line, stops at the first symbol
    */
    while((isDigit(msg.charAt(i))) || (isLetterAZ(msg.charAt(i)))){
      //If no weird symbol is found, the whole msg is sent to checkEnums
      if(i == msg.length()-1){
    //    System.out.println("Done, sending " + msg + " to checkEnums");
        checkEnums(msg);
        return;
      }
      i++;
    }
    //If the symbol . we need to check whether the symbol after it
    //Is an integer or an letter
    if(msg.charAt(i) == '.'){
      int nextSymbol = i+1;
      if(nextSymbol == msg.length()-1){
        beforeText = msg.substring(0, nextSymbol+1);
        checkEnums(beforeText);
        return;
      }
      //If the next symbol is an letter, send everything before the .
      //To checkEnums, and the operator to checkEnums.
      //The rest of the line will recursively be sent back to checkString
      else if(isLetterAZ(msg.charAt(nextSymbol))){
        beforeText = msg.substring(0, nextSymbol);
        singleOp = msg.charAt(nextSymbol);
        afterText = msg.substring(nextSymbol + 1, msg.length());

        checkEnums(beforeText);
        checkEnums(Character.toString(singleOp));
        checkString(afterText);
        return;
      }else{
        //the symbol after the . is/are integers,
        //Concatinate the "float" number with the help of a while loops
        //And a pointer
        while(isDigit(msg.charAt(nextSymbol))){
          if(nextSymbol == msg.length() -1){
            floatNumber = msg.substring(0, nextSymbol+1);
            checkEnums(floatNumber);
            return;
          }
          nextSymbol ++;
        }
        floatNumber = msg.substring(0, nextSymbol);
        afterText = msg.substring(nextSymbol, msg.length());
        checkEnums(floatNumber);
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
      beforeText = msg.substring(0, i);
      checkEnums(beforeText);
      afterText = msg.substring(i+1, msg.length());
      checkString(afterText);
      return;
    }else{
      /*
      * If not space, we know it's a symbol
      * Need to check if the symbol is a quote, if so
      * Iterate through the rest of msg to locate second quote
      */
      if(msg.charAt(i) == '"'){
        for (int j = i+1; j < msg.length() ; j++) {
          /*
          * Located second quote, sending string literal to checkEnums
          * Also checking if the string literal was the last of the msg
          * If not, send the rest back to checkString
          */
          if(msg.charAt(j) == '"'){
            quoteText = msg.substring(i, j+1);
          //  System.out.println("Sending quoteText to checkEnums: " + quoteText);
            checkEnums(quoteText);
            //End of line, don't need to do anything more
            if(j+1 > msg.length()){
              //Sending everything after the quotations to checkString
              return;
            }else{
              afterText = msg.substring(j+1, msg.length());
          //    System.out.println("AfterText to checkString: " + afterText);
              checkString(afterText);
              return;
            }
          }
        }
        return;
      }
      /*
      * If the symbol is an apostrophe,
      * we will need to handle it like it's an quotation mark
      * (Unless it's inside two quotation marks)
      */
      if(msg.charAt(i) == '\''){
        for (int j = i+1; j < msg.length() ; j++) {
          /*
          * Located second apostrophe, sending it to checkEnums
          * Also checking if it was the last of the msg
          * If not, send the rest back to checkString
          */
          if(msg.charAt(j) == '\''){
            quoteText = msg.substring(i, j+1);
            //System.out.println("Sending apostrophe string to checkEnums: " + quoteText);

            checkEnums(quoteText);
            //End of line, don't need to do anything more
            if(j+1 > msg.length()){
              //Sending everything after the apostrphes to checkString
              return;
            }else{
              afterText = msg.substring(j+1, msg.length());
            //  System.out.println("AfterText to checkString: " + afterText);
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
      //  System.out.println("Met hashtag or end, sending shit");
        beforeText = msg.substring(0, i);
      //  System.out.println("Hashtag= Sending beforeText to checkString: " + beforeText);
        checkString(beforeText);
        return;
      }
      /*
      * Go through the bracketsArray to check if our current symbol is an bracket
      * If it's a bracket, send everything before it to checkString
      * Send the bracket to checkEnums
      * Send everything after the bracket to checkString
      */
      for(int l = 0; l < bracketList.length; l++){
        if(msg.charAt(i) == bracketList[l]){
          singleOp = msg.charAt(i);
          beforeText = msg.substring(0, i);
      //    System.out.println("Sending beforeText to checkString " + beforeText);
          checkString(beforeText);
          checkEnums(Character.toString(singleOp));
        //  System.out.println("Sending bracket to checkEnums " + singleOp);
          afterText =  msg.substring(i+1, msg.length());
        //  System.out.println("Sending afterText to checkString " + afterText);
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
        beforeText = msg.substring(0, i);
      //  System.out.println("Sending beforeText to checkEnums: "+ beforeText);
        checkEnums(beforeText);
        singleOp = msg.charAt(i);
    //    System.out.println("Sending singleOp to checkEnums: "+ singleOp);

        checkEnums(Character.toString(singleOp));
        return;
      }
      /*
      * If any of the if-s above didn't activate,
      * Loop through msg starting from the operator's position
      * In order to see if the operator is several combined.
      * However, the while-loop terminates immediately if it meets an
      * letter, digit, hashtag, space,single apotrophe, or end-of-line
      */
      while (msg.charAt(counter) != '"' &&
      (!(isLetterAZ(msg.charAt(counter)))) &&
      (!(isDigit(msg.charAt(counter)))) && (counter<msg.length())
      && msg.charAt(counter) != '#' && msg.charAt(counter) != ' '
      && msg.charAt(counter) != '\'' ) {
        /*
        * If a bracket is found, send everything before the operator (i) to
        * checkString, the operator (i) to checkEnum, and everything
        * after the operator recursively to checkString
        */
        for(int l = 0; l < bracketList.length; l++){
          if(msg.charAt(counter) == bracketList[l]){
            singleOp = msg.charAt(counter);

            beforeText = msg.substring(0, counter);
          //  System.out.println("Sending beforeText to checkString " + beforeText);
            checkString(beforeText);
            checkEnums(Character.toString(singleOp));
        //    System.out.println("Sending bracket to checkEnums " + singleOp);
            afterText =  msg.substring(counter+1, msg.length());
          //  System.out.println("Sending afterText to checkString " + afterText);
            checkString(afterText);
            return;
          }
        }
        counter++;
      }

      /*
      * Concatinate operators if found, else send only single
      * Send text before the operator(s) to checkEnums
      * Send text after the operator(s) to checkString
      */
      beforeText = msg.substring(0, i);
    //  System.out.println("BeforeText sendes til checkEnums: " + beforeText);
      checkEnums(beforeText);
      op = msg.substring(i, counter);
    //  System.out.println("Sending this to OPCheck" + op);
    //  System.out.println("Operator sendes til checkEnums: " + op);
      checkEnums(op);
      afterText = msg.substring(counter, msg.length());
      //System.out.println("AfterText sendes til checkString: " + afterText);
      checkString(afterText);
      return;
    }
  }

  /*
  * If several operators are written after each other, check if part of
  * the operator exists within the Enum classes
  * If exist, send it to checkEnum and the remainmant to checkEnum
  */
  public void operatorCheck(String msg) {
    for(TokenKind t : TokenKind.values()){
      if(msg.contains(t.toString())){
        if(t.toString().charAt(0) == msg.charAt(0)){
          checkEnums(t.toString());
          String [] parts = msg.split("\\"+t.toString());
          checkEnums(parts[1]);
          return;
        }else{
          int firstOccu = msg.indexOf(t.toString().charAt(0));
          String start = msg.substring(0, firstOccu);
          checkEnums(start);
          checkEnums(t.toString());
          return;
        }
      }

    }
  //  System.out.println("Error, something shouldn't hit here");
    return;
  }

  /*
  * Our create-token method, receives a string and checks it towards
  * various tokenkinds
  */
  public void checkEnums(String msg){
    //Message is null, exiting..
    if(msg == null){
  //    System.out.println("msg is NULL");
      return;
    }
    if(msg.length() == 0){
      return;
    }
    //If the message starts with a ", create a string litteral token
    if(msg.charAt(0) == '"'){
      //String token
      TokenKind kind = TokenKind.stringToken;
      Token tok = new Token(kind, curLineNum());
      tok.stringLit = msg;
      curLineTokens.add(tok);
  //    System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);

      return;
    }
    //If the message starts with an apostrophe, treat it as a string litteral token
    if(msg.charAt(0) == '\''){
      //String token
      msg = msg.replace('\'', '"');
      TokenKind kind = TokenKind.stringToken;
      Token tok = new Token(kind, curLineNum());
      tok.stringLit = msg;
      curLineTokens.add(tok);
  //    System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);

    }
    //If the first symbol is a char, make it a nameToken
    else if(isLetterAZ(msg.charAt(0))){
      TokenKind temp = checkToken(msg);
      if(temp != null){
        Token tok = new Token(temp, curLineNum());
        curLineTokens.add(tok);
    //    System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
        return;
      }else{
    //    System.out.println("KeywordToken not found: " + msg);

      }
      //Else, create a name token
      TokenKind kind = TokenKind.nameToken;
      Token tok = new Token(kind, curLineNum());
      tok.name = msg;

      curLineTokens.add(tok);
  //    System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
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
      //    System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
          return;
        }
      }
      //If for doesn't hit, it's an integer token
      TokenKind kind = TokenKind.integerToken;
      Token tok = new Token(kind, curLineNum());
      tok.integerLit = Integer.parseInt(msg);
      curLineTokens.add(tok);
      //System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
    }
    //It's otherwise an operator token, need to iterate through the list of
    //known operators and assign new Token on that kind
    else{
      //Operator
      TokenKind temp = checkToken(msg);
      if(temp != null){
        Token tok = new Token(temp, curLineNum());
        curLineTokens.add(tok);
  //      System.out.println(ANSI_GREEN +"created and added: " + tok.toString() +ANSI_RESET);
      }else{
    //    System.out.println(ANSI_RED + "OperatorToken not found"+ ANSI_RESET);
        operatorCheck(msg);
      }
    }
  }

  /*
  * Prescanning for the correct amount of quotation marks
  * per line, before the line is sent to checkString
  */
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
  //    System.out.println("Quotes looks alrighty");
    }
  }

  /*
  * Looping through all Enums, looking for
  * token to match the string msg
  */
  private TokenKind checkToken(String msg){
    for(TokenKind t : TokenKind.values()){
      if(t.toString().equals(msg)){
        return t;
      }
    }
  //  System.out.println("Tokenkind not found: " + msg);
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

  /*
  * Adding dedent according to Dag's algorithm
  */
  private void addDedent(){
    while(n < theStack.peek()){
      theStack.pop();
      System.out.println("dedent i dag sin algo");
      curLineTokens.add(new Token(dedentToken, curLineNum()));
    }
    if(n != theStack.peek()){
  //    System.out.println(ANSI_RED +"Indent error " + ANSI_RESET);
    }
    return;
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
