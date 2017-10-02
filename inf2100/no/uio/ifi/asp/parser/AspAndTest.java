package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspAndTest extends AspSyntax {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	ArrayList<AspNotTest> notTests = new ArrayList<>();

	AspAndTest(int n) {
		super(n);
	}




	public static AspAndTest parse(Scanner s) {
		System.out.println("DETTE HER ER I AND: " + s.curToken().kind.toString());

		Main.log.enterParser("and test");
		AspAndTest aat = new AspAndTest(s.curLineNum());

		while (true) {
			aat.notTests.add(AspNotTest.parse(s));
			if (s.curToken().kind != andToken) {
				break;
			// wtf vil ikke man skippe dobbelt naar man evnt skipper i notTests
			}
			skip(s, andToken);
		}


		Main.log.leaveParser("and test");
		return aat;
	}

	@Override
	void prettyPrint() {
		// int nPrinted = 0;
		// for (AspNotTest ant: notTests) {
		// 	if (nPrinted > 0){
		// 		Main.log.prettyWrite(" and ");
		// 	}
		// 	ant.prettyPrint();
		// 	++nPrinted;
		// }
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) {
		return null;
	}

}
