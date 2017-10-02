package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspExprStmt extends AspStmt{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspExpr body;

	AspExprStmt(int n){
		super(n);
	}

	static AspExprStmt parse(Scanner s){
		Main.log.enterParser("expr stmt");
		AspExprStmt aes = new AspExprStmt(s.curLineNum());
		//s.readNextToken();
		aes.body = AspExpr.parse(s);
		skip(s, newLineToken);
		//skip(s, s.curToken().kind);

		Main.log.leaveParser("expr stmt");
		return aes;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		public void prettyPrint() {/*

			Main.log.prettyWrite(" expr statement ");
			body.prettyPrint();
		}
 */
 }
}
