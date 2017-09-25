import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInnerExpr extends AspAtom{
	AspExpr bod1;

	AspInnerExpr(int n){
		super(n);
	}

	static AspInnerExpr parse(Scanner s){
		Main.log.enterParser("Inner expr");
		AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

		while(true){
			Token temp = s.curToken();
			if(testToken(temp, rightParToken)){
				Main.Log.exitParser("inner expr");
				return aie;
			}else if(testToken(temp, newLineToken)){
				parserError("Expected a " + rightParToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				aie.bod1 = AspExpr.parse(s);
				s.readNextToken();
			}
		}
	}
}
