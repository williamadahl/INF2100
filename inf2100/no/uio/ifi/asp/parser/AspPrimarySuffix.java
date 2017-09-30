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
		System.out.println("DETTE HER ER I PIMARYSUFFIX 1: " + s.curToken().kind.toString());

		Main.log.enterParser("primary suffix");
		if(s.curToken().kind == leftParToken){
				aps.aa = AspArguments.parse(s);
				System.out.println("DETTE HER ER I PIMARYSUFFIX ARGUMENTS: " + s.curToken().kind.toString());
				skip(s, rightParToken);

		}else if(s.curToken().kind == leftBracketToken){
				aps.as = AspSubscription.parse(s);
				skip(s, rightBracketToken);
		}else{
			//Main.parserError("No brackets", s.curLineNum());
			Main.log.leaveParser("primary suffix");
			return aps;
		}
		Main.log.leaveParser("primary suffix");
		System.out.println("DETTE HER ER I PIMARYSUFFIX 2: " + s.curToken().kind.toString());

		return aps;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
}
