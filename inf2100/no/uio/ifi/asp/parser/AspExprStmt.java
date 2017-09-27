class AspExprStmt extends AspStmt{
	AspExpr body;

	AspExprStmt(int n){
		super(n);
	}

	static AspExprStmt parse(Scanner s){
		Main.log.enterParser("expr stmt");
		AspExprStmt aes = new AspExprStmt(s.curLineNum());
		s.readNextToken();
		aes.body = AspExpr.parse(s);
		skip(s, newLineToken);
		Main.log.exitParser("expr stmt");
		return aes;
	}
}
