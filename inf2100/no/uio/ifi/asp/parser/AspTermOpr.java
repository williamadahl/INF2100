package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspTermOpr extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	String ayy = "";

	AspTermOpr(int n){
		super(n);
	}
	static AspTermOpr parse(Scanner s) {
		AspTermOpr ato = new AspTermOpr(s.curLineNum());
		Main.log.enterParser("term opr");
		Main.log.leaveParser("term opr");
		ato.ayy = s.curToken().kind.toString();
		skip(s, s.curToken().kind);
		return ato;
	}


	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			System.out.println("SKAL NAA SKRIVE DENNE STRENGEN :" + ayy);
			Main.log.prettyWrite(ayy);
		}
}
