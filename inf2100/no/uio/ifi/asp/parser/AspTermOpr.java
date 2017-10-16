package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspTermOpr extends AspSyntax{

	String ayy = "";

	AspTermOpr(int n){
		super(n);
	}
	static AspTermOpr parse(Scanner s) {
		AspTermOpr ato = new AspTermOpr(s.curLineNum());
		Main.log.enterParser("term opr");
		Main.log.leaveParser("term opr");
		ato.ayy = s.curToken().kind.toString();
		skip(s, s.curToken().kind);
		return ato;
	}


	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(" " + ayy + " ");
		}
}
