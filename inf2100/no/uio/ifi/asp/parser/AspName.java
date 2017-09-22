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

		test(s, leftBracketToken);
		asnm.body1 = AspSubscription.parse(s);

		test(s, equalToken);
		asnm.body2 = AspExpr.parse(s);
		
		Main.log.ecitParser("name");
	}
}
