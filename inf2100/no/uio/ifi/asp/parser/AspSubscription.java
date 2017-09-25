import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSubscription extends AspPrimarySuffix{
	AspExpr body1;

	AspSubscription(int n){
		super(n);
	}

	static AspSubscription parse(Scanner s){
		Main.Log.enterParser("subscription");
		AspSubscription asub = new AspSubscription(s.curLineNum());
		skip(s, leftBracketToken);

		while(true){
			Token temp = s.curToken();
			if(testToken(temp, rightBracketToken)){
				Main.Log.exitParser("subscription");
				return asub;
			}else if(testToken(temp, newLineToken)){
				parserError("Expected a " + rightBracketToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				asub.body1 = AspExpr.parse(s);
				s.readNextToken();
			}
		}
	}
}
