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
		skip(s, leftParToken);
		Main.log.enterParser("inner expr");
		AspInnerExpr aie = new AspInnerExpr(s.curLineNum());


		aie.bod1 = AspExpr.parse(s);
		skip(s, rightParToken);
		Main.log.leaveParser("inner expr");
		return aie;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(" ( ");
			bod1.prettyPrint();
			Main.log.prettyWrite(" ) ");
		}
}
