package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFloatLiteral extends AspAtom{
	static ArrayList<Double> dong = new ArrayList<>();
	static int counter = 0;


	AspFloatLiteral(int n){
		super(n);
	}
	static AspFloatLiteral parse(Scanner s){
		AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
		dong.add(s.curToken().floatLit);

		Main.log.enterParser("float literal");
		Main.log.leaveParser("float literal");
		skip(s, floatToken);

		return afl;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint() {
			Main.log.prettyWrite(Double.toString(dong.get(counter)));
			counter++;
		}
}
