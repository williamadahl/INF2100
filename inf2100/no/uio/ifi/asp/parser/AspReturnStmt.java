class AspReturnStmt extends AspStmt{
  AspExpr ae;

  AspReturnStmt(int n){
    super(n);
  }
  static AspReturnStmt parse(Scanner s){
    AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
    Main.log.enterParser("return");
    skip(s, returnToken);
    ars.ae = AspExpr.parse(s);
    skip(s, newLineToken);
    Main.log.leaveParser("return");
    return ars;
  }

}
