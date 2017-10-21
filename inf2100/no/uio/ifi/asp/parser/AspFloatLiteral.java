package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFloatLiteral extends AspAtom{
	ArrayList<Double> dong = new ArrayList<>();
	int counter = 0;
	TokenKind kind;


	AspFloatLiteral(int n){
		super(n);
	}
	static AspFloatLiteral parse(Scanner s){
		AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
		afl.dong.add(s.curToken().floatLit);

		Main.log.enterParser("float literal");
		Main.log.leaveParser("float literal");
		afl.kind = s.curToken().kind;
		skip(s, floatToken);

		return afl;
	}


	@Override
		void prettyPrint() {
			Main.log.prettyWrite(Double.toString(dong.get(counter)));

		}


		@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


}
