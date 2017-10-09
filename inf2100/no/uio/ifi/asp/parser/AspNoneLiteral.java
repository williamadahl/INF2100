package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNoneLiteral extends AspAtom{
	static String uff = "None";

	AspNoneLiteral(int n){
		super(n);
	}
	static AspNoneLiteral parse(Scanner s){
		AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
		if(s.curToken().kind == noneToken){
			Main.log.enterParser("none");
			Main.log.leaveParser("none");
			skip(s, noneToken);
			return anl;
		}else{
		}
		return anl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(uff);
		}
}
