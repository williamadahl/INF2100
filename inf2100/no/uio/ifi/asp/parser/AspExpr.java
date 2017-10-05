package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


public class AspExpr extends AspSyntax {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	     super(n);
    }

    public static AspExpr parse(Scanner s) {
      AspExpr ae = new AspExpr(s.curLineNum());
    //  System.out.println("DETTE HER ER I EXPR: " + s.curToken().kind.toString());

	     Main.log.enterParser("expr");

       while(true){
         ae.andTests.add(AspAndTest.parse(s));
         //System.out.println(ANSI_YELLOW + "I AND TEST (WHILE) : " + s.curToken().kind.toString() + ANSI_RESET);
          //skip(s, andToken);


          //s.readNextToken();
         if(s.curToken().kind != orToken){
          // skip(s, s.curToken().kind);
           break;
         }
         skip(s, orToken);
       }

	      Main.log.leaveParser("expr");
      //  System.out.println("DETTE HER ER I EXPR2: " + s.curToken().kind.toString());

	      return ae;
      }


    @Override
    public void prettyPrint() {
      System.out.println("KOMMER INN I EXPR");
      int nPrinted = 0;
      for (AspAndTest aat: andTests) {
      	if (nPrinted > 0){
      		Main.log.prettyWrite(" or ");
      	}
        System.out.println("SKAL KALLE PAA DENNE ANDTESTEN :" + aat+ " : "+ nPrinted);
      	aat.prettyPrint();
      	++nPrinted;
      }

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	     return null;
    }
}
