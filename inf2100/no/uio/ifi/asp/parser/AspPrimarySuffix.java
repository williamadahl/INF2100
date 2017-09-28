package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspPrimarySuffix extends AspSyntax{
	AspSubscription as;
	AspArguments aa;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		AspPrimarySuffix aps = null;

		Main.log.enterParser("primary suffix");
		if(s.curToken().kind == leftParToken){
				aps.aa = AspArguments.parse(s);
				skip(s, rightParToken);

		}else if(s.curToken().kind == leftBracketToken){
				aps.as = AspSubscription.parse(s);
				skip(s, rightBracketToken);
		}else{
			//Main.parserError("No brackets", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return aps;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
}
