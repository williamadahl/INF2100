package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspName extends AspAtom{
	ArrayList<String> guitar = new ArrayList<>();
	TokenKind kind;
	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
		Main.log.enterParser("name");
		Main.log.leaveParser("name");
		an.guitar.add(s.curToken().name);

		an.kind = s.curToken().kind;

		skip(s, nameToken);

		return an;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = new RuntimeStringValue(guitar.get(0));

		return v;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(guitar.get(0));
	}
}
