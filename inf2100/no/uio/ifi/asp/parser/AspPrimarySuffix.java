package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspPrimarySuffix extends AspSyntax{
	AspPrimarySuffix aps = null;

	boolean isArgument = false;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		AspPrimarySuffix b = null;

		Main.log.enterParser("primary suffix");
		switch(s.curToken().kind){
			case leftParToken:
				b.isArgument = true;
				b.aps = AspArguments.parse(s);

			break;
			case leftBracketToken:
			b.aps = AspSubscription.parse(s);

			break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return b;

	}

	@Override
		RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue {
			RuntimeValue v = aps.eval(curScope);
			return v;
		}

	@Override
		void prettyPrint(){
			aps.prettyPrint();
		}

}
