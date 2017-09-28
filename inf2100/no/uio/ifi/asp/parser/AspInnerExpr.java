package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

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
			if(testToken(s, rightParToken)){
				Main.log.leaveParser("inner expr");
				return aie;
			}else if(testToken(s, newLineToken)){
				parserError("Expected a " + rightParToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				aie.bod1 = AspExpr.parse(s);
				s.readNextToken();
			}
		}
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(" inner expr ");
			bod1.prettyPrint();
		}
}
