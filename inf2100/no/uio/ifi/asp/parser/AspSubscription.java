package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
class AspSubscription extends AspPrimarySuffix{

	AspExpr body1;

	AspSubscription(int n){
		super(n);
	}

	static AspSubscription parse(Scanner s){
		Main.log.enterParser("subscription");
		AspSubscription asub = new AspSubscription(s.curLineNum());
		skip(s, leftBracketToken);

		while(true){
			Token temp = s.curToken();
			if(testToken(s, rightBracketToken)){
				Main.log.leaveParser("subscription");
				skip(s, rightBracketToken);
				return asub;
			}else if(testToken(s, newLineToken)){
				parserError("Expected a " + rightBracketToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				asub.body1 = AspExpr.parse(s);
				s.readNextToken();
			}
		}
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {/*
			Main.log.prettyWrite(" subscription ");
			body1.prettyPrint();*/
		}
}
