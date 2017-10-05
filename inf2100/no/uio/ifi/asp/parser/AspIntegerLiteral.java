package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIntegerLiteral extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	static ArrayList<Long> dong = new ArrayList<>();
	static int counter = 0;
	// <|:^)


	AspIntegerLiteral(int n){
		super(n);
	}
	static AspIntegerLiteral parse(Scanner s){
		AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
		dong.add(s.curToken().integerLit);

		Main.log.enterParser("integer literal");
		Main.log.leaveParser("integer literal");
		skip(s, integerToken);

		return ail;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I INTEGER LITERAL");
			System.out.println("SKAL NAA SKRIVE DENNE  LONG :" + Long.toString(dong.get(counter))	);
			Main.log.prettyWrite(Long.toString(dong.get(counter)));
			counter ++;
		}
}
