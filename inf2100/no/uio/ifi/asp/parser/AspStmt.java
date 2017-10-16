package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspStmt extends AspSyntax{
	AspStmt as;

	AspStmt(int n){
		super(n);
	}

	static AspStmt a = null;
	static AspStmt parse(Scanner s){
		Main.log.enterParser("stmt");

		//Find out what type of token it is
		//Send it to the correct class
		switch (s.curToken().kind) {
			case nameToken:
			case integerToken:
			case stringToken:
			case minusToken:
			case floatToken:
			case notToken:
			case falseToken:
			case leftBracketToken:
			case leftBraceToken:
			case leftParToken:
			//Checks if any equal token on line
			//If yes, it's an assignment
			//Else, it's an expression statement
			if (s.anyEqualToken()){
					a = AspAssignment.parse(s);
			}else{
					a = AspExprStmt.parse(s);
			}
			break;

			case ifToken:
				a = AspIfStmt.parse(s);
				break;

			case whileToken:
				a = AspWhileStmt.parse(s);
				break;

			case returnToken:
				a = AspReturnStmt.parse(s);
				break;

			case passToken:
				a = AspPassStmt.parse(s);
				break;

			case defToken:
				a = AspFuncDef.parse(s);
				break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}

		Main.log.leaveParser("stmt");
		a.as = a;
		return a;
	}

	//Abstract class, just passes prettyprint around to the
	//Correct subclass
	@Override
	void prettyPrint() {
		as.prettyPrint();
	}

//bare test for am dette sender videre

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		RuntimeValue v = a.eval(curScope);
		return v;
	}
}
