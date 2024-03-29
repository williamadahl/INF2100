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
		AspTerm atat = new AspTerm(s.curLineNum());
		Main.log.enterParser("term");
		while (true) {
			atat.factorTests.add(AspFactor.parse(s));
			if(s.curToken().kind == plusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
			}else if(s.curToken().kind == minusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
			}else{
				break;
			}
		}
		Main.log.leaveParser("term");
		return atat;
	}

	@Override
	void prettyPrint() {
		int nPrinted = 0;
		int counter = 0;
		for (AspFactor ant: factorTests){
			if(nPrinted > 0){
				if(!termOprTests.isEmpty()){
					AspTermOpr hi = termOprTests.get(counter);
					hi.prettyPrint();
					counter++;
				}
			}
			++nPrinted;
			ant.prettyPrint();
		}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = factorTests.get(0).eval(curScope);
		RuntimeValue next = null;

		for (int i = 1; i < factorTests.size(); ++i) {
			TokenKind k = termOprTests.get(i-1).kind;
			next = factorTests.get(i).eval(curScope);

			RuntimeValue vpotential = curScope.probeValue(v.toString(), this);
			RuntimeValue nextpotential = curScope.probeValue(next.toString(), this);

			// same as in comparison and AspFactor
			if((vpotential == null) && (nextpotential == null)){

			}
			else if(vpotential == null){
				next = nextpotential;
			}else if(nextpotential == null){
				v = vpotential;
			}else{
				v = vpotential;
				next = nextpotential;
			}

			switch (k) {
				case minusToken:
				v = v.evalSubtract(next, this); break;
				case plusToken:
				v = v.evalAdd(next, this); break;
				default:
				Main.panic("Illegal term operator: " + k + "!");
			}
		}

		return v;
	}
}
