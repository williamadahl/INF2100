package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;



public class AspProgram extends AspSyntax {
  ArrayList<AspStmt> stmts = new ArrayList<>();


    AspProgram(int n) {
	     super(n);
    }


    public static AspProgram parse(Scanner s) {
	     Main.log.enterParser("program");
       //System.out.println("DETTE HER ER I PROGRAM: " + s.curToken().kind.toString());

	      AspProgram ap = new AspProgram(s.curLineNum());
	       while (s.curToken().kind != eofToken) {
	          ap.stmts.add(AspStmt.parse(s));
            if(s.curToken().kind == eofToken){
              break;
            }
            //System.out.println("DETTE HER ER I PROGRAM2: " + s.curToken().kind.toString());
             //s.readNextToken();
	         }
	          Main.log.leaveParser("program");
	          return ap;
    }


    @Override
    public void prettyPrint() {
    	for(AspStmt burger : stmts){
        burger.prettyPrint();
        //Main.log.prettyWriteLn();
      }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 4:
	return null;
    }
}
