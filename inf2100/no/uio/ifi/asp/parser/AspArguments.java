class AspArguments extends AspPrimarySuffix{
	ArrayList<AspExpr> asex = new ArrayList<>();

	AspArguments(int n){
		super(n);
	}

	static AspArguments parse(Scanner s){
		Main.log.enterParser("arguments");
		AspArguments arar = new AspArguments(s.curLineNum());

	while(true){
		Token temp = s.curToken();
		if(testToken(temp, rightParToken)){
			Main.Log.exitParser("arguments");
			return arar;
		}else if(testToken(temp, newLineToken)){
			parserError("Expected a " + rightBracketToken + " but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}else{
			while(true){
				arar.asex.add(AspExpr.parse(s));
				s.readNextToken();
				if(s.curToken().kind != commaToken){
					break;
				}
			}
		}	
		}
	}
}
