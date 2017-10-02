package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


class AspDictDisplay extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspStringLiteral atl;
	AspExpr ae;

	AspDictDisplay(int n){
		super(n);
	}

	static AspDictDisplay parse(Scanner s){
		Main.log.enterParser("dict display");
		AspDictDisplay add = new AspDictDisplay(s.curLineNum());
		skip(s, leftBraceToken);

		Token temp = s.curToken();

		while(true){
			if(testToken(s, rightBraceToken)){
				Main.log.leaveParser("dict display");
				skip(s, s.curToken().kind);

				return add;
			}else if(testToken(s, newLineToken)){
				parserError("Expected a " + rightBraceToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				add.atl = AspStringLiteral.parse(s);
				skip(s, stringToken);
				skip(s, colonToken);
				add.ae = AspExpr.parse(s);
				s.readNextToken();

				if(s.curToken().kind != commaToken){
					break;
				}
			}
		}
		Main.log.leaveParser("dict display");
		return add;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override

	void prettyPrint() {/*
		Main.log.prettyWrite(" dict display ");
		atl.prettyPrint();
		ae.prettyPrint();*/
	}
}
