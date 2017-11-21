package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIntegerLiteral extends AspAtom{
	ArrayList<Long> dong = new ArrayList<>();
	TokenKind kind;

	int counter = 0;
	// <|:^)


	AspIntegerLiteral(int n){
		super(n);
	}
	static AspIntegerLiteral parse(Scanner s){
		AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
		ail.dong.add(s.curToken().integerLit);

		Main.log.enterParser("integer literal");
		Main.log.leaveParser("integer literal");
		ail.kind = s.curToken().kind;
		skip(s, integerToken);

		return ail;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(Long.toString(dong.get(counter)));

	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		return new RuntimeIntValue(dong.get(0));
	}

}
