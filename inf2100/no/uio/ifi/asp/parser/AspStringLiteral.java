package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspStringLiteral extends AspAtom{
	ArrayList<String> str = new ArrayList<>();
	int counter = 0;
	String bing = "";
	TokenKind kind;

	AspStringLiteral(int n){
		super(n);
	}

	static AspStringLiteral parse(Scanner s){
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		asl.str.add(s.curToken().stringLit);

		Main.log.enterParser("string literal");
		Main.log.leaveParser("string literal");
		asl.kind = s.curToken().kind;
		skip(s, stringToken);
		return asl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(str.get(counter));
			counter ++;
		}

}
