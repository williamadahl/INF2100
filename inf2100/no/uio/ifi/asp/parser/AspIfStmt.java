package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIfStmt extends AspStmt{
	ArrayList<AspExpr> aexp = new ArrayList<>();
	ArrayList<AspSuite> asui = new ArrayList<>();
	boolean hasElse = false;

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
			aif.hasElse = true;
			aif.asui.add(AspSuite.parse(s));
		}
		Main.log.leaveParser("if stmt");
		return aif;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		RuntimeValue v = null;
		for(int i = 0; i < aexp.size(); i++){
			v = aexp.get(i).eval(curScope);
			System.out.println("dette er vaaar v : " + v);
			if(v.getBoolValue("if statement", this)){
				v = asui.get(i).eval(curScope);
				return v;
			}
		}
		if(hasElse){
			trace("else: ...");
			v = asui.get(asui.size()-1).eval(curScope);
		}
		return v;
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
