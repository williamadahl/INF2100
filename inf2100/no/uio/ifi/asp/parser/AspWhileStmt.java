package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspWhileStmt extends AspStmt {
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
    super(n);
    }

    //This parse method skips whileToken,
    //Parses the expression, skips colon, and
    //Parses the suite
    static AspWhileStmt parse(Scanner s) {
      Main.log.enterParser("while stmt");
      AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
      skip(s, whileToken);
      aws.test = AspExpr.parse(s);
      skip(s, colonToken);
      aws.body = AspSuite.parse(s);

      Main.log.leaveParser("while stmt");
      return aws;
    }

    @Override
  	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
  		RuntimeValue v = null;
      while (true) {
        if(!test.eval(curScope).getBoolValue("expression", this)){
          break;
        }
        v = body.eval(curScope);
        //("Dette er i while : " + v.toString());
      }

      return v;
  	}

    @Override
  	void prettyPrint() {
      Main.log.prettyWrite("while ");
      test.prettyPrint();
      Main.log.prettyWrite(":");
      body.prettyPrint();
  	}
}
