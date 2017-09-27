class AspAssignment extends AspStmt{
	AspName test;
	AspExpr test2;

	ArrayList<AspSubscription> as = new ArrayList<>();

	AspAssignment(int n){
		super(n);
	}

	static AspAssignment parse(Scanner s){
		AspAssignment asss = new AspAssignment(s.curLineNum());
		Main.log.enterParser("assignment");
		// We know it is a nameToken so we parse it to that class
		asss.test = AspName.parse(s);
		skip(s, nameToken);

		if(s.curToken.kind == leftBracketToken){
			while(true){
				asss.as.add(AspSubscription.parse(s));
				if(s.curToken().kind == equalToken){
					break;
				}
			}
		}else{
			skip(s, equalToken);
			asss.test2 = AspExpr.parse(s);
			skip(s, newLineToken);
		}


		Main.log.exitParser("assignment");



	}
}
