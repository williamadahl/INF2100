package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNoneLiteral extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	static String uff = "None";

	AspNoneLiteral(int n){
		super(n);
	}
	static AspNoneLiteral parse(Scanner s){
		AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
		if(s.curToken().kind == noneToken){
			Main.log.enterParser("none");
			Main.log.leaveParser("none");
			skip(s, noneToken);
			return anl;
		}else{
			//Main.parserError("Not none token :(", s.curLineNum());
		}
		return anl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(uff);
		}
}
