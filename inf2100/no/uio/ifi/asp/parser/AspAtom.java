package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;
abstract class AspAtom extends AspSyntax{
	AspAtom(int n) {
		super(n);
	}

	static AspAtom parse(Scanner s){
		AspAtom a = null;
		Main.log.enterParser("atom");
		switch (s.curToken().kind) {
			case falseToken:
			case trueToken:
			a = AspBooleanLiteral.parse(s);
			skip(s, s.curToken().kind); break;
			case floatToken:
			a = AspFloatLiteral.parse(s);
			skip(s, floatToken); break;
			case integerToken:
			a = AspIntegerLiteral.parse(s);
			skip(s, integerToken); break;
			case leftBraceToken:
			a = AspDictDisplay.parse(s);
			skip(s, leftBraceToken);  break;
			case leftBracketToken:
			a = AspListDisplay.parse(s);
			skip(s, leftBracketToken);  break;
			case leftParToken:
			a = AspInnerExpr.parse(s);
			skip(s, leftParToken);  break;
			case nameToken:
			a = AspName.parse(s);
			skip(s, nameToken);  break;
			case noneToken:
			a = AspNoneLiteral.parse(s);
			skip(s, noneToken);  break;
			case stringToken:
			a = AspStringLiteral.parse(s);
			skip(s, stringToken);  break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.leaveParser("atom");
		return a;
	}

}
