package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPassStmt extends AspStmt{

  AspPassStmt(int n){
    super(n);
  }
  static AspPassStmt parse(Scanner s){
    AspPassStmt aps = new AspPassStmt(s.curLineNum());
    skip(s, passToken);
    Main.log.enterParser("pass");
    skip(s, newLineToken);
    Main.log.leaveParser("pass");
    return aps;
  }
  @Override
  RuntimeValue eval(RuntimeScope curScope) throws RuntimeValue{
    traceEval("none ", this);
    return null;
  }

  @Override
  void prettyPrint() {
    Main.log.prettyWrite(" pass ");
    Main.log.prettyWriteLn();
  }
}
