package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspWhileStmt extends AspStmt {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
    super(n);
    }


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
  		RuntimeValue eval(RuntimeScope curScope) {
  			return null;
  		}

      @Override
  		void prettyPrint() {
        /*
  			Main.log.prettyWrite(" while ");
  			test.prettyPrint();
  			body.prettyPrint();
        */
  		}
}
