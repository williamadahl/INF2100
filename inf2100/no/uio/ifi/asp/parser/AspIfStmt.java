class AspIfStmt extends AspStmt{
	ArrayList <AspExpr> aexp = new ArrayList<>();
	ArrayList <AspSuite> asui = new ArrayList<>();


	AspIfStmt(int n){
		super(n);
	}

	static AspIfStmt parse(Scanner s){
		AspIfStmt aif = new AspIfStmt(s.curLineNum());
		Main.log.enterParser("if statement");
		skip(s, ifToken);
		while(true){
			aif.aexp.add(AspExpr.parse(s));
			s.readNextToken();
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			s.readNextToken();
			if(s.curToken() != elifToken){
				break;
			}
		}
		if(s.curToken == elseToken){
			skip(s, elseToken);
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			s.readNextToken();
		}
		Main.log.leaveParser("if statement");
		return aif;
	}
}
