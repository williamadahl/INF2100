package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIfStmt extends AspStmt{
	ArrayList<AspExpr> aexp = new ArrayList<>();
	ArrayList<AspSuite> asui = new ArrayList<>();

	AspIfStmt(int n){
		super(n);
	}

	static AspIfStmt parse(Scanner s){
		AspIfStmt aif = new AspIfStmt(s.curLineNum());
		Main.log.enterParser("if stmt");

		skip(s, ifToken);
		//Sends expr, skips :, sends suite
		//Until there are no more elif tokens
		while(true){
			aif.aexp.add(AspExpr.parse(s));
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			if(s.curToken().kind != elifToken){
				break;
			}
			skip(s, elifToken);
		}

		//Checks if there is an else in the program
		if(s.curToken().kind == elseToken){
			skip(s, elseToken);
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
		}
		Main.log.leaveParser("if stmt");
		return aif;
	}
	@Override
	RuntimeValue eval(RuntimeScope curScope) {
		return null;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite("if ");
		int counter = 0;
		int nPrinted = 0;
		for (AspExpr ae: aexp) {
			if (nPrinted > 0){
				Main.log.prettyWrite("elif ");
			}
			ae.prettyPrint();
			Main.log.prettyWrite(":");
			asui.get(counter).prettyPrint();
			++nPrinted;
			counter++;

		 }

		 if(asui.size() > aexp.size()){
			 Main.log.prettyWrite("else");
			 Main.log.prettyWrite(":");
			 asui.get(counter).prettyPrint();
		 }

	}
}
