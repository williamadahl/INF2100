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

	ArrayList<AspExpr> aexp = new ArrayList<>();
	ArrayList<AspSuite> asui = new ArrayList<>();
	static int counter = 0;


	AspIfStmt(int n){
		super(n);
	}

	static AspIfStmt parse(Scanner s){
		AspIfStmt aif = new AspIfStmt(s.curLineNum());
		Main.log.enterParser("if stmt");
		//System.out.println("DETTE HER ER I IFSTMT: " +s.curToken().kind.toString());

		skip(s, ifToken);
		while(true){
			aif.aexp.add(AspExpr.parse(s));
			// s.readNextToken();
			//System.out.println("DETTE HER ER I IFSTMT 2 : " +s.curToken().kind.toString());

			skip(s, colonToken);

			aif.asui.add(AspSuite.parse(s));
			// s.readNextToken();
			if(s.curToken().kind != elifToken){
				break;
			}
			skip(s, elifToken);
		}
		//System.out.println("DETTE HER ER I ELSE Før IF: " +s.curToken().kind.toString());
		//ute av while
		if(s.curToken().kind == elseToken){
			skip(s, elseToken);
			//System.out.println("DETTE HER ER I ELSE: " +s.curToken().kind.toString());

			skip(s, colonToken);
			aif.asui.add(AspSuite.parse(s));
			// s.readNextToken();
		}
		Main.log.leaveParser("if stmt");
		//System.out.println("DETTE HER ER I IFSTMT LEAVE: " +s.curToken().kind.toString());

		return aif;
	}
	@Override
	RuntimeValue eval(RuntimeScope curScope) {
		return null;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(" if ");
		for(AspExpr dick : aexp){
			dick.prettyPrint();
			Main.log.prettyWrite(" : ");
			asui.get(counter).prettyPrint();
			counter++;

			if(counter < aexp.size()){
				Main.log.prettyWrite(" elif ");
			}
		}
		if(counter != asui.size()){
			Main.log.prettyWrite(" else ");
			Main.log.prettyWrite(" : ");
			asui.get(counter).prettyPrint();
			counter++;
		}
	}
}
