package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspName extends AspAtom{
	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
			Main.log.enterParser("name");
			Main.log.leaveParser("name");
			return an;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {/*
			Main.log.prettyWrite(" name ");*/
			Main.log.prettyWrite(" name ");
			s.nextToken();
			if(s.curToken() == null){
				Main.log.parserError("Did not expect eof", s.curLineNum());

			}
			else if(s.curToken().kind == equalToken){
				Main.log.prettyWrite(" = ");
			}
			 //AspSubscription.prettyPrint();

		}

}
