package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIntegerLiteral extends AspAtom{
	AspIntegerLiteral(int n){
		super(n);
	}
	static AspIntegerLiteral parse(Scanner s){
		AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
		Main.log.enterParser("integer literal");
		/*if(s.curToken().kind.integerLit == 0){
			skip(s, integerToken);
			return ail;
		}else{
			for(int i = 0; i<s.curToken().integerLit.length(); i++){
				if(!(isDigit(s.curToken().integerLit.charAt(i)))){
					Main.parserError("Not all chars are integers!", s.curLineNum());
				}
			}
			Main.log.leaveParser("integer literal");
			return ail;
		}*/
		Main.log.leaveParser("integer literal");
		return ail;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {/*
			Main.log.prettyWrite(" integer literal ");*/
		}
}
