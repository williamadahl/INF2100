package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPrimary extends AspSyntax{
	AspAtom aa;
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);
		skip(s, s.curToken().kind);

		while(true){
			if(s.curToken().kind != leftParToken ||
					s.curToken().kind != leftBracketToken){
						break;
					}

			ap.aps.add(AspPrimarySuffix.parse(s));
			s.readNextToken();
		}




		Main.log.leaveParser("primary");
	}
}
