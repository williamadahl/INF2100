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

	ArrayList<String> guitar = new ArrayList<>();
	// int counter = 0;

	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
		//System.out.println(ANSI_PURPLE + "DETT ER I NAME " + s.curToken().kind.toString() + ANSI_RESET);

			Main.log.enterParser("name");
			Main.log.leaveParser("name");
			an.guitar.add(s.curToken().name);
			skip(s, nameToken);

			return an;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			// System.out.println("KOMMER INN I NAME");
			// System.out.println(ANSI_PURPLE + "SKAL WRITE DETTE STRENG : " + guitar.get(counter) + ANSI_RESET);
			Main.log.prettyWrite(guitar.get(0));
			// counter++;
		}

}
