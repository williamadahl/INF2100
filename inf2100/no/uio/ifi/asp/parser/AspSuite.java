class AspSuite extends AspSyntax{
	ArrayList<AspStmt> astmt = new ArrayList<>();
	AspSuite(int n){
		super(n);
	}

	static AspSuite parse(Scanner s){
		Main.log.enterParser("suite");
		AspSuite as = new AspSuite(s.curLineNum());
		skip(s, newLineToken);
		skip(s, indentToken);
		while(true){
			as.astmt.add(AspStmt.parse(s));
			s.readNextToken();
			if(s.curToken().kind == dedentToken){
				break;
			}
		}
		skip(s, dedentToken);
		Main.log.leaveParser("suite");
		return as;
	}

}
