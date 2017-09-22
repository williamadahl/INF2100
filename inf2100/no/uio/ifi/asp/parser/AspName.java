class AspName extends AspAtom{
	AspSubscription body1;
	AspExpr body2;

	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName asnm = new AspName(s.curLineNum());

		//String temp = s.curToken().toString();
		//Cheking if the nameToken only contains valid chars
		// for(i = 0 ; i < temp.length; i++){
		// 	if(!(isLetterAZ(temp.charAt(i))) || !(isDigit(temp.charAt(i))) || !(temp.charAt(i) == '_') ){
		//
		// 	}
		//
		// }
		Main.log.enterParser("name");
		skip(s, nameToken);

		if(testToken(s, leftBracketToken)){
			asnm.body1 = AspSubscription.parse(s);
		}else if(testToken(s, equalToken )){
			//Skipping equalToken because expr isn't expecting
			//Any symbol that's not suppose to be in expr
			skip(s, equalToken);
			asnm.body2 = AspExpr.parse(s);
		}else{
			parserError("Expected a " + leftBracketToken + " or a " + equalToken + " but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.exitParser("name");
		return asnm;
	}

}
