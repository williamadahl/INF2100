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
		System.out.println("DETTE HER ER I PRIMARY: " + s.curToken().kind.toString());
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);
		System.out.println("DETTE HER ER I PRIMARY: " + s.curToken().kind.toString());
		//skip(s, s.curToken().kind);

		while(true){
			if(s.curToken().kind != leftParToken ||
					s.curToken().kind != leftBracketToken){
						break;
					}

			ap.aps.add(AspPrimarySuffix.parse(s));
			s.readNextToken();
		}
		Main.log.leaveParser("primary");
		return ap;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {
			int nPrinted = 0;
			Main.log.prettyWrite(" primary suffix ");
			aa.prettyPrint();

			for (AspPrimarySuffix ap: aps) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" pirmary suffix ");
				}
				ap.prettyPrint();
				++nPrinted;
			}
		}

}
