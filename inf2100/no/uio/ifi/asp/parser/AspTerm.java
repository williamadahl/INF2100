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
		System.out.println("dette er i AspTerm : " + v.toString());
		RuntimeValue next = null;
		
		for (int i = 1; i < factorTests.size(); ++i) {
			TokenKind k = termOprTests.get(i-1).kind;
			next = factorTests.get(i).eval(curScope);


			System.out.println("Naa er vi i Term og prober paa  v : " + v.toString());
			System.out.println("Naa er vi i Term og prober paa next  : " + next.toString());

			v = curScope.probeValue(v.toString(), this);
			next = curScope.probeValue(next.toString(), this);

			//System.out.println("Dette er e veriden til Next etter probe : " + next.toString());

			if((v == null) && (next == null)){
				System.out.println("1");
				v = factorTests.get(0).eval(curScope);
				next = factorTests.get(i).eval(curScope);
			}
			else if(v == null){
				System.out.println("2");
				v = factorTests.get(0).eval(curScope);
			}else if(next == null){
				System.out.println("3");
				next = factorTests.get(i).eval(curScope);
			}else{
				System.out.println("Both variables have values!");
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

		System.out.println("Inne i term : "  + v.toString());
		return v;
	}
}
