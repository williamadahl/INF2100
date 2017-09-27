class AspPassStmt extends AspStmt{

  AspPassStmt(int n){
    super(n);
  }
  static AspPassStmt parse(Scanner s){
    AspPassStmt aps = new AspPassStmt(s.curLineNum());
    Main.log.enterParser("pass");
    skip(s, newLineToken);
    Main.log.leaveParser("pass");
    return aps;
  }

}
