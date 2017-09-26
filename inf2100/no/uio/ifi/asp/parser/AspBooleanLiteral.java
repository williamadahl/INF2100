class AspBooleanLiteral extends AspAtom{
	AspBooleanLiteral(int n){
		super(n);
	}
	static AspBooleanLiteral parse(Scanner s){
		AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
		if(s.curToken.kind == trueToken ||
		s.curToken.kind == falseToken){
			Main.log.enterParser("boolean literal");
			Main.log.leaveParser("boolean literal");
			return abl;
		}else{
			Main.parserError("Neither true or false", s.curLineNum());
		}
	}
}
