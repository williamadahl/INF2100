package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNoneLiteral extends AspAtom{
	static String uff = "None";
	TokenKind kind;

	AspNoneLiteral(int n){
		super(n);
	}
	static AspNoneLiteral parse(Scanner s){
		AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
		if(s.curToken().kind == noneToken){
			Main.log.enterParser("none");
			Main.log.leaveParser("none");
			anl.kind = s.curToken().kind;
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
