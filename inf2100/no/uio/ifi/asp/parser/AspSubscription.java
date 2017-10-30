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
		asub.body1 = AspExpr.parse(s);
		skip(s, rightBracketToken);
		Main.log.leaveParser("subscription");
		return asub;

	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
			RuntimeValue v = null;
	
			 v = body1.eval(curScope);
			 System.out.println(v.showInfo());
			 return v;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite("[");
			body1.prettyPrint();
			Main.log.prettyWrite("]");

		}
}
