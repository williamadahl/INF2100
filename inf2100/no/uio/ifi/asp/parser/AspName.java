package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspName extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
		System.out.println(ANSI_PURPLE + "DETT ER I NAME " + s.curToken().kind.toString() + ANSI_RESET);

			Main.log.enterParser("name");
			Main.log.leaveParser("name");
			skip(s, nameToken);
			System.out.println(ANSI_PURPLE + "DETT ER I NAME LEAVE " + s.curToken().kind.toString() + ANSI_RESET);

			return an;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {/*
			Main.log.prettyWrite(" name ");*/
			// Main.log.prettyWrite(" name ");
			//Main.log.prettyWrite(" = ");


			// s.nextToken();
			// if(s.curToken() == null){
			// 	Main.log.parserError("Did not expect eof", s.curLineNum());
			//
			// }
			// else if(s.curToken().kind == equalToken){
			// 	Main.log.prettyWrite(" = ");
			// }
			 //AspSubscription.prettyPrint();

		}

}
