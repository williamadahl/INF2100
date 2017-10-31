package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactorPrefix extends AspSyntax{
	String ayy = "";
	TokenKind kind;

	AspFactorPrefix(int n){
		super(n);
	}

	static AspFactorPrefix parse(Scanner s){
		AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
		afp.ayy = s.curToken().kind.toString();
		afp.kind = s.curToken().kind;
		Main.log.enterParser("factor prefix");
		skip(s, s.curToken().kind);
		Main.log.leaveParser("factor prefix");
		return afp;

	}
	@Override
	RuntimeValue eval(RuntimeScope curScope) {
		return null;
	}


	@Override
	void prettyPrint() {
		Main.log.prettyWrite(ayy);
	}
}
