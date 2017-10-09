package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspBooleanLiteral extends AspAtom{
	static ArrayList<String> hi = new ArrayList<>();
	static int counter = 0;

	AspBooleanLiteral(int n){
		super(n);
	}

	static AspBooleanLiteral parse(Scanner s){
		AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
		if(s.curToken().kind == trueToken ||
		s.curToken().kind == falseToken){
			hi.add(s.curToken().kind.toString());
			Main.log.enterParser("boolean literal");
			Main.log.leaveParser("boolean literal");

			skip(s, s.curToken().kind);
			return abl;
		}else{
		}
		return abl;
	}


	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(hi.get(counter));
		counter++;
	}
}
