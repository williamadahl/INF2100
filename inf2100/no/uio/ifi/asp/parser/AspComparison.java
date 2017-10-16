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
		for (AspTerm ant: termTests) {
			if (nPrinted > 0){

				if(!compOprTests.isEmpty()){
					AspCompOpr hi = compOprTests.get(0);
					compOprTests.remove(0);
					hi.prettyPrint();
				}
			}
			ant.prettyPrint();
			++nPrinted;
		}
	}


//lager bare for AspTerms for naa, vil bare komme ut av denne classen
// kommentert ut if , aner ikke hva som skal testes mot

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = termTests.get(0).eval(curScope);
		for(int i = 1 ; i < termTests.size(); ++i){
			if(! v.getBoolValue("and operator", this)){
				return v;
			}
			v = termTests.get(i).eval(curScope);
		}
		return v;
	}


}
