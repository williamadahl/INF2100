package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspAssignment extends AspStmt{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	AspName test;
	AspExpr test2;
	ArrayList<AspSubscription> as = new ArrayList<>();

	AspAssignment(int n){
		super(n);
	}

	static AspAssignment parse(Scanner s){
		AspAssignment asss = new AspAssignment(s.curLineNum());
		//System.out.println(ANSI_CYAN + "DETTE HER ER I ASSIGNMENT: " + s.curToken().kind.toString() + ANSI_RESET);

		Main.log.enterParser("assignment");
		// We know it is a nameToken so we parse it to that class
		asss.test = AspName.parse(s);
		//skip(s, nameToken);

		if(s.curToken().kind == leftBracketToken){
			while(true){
				asss.as.add(AspSubscription.parse(s));
				if(s.curToken().kind == equalToken){
					skip(s, equalToken);
					asss.test2 = AspExpr.parse(s);
					skip(s, newLineToken);
					Main.log.leaveParser("assignment");
					//System.out.println("DETTE HER ER I ASSIGNMENT3: " + s.curToken().kind.toString());
					return asss;
				}
			}
		}
		else{

			skip(s, equalToken);
			asss.test2 = AspExpr.parse(s);
			skip(s, newLineToken);
			Main.log.leaveParser("assignment");
			return asss;
		}
	}


	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	public void prettyPrint() {

		test.prettyPrint();

		for(AspSubscription phub : as){
			phub.prettyPrint();
		}

		Main.log.prettyWrite(" = ");
		System.out.println("KALLER PAA EXPRESSION SIN PRETTYPRINT " + test2);
		test2.prettyPrint();
		System.out.println("KALLER PAA EN PRINT NEWLINE");
		Main.log.prettyWriteLn();

	}
}
