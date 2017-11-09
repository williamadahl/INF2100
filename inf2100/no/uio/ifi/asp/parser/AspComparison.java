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
		// RuntimeValue k = curScope.find(v.toString(), this);
    //
		// System.out.println("Found you");


		for (int i = 1; i < termTests.size(); ++i) {
			v = termTests.get(i-1).eval(curScope);
			TokenKind bender = compOprTests.get(i-1).kind;

			switch (bender) {
				case lessToken:
					v = v.evalLess(termTests.get(i).eval(curScope), this); break;
				case greaterToken:
					v = v.evalGreater(termTests.get(i).eval(curScope), this); break;
				case doubleEqualToken:
					v = v.evalEqual(termTests.get(i).eval(curScope), this); break;
				case greaterEqualToken:
					v = v.evalGreaterEqual(termTests.get(i).eval(curScope), this); break;
				case lessEqualToken:
					v = v.evalLessEqual(termTests.get(i).eval(curScope), this); break;
				case notEqualToken:
					v = v.evalNotEqual(termTests.get(i).eval(curScope), this); break;
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
