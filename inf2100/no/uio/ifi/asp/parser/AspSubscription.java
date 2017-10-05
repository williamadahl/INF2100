package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspSubscription extends AspPrimarySuffix{

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	AspExpr body1;

	AspSubscription(int n){
		super(n);
	}

	static AspSubscription parse(Scanner s){

		//System.out.println( ANSI_GREEN + "DETTE HER ER I SUBSCRITPION 1: " +s.curToken().kind.toString() + ANSI_RESET);
		Main.log.enterParser("subscription");
		AspSubscription asub = new AspSubscription(s.curLineNum());
		skip(s, leftBracketToken);
		asub.body1 = AspExpr.parse(s);
		//System.out.println(ANSI_GREEN + "DETTE HER ER I SUBSCRITPION 2: " +s.curToken().kind.toString()  + ANSI_RESET);
		skip(s, rightBracketToken);
		Main.log.leaveParser("subscription");
		//System.out.println(ANSI_GREEN + "DETTE HER ER I END SUBSCRITPION: " +s.curToken().kind.toString()  + ANSI_RESET);
		return asub;

	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I SUBSCRIPTION");
			Main.log.prettyWrite("[");
			System.out.println("SKAL NAA KALLE PAA DENNE SIN EKEPRESSION : " + body1);
			body1.prettyPrint();
			Main.log.prettyWrite("]");

		}
}
