package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


public class AspExpr extends AspSyntax {

    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	     super(n);
    }

    public static AspExpr parse(Scanner s) {
      AspExpr ae = new AspExpr(s.curLineNum());
	     Main.log.enterParser("expr");

       while(true){
         ae.andTests.add(AspAndTest.parse(s));
         s.readNextToken();
         if(s.curToken().kind != orToken){
           break;
         }
         skip(s, orToken);
       }

	      Main.log.leaveParser("expr");
	      return ae;
      }


    @Override
    public void prettyPrint() {
  
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	     return null;
    }
}
