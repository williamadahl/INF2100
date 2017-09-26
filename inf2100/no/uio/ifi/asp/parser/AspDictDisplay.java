import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspDictDisplay extends AspAtom{
	AspStringLiteral atl;
	AspExpr ae;

	AspDictDisplay(int n){
		super(n);
	}

	static AspDictDisplay parse(Scanner s){
		Main.log.enterParser("dict display");
		AspDictDisplay add = new AspDictDisplay(s.curLineNum());

		Token temp = s.curToken();

		while(true){
			if(testToken(temp, rightBraceToken)){
				Main.Log.leaveParser("dict display");
				return add;
			}else if(testToken(temp, newLineToken)){
				parserError("Expected a " + rightBraceToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				add.atl = AspStringLiteral.parse(s);
				skip(s, stringToken);
				skip(s, colonToken);
				add.ae = AspExpr.parse(s);
				s.readNextToken();
				
				if(s.curToken.kind != commaToken){
					break;
				}
			}
		}
	}
}
