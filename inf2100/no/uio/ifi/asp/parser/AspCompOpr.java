package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspCompOpr extends AspSyntax{
	AspCompOpr(int n){
		super(n);
	}
	String johnnyGuitar = "";

	static AspCompOpr parse(Scanner s) {
		AspCompOpr aco = new AspCompOpr(s.curLineNum());

		Main.log.enterParser("comp opr");
		aco.johnnyGuitar = s.curToken().toString();
		skip(s, s.curToken().kind);
		Main.log.leaveParser("comp opr");
		return aco;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(" " + johnnyGuitar + " ");

	}


}
