package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspNotTest extends AspSyntax{
	AspComparison body1;
	boolean dank = false;

	AspNotTest(int n){
		super(n);
	}

	static AspNotTest parse(Scanner s) {
		AspNotTest nut = new AspNotTest(s.curLineNum());
		Main.log.enterParser("not test");
		Token temp = s.curToken();
		if(temp.kind == notToken){
			skip(s, notToken);
			nut.dank = true;
			nut.body1 = AspComparison.parse(s);
		}else{
			nut.dank = false;
			nut.body1 = AspComparison.parse(s);
		}
		Main.log.leaveParser("not test");
		return nut;
	}

		@Override
		void prettyPrint() {
			if(dank){
				Main.log.prettyWrite(" not ");
			}
			body1.prettyPrint();

		}

		@Override
		RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
			RuntimeValue v = body1.eval(curScope);

			if(dank){
				v = v.evalNot(this);
			}
			return v;
		}


}
