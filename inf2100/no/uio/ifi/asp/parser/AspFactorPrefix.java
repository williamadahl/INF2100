package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactorPrefix extends AspSyntax{
	AspFactorPrefix(int n){
		super(n);
	}

	static AspFactorPrefix parse(Scanner s){
		AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
		Main.log.enterParser("factor prefix");
		Main.log.leaveParser("factor prefix");
		return afp;

	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint() {
				Main.log.prettyWrite(" factor prefix ");
		}
	}
