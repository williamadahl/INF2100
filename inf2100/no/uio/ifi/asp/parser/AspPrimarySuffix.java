package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspPrimarySuffix extends AspSyntax{
	static AspPrimarySuffix aps = null;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		Main.log.enterParser("primary suffix");
		switch(s.curToken().kind){
			case leftParToken:
			aps = AspArguments.parse(s);

			break;
			case leftBracketToken:
			aps = AspSubscription.parse(s);

			break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return aps;

	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint(){
			aps.prettyPrint();
		}

}
