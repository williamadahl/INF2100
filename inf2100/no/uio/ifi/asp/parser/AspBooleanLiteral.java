package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspBooleanLiteral extends AspAtom{
	ArrayList<String> hi = new ArrayList<>();
	int counter = 0;
	TokenKind kind;

	AspBooleanLiteral(int n){
		super(n);
	}

	static AspBooleanLiteral parse(Scanner s){
		AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
		
		if(s.curToken().kind == trueToken ||
		s.curToken().kind == falseToken){
			abl.hi.add(s.curToken().kind.toString());
			Main.log.enterParser("boolean literal");
			Main.log.leaveParser("boolean literal");
			abl.kind = s.curToken().kind;
			skip(s, s.curToken().kind);
			return abl;
		}else{
		}
		return abl;
	}


	@Override
	void prettyPrint() {
		Main.log.prettyWrite(hi.get(counter));
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		if(hi.get(0) == "False"){
			return new RuntimeBoolValue(false);
		}
		return new RuntimeBoolValue(true);
	}
}
