package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspIntegerLiteral extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
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
		skip(s, integerToken);

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
