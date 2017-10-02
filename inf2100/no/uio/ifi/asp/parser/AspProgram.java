package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;



public class AspProgram extends AspSyntax {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
    ArrayList<AspStmt> stmts = new ArrayList<>();


    AspProgram(int n) {
	     super(n);
    }


    public static AspProgram parse(Scanner s) {
	     Main.log.enterParser("program");
       System.out.println("DETTE HER ER I PROGRAM: " + s.curToken().kind.toString());

	      AspProgram ap = new AspProgram(s.curLineNum());
	       while (s.curToken().kind != eofToken) {
	          ap.stmts.add(AspStmt.parse(s));
            if(s.curToken().kind == eofToken){
              break;
            }
            System.out.println("DETTE HER ER I PROGRAM2: " + s.curToken().kind.toString());
            //s.readNextToken();
	         }
	          Main.log.leaveParser("program");
	          return ap;
    }


    @Override
    public void prettyPrint() {

    	// int nPrinted = 0;
    	// for (AspStmt ast: stmts) {
      //   Main.log.prettyWrite(" program ");
      //   //ast.prettyPrint();
      //   System.out.println("{}");
    	// 	if (nPrinted > 0){
    	// 		Main.log.prettyWrite(" program ");
    	// 	}
    	// 	 //ast.prettyPrint();
      //   System.out.println("{}");
      //
    	// 	++nPrinted;
    	// 	}

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 4:
	return null;
    }
}
