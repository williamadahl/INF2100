package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFloatLiteral extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	static ArrayList<Double> dong = new ArrayList<>();
	static int counter = 0;


	AspFloatLiteral(int n){
		super(n);
	}
	static AspFloatLiteral parse(Scanner s){
		AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
		dong.add(s.curToken().floatLit);

		Main.log.enterParser("float literal");
		Main.log.leaveParser("float literal");
		skip(s, floatToken);

		return afl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I FLOAT LITERAL");
			System.out.println("WRITER NAA UT DENNE DOUBLE : " + Double.toString(dong.get(counter)) );
			Main.log.prettyWrite(Double.toString(dong.get(counter)));
			counter++;
		}
}
