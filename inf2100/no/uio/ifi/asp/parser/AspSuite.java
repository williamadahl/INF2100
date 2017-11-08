package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspSuite extends AspSyntax{

	ArrayList<AspStmt> astmt = new ArrayList<>();
	AspSuite(int n){
		super(n);
	}

	static AspSuite parse(Scanner s){
		Main.log.enterParser("suite");
		AspSuite as = new AspSuite(s.curLineNum());
		skip(s, newLineToken);
		skip(s, indentToken);
		while(true){
			as.astmt.add(AspStmt.parse(s));
			if(s.curToken().kind == dedentToken){
				break;
			}
		}
		skip(s, dedentToken);
		Main.log.leaveParser("suite");

		return as;
	}
	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		RuntimeValue v  = null;
		for(AspStmt states: astmt){
			v = states.eval(curScope);
		}

		return v;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWriteLn();
		Main.log.prettyIndent();

		for(AspStmt freedom : astmt){
			freedom.prettyPrint();
		}
		Main.log.prettyDedent();
	}
}
