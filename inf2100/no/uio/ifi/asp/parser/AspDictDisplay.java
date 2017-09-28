package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


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
			if(testToken(s, rightBraceToken)){
				Main.log.leaveParser("dict display");
				return add;
			}else if(testToken(s, newLineToken)){
				parserError("Expected a " + rightBraceToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				add.atl = AspStringLiteral.parse(s);
				skip(s, stringToken);
				skip(s, colonToken);
				add.ae = AspExpr.parse(s);
				s.readNextToken();

				if(s.curToken().kind != commaToken){
					break;
				}
			}
		}
		return add;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}
	@Override
	void prettyPrint() {
		Main.log.prettyWrite(" dict display ");
		atl.prettyPrint();
		ae.prettyPrint();
	}
}
