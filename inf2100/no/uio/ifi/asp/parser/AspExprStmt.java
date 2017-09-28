package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspExprStmt extends AspStmt{
	AspExpr body;

	AspExprStmt(int n){
		super(n);
	}

	static AspExprStmt parse(Scanner s){
		Main.log.enterParser("expr stmt");
		AspExprStmt aes = new AspExprStmt(s.curLineNum());
		s.readNextToken();
		aes.body = AspExpr.parse(s);
		skip(s, newLineToken);
		Main.log.leaveParser("expr stmt");
		return aes;
	}
}
