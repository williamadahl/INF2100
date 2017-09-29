package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspTerm extends AspSyntax{
	ArrayList<AspFactor> factorTests = new ArrayList<>();
	ArrayList<AspTermOpr> termOprTests = new ArrayList<>();

	AspTerm(int n){
		super(n);
	}

	static AspTerm parse(Scanner s) {
		System.out.println("DETTE HER ER I TERM: " + s.curToken().kind.toString());

		Main.log.enterParser("term");
		AspTerm atat = new AspTerm(s.curLineNum());
		while (true) {
			atat.factorTests.add(AspFactor.parse(s));
			if(s.curToken().kind == plusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
				skip(s, plusToken);
			}else if(s.curToken().kind == minusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
				skip(s, minusToken);
			}else{
				break;
			}
		}
		Main.log.leaveParser("term");
		return atat;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			int nPrinted = 0;
			for (AspFactor af: factorTests) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" term ");
				}
				af.prettyPrint();
				++nPrinted;
			}
			int nPrinted2 = 0;
			for (AspTermOpr ato: termOprTests) {
				if (nPrinted2 > 0){
					Main.log.prettyWrite(" term ");
				}
				ato.prettyPrint();
				++nPrinted2;
			}
		}
}
