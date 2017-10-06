package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspStringLiteral extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	ArrayList<String> str = new ArrayList<>();
	int counter = 0;
	String bing = "";

	AspStringLiteral(int n){
		super(n);
	}

	static AspStringLiteral parse(Scanner s){
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		asl.str.add(s.curToken().stringLit);

		Main.log.enterParser("string literal");
		Main.log.leaveParser("string literal");
		skip(s, stringToken);
		return asl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			//int counter = 0;
			System.out.println(ANSI_RED + "KOMMER INN I STRING LITERAL" + str.get(counter) + ANSI_RESET);
			Main.log.prettyWrite(str.get(counter));
			counter ++;
		}

}
