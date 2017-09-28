package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIfStmt extends AspStmt{
	ArrayList <AspExpr> aexp = new ArrayList<>();
	ArrayList <AspSuite> asui = new ArrayList<>();


	AspIfStmt(int n){
		super(n);
	}

	static AspIfStmt parse(Scanner s){
		AspIfStmt aif = new AspIfStmt(s.curLineNum());
		Main.log.enterParser("if statement");
		skip(s, ifToken);
		while(true){
			aif.aexp.add(AspExpr.parse(s));
			s.readNextToken();
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			s.readNextToken();
			if(s.curToken().kind != elifToken){
				break;
			}
		}
		if(s.curToken().kind == elseToken){
			skip(s, elseToken);
			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			s.readNextToken();
		}
		Main.log.leaveParser("if statement");
		return aif;
	}
}
