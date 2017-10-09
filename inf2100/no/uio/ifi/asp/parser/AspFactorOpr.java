package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactorOpr extends AspSyntax{
	String kek = "";

	AspFactorOpr(int n){
		super(n);
	}

	static AspFactorOpr parse(Scanner s){
		AspFactorOpr noe = new AspFactorOpr(s.curLineNum());
		Main.log.enterParser("factor opr");
		Main.log.leaveParser("factor opr");
		noe.kek = s.curToken().kind.toString();

		skip(s, s.curToken().kind);
		return noe;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

	@Override
		void prettyPrint() {
			Main.log.prettyWrite(" " + kek + " ");
		}
}
