package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIfStmt extends AspStmt{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
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
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {/*
			int nPrinted = 0;
			for (AspExpr ae: aexp) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" if statement ");
				}
				ae.prettyPrint();
				++nPrinted;
			}
			for (AspSuite a: asui) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" if statement ");
				}
				a.prettyPrint();
				++nPrinted;
			}*/
		}
}
