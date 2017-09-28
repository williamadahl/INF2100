package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactorOpr extends AspSyntax{
	AspFactorOpr(int n){
		super(n);
	}

	static AspFactorOpr parse(Scanner s){
		AspFactorOpr noe = new AspFactorOpr(s.curLineNum());
		Main.log.enterParser("Factor opr");
		Main.log.leaveParser("Factor opr");
		return noe;
	}

}
