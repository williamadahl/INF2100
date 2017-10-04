package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNotTest extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	AspComparison body1;
	static AspNotTest nut;
	static boolean dank = true;

	AspNotTest(int n){
		super(n);
	}

	static AspNotTest parse(Scanner s) {
		nut = new AspNotTest(s.curLineNum());
		//System.out.println("DETTE HER ER I NOT TEST: " + s.curToken().kind.toString());

		Main.log.enterParser("not test");
		//s.readNextToken();
		Token temp = s.curToken();
		if(temp.kind == notToken){
			skip(s, notToken);
			dank = true;
			nut.body1 = AspComparison.parse(s);
		}else{
			dank = false;
			nut.body1 = AspComparison.parse(s);
		}
		Main.log.leaveParser("not test");
		return nut;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I NOT");
			if(dank){
				Main.log.prettyWrite(" not ");
			}
			body1.prettyPrint();

		}


}
