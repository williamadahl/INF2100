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
		// RuntimeValue k = curScope.find(v.toString(), this);
		//
		// System.out.println("Found you");
		System.out.println("Dette er I compariston :" + v.toString());


		for (int i = 1; i < termTests.size(); ++i) {
			v = termTests.get(i-1).eval(curScope);
			next = termTests.get(i).eval(curScope);
			TokenKind bender = compOprTests.get(i-1).kind;

			System.out.println("----------------");
			// System.out.println("I will now seach for this value : " + v.toString());
			v = curScope.probeValue(v.toString(), this);
			// System.out.println("Value of V : " + v.toString());
			next = curScope.probeValue(next.toString(), this);
			// System.out.println("Value of  next: " + next.toString());

			if((v == null) && (next == null)){
				v = termTests.get(i-1).eval(curScope);
				next = termTests.get(i).eval(curScope);
			}
			else if(v == null){
				v = termTests.get(i-1).eval(curScope);
			}else if(next == null){
				next = termTests.get(i).eval(curScope);
			}else{
				System.out.println("Both variables have values!");
			}
			// v = curScope.probeValue(v.toString(), this);
			System.out.println("This is the value Im pointing at in Comparison : " + v.showInfo());

			switch (bender) {
				case lessToken:
				v = v.evalLess(next, this); break;
				case greaterToken:
				v = v.evalGreater(next, this); break;
				case doubleEqualToken:
				System.out.println("Here is v in comparison: " + v.toString());
				System.out.println("This is tokenkind: " + bender);
				System.out.println("Here is next in comparison: " + next.toString());
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
			if(! v.getBoolValue("and operator", this)){
				return v;
			}
		}
		return v;
	}
}
