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

	      AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
          ap.stmts.add(AspStmt.parse(s));
          if(s.curToken().kind == eofToken){
            break;
          }
        }
        Main.log.leaveParser("program");
        return ap;
    }


    @Override
    public void prettyPrint() {
    	for(AspStmt burger : stmts){
        burger.prettyPrint();
      }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v = null;
      try{
        for(int i = 0; i < stmts.size(); i++){
          v = stmts.get(i).eval(curScope);
        }
      } catch (RuntimeReturnValue xxx){
    
      }
      return v;
    }
}
