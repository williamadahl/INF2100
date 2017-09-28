package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspAndTest extends AspSyntax {
	ArrayList<AspNotTest> notTests = new ArrayList<>();

	AspAndTest(int n) {
		super(n);
	}

	public static AspAndTest parse(Scanner s) {
		Main.log.enterParser("and test");
		AspAndTest aat = new AspAndTest(s.curLineNum());

		while (true) {
			aat.notTests.add(AspNotTest.parse(s));
			if (s.curToken().kind != andToken) break;
			// wtf vil ikke man skippe dobbelt naar man evnt skipper i notTests
			skip(s, andToken);
		}


		Main.log.leaveParser("and test");
		return aat;
	}
}
