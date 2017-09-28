package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNotTest extends AspSyntax{
	AspComparison body1;

	AspNotTest(int n){
		super(n);
	}

	static AspNotTest parse(Scanner s) {
		AspNotTest nut = new AspNotTest(s.curLineNum());
		Main.log.enterParser("not test");
		s.readNextToken();
		Token temp = s.curToken();
		if(temp.kind == notToken){
			skip(s, notToken);
			nut.body1 = AspComparison.parse(s);
		}else{
			nut.body1 = AspComparison.parse(s);
		}
		Main.log.leaveParser("not test");
		return nut;
	}
}
