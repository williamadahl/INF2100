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
		System.out.println("DETTE HER ER I NOT TEST: " + s.curToken().kind.toString());

		Main.log.enterParser("not test");
		//s.readNextToken();
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
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {

			Main.log.prettyWrite(" not ");
			body1.prettyPrint();
		}


}
