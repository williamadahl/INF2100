package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;

class AspComparison extends AspSyntax{
	ArrayList<AspTerm> termTests = new ArrayList<>();
	ArrayList<AspCompOpr> compOprTests = new ArrayList<>();

	AspComparison(int n){
		super(n);
	}

	static AspComparison parse(Scanner s) {
		AspComparison acp = new AspComparison(s.curLineNum());
		Main.log.enterParser("comparison");
		while (true) {
			acp.termTests.add(AspTerm.parse(s));

			if(s.curToken().kind == lessToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else if(s.curToken().kind == greaterToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else if(s.curToken().kind == doubleEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else if(s.curToken().kind == greaterEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else if(s.curToken().kind == lessEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else if(s.curToken().kind == notEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
			}else{
				break;
			}
		}
		Main.log.leaveParser("comparison");
		return acp;
	}


	@Override
	void prettyPrint() {
		int nPrinted = 0;
		int counter = 0;
		for (AspTerm ant: termTests) {
			if (nPrinted > 0){

				if(compOprTests.get(counter) != null){
					AspCompOpr hi = compOprTests.get(counter);
					hi.prettyPrint();
					counter ++;
				}
			}
			ant.prettyPrint();
			++nPrinted;
		}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = termTests.get(0).eval(curScope);
		RuntimeValue next = null;


		for (int i = 1; i < termTests.size(); ++i) {
			v = termTests.get(i-1).eval(curScope);
			next = termTests.get(i).eval(curScope);
			TokenKind bender = compOprTests.get(i-1).kind;


			RuntimeValue vpotential = curScope.probeValue(v.toString(), this);
			RuntimeValue nextpotential = curScope.probeValue(next.toString(), this);

			// if both vpotential and nextpotential are null (no value with those names) we let v and next
			// point to what they are pointing at.
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


			switch (bender) {
				case lessToken:
				v = v.evalLess(next, this); break;
				case greaterToken:
				v = v.evalGreater(next, this); break;
				case doubleEqualToken:
				v = v.evalEqual(next, this); break;
				case greaterEqualToken:
				v = v.evalGreaterEqual(next, this); break;
				case lessEqualToken:
				v = v.evalLessEqual(next, this); break;
				case notEqualToken:
				v = v.evalNotEqual(next, this); break;
				default:
				Main.panic("Illegal term operator: " + bender + "!");
			}
			if(!v.getBoolValue("and operator", this)){
				return v;
			}
		}
		return v;
	}
}
