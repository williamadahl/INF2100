package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
class AspListDisplay extends AspAtom{
	AspExpr bod1;

	AspListDisplay(int n){
		super(n);
	}

	static AspListDisplay parse(Scanner s){
		Main.log.enterParser("list display");
		AspListDisplay ald = new AspListDisplay(s.curLineNum());

		while(true){
			Token temp = s.curToken();
			if(testToken(s, rightBracketToken)){
				Main.log.leaveParser("list display");
				return ald;
			}else if(testToken(s, newLineToken)){
				parserError("Expected a " + rightBracketToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				while(true){
					ald.bod1 = AspExpr.parse(s);
					s.readNextToken();
					if(s.curToken().kind != commaToken){
						break;
					}
				}
			}
		}
	}
}
