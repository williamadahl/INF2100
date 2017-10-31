package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspReturnStmt extends AspStmt{
  AspExpr ae;

  AspReturnStmt(int n){
    super(n);
  }
  
  static AspReturnStmt parse(Scanner s){
    AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
    Main.log.enterParser("return stmt");
    skip(s, returnToken);
    ars.ae = AspExpr.parse(s);
    skip(s, newLineToken);
    Main.log.leaveParser("return stmt");

    return ars;
  }

  @Override
  RuntimeValue eval(RuntimeScope curScope) {
    return null;
  }

  @Override
  void prettyPrint() {
    Main.log.prettyWrite("return ");
    ae.prettyPrint();
    Main.log.prettyWriteLn();
  }
}
