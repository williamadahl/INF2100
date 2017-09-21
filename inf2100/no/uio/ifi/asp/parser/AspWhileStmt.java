class AspWhileStmt extends AspStmt {
  AspExpr test;
  AspSuite body;

  AspWhileStmt(int n) {
    super(n);
  }


  static AspWhileStmt parse(Scanner s) {

    Main.log.enterParser("while stmt");
    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
    skip(s, whileToken);  aws.test = AspExpr.parse(s);
    skip(s, colonToken);  aws.body = AspSuite.parse(s);

    Main.log.leaveParser("while stmt");
    return aws;

    System.out.println("Weienerdoggos r superior than cattos");
}
