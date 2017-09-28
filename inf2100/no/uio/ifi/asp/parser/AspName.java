package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspName extends AspAtom{
	AspSubscription body1;
	AspExpr body2;

	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
			Main.log.enterParser("navn");
			Main.log.leaveParser("navn");
			return an;
	}
}
