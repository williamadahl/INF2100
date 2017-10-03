package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspSuite extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	ArrayList<AspStmt> astmt = new ArrayList<>();
	AspSuite(int n){
		super(n);
	}

	static AspSuite parse(Scanner s){
		Main.log.enterParser("suite");
		//System.out.println(ANSI_YELLOW + "DETT ER I SUITE: " + s.curToken().kind.toString() + ANSI_RESET);

		AspSuite as = new AspSuite(s.curLineNum());
		skip(s, newLineToken);
		skip(s, indentToken);
		while(true){
			as.astmt.add(AspStmt.parse(s));
			// System.out.println("DETTE HER ER I IFSTMT: " +s.curToken().kind.toString());
			//System.out.println(ANSI_YELLOW+ "DETT ER I SUITE HHHH: " + s.curToken().kind.toString() + ANSI_RESET);

			// s.readNextToken();
			if(s.curToken().kind == dedentToken){
				break;
			}
		}
		skip(s, dedentToken);
		Main.log.leaveParser("suite");
		//System.out.println(ANSI_YELLOW+ "DETT ER I SUITE LEAVE: " + s.curToken().kind.toString() + ANSI_RESET);

		return as;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {
			// int nPrinted = 0;
			// for (AspStmt a: astmt) {
			// 	if (nPrinted > 0){
			// 		Main.log.prettyWrite(" suite ");
			// 	}
			// 	a.prettyPrint();
			// 	++nPrinted;
			// }
		}
}
