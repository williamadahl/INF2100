package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspListDisplay extends AspAtom{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspExpr bod1;

	AspListDisplay(int n){
		super(n);
	}

	static AspListDisplay parse(Scanner s){
		System.out.println( ANSI_GREEN + "DETTE HER ER I LISTDISPLAY 1: " +s.curToken().kind.toString() + ANSI_RESET);

		Main.log.enterParser("list display");
		AspListDisplay ald = new AspListDisplay(s.curLineNum());
		skip(s, leftBracketToken);

		if(s.curToken().kind == rightBracketToken){
			skip(s, rightBracketToken);
			Main.log.leaveParser("list display");
			return ald;
		}

		while (true){
			ald.bod1.parse(s);
			if(s.curToken().kind == rightBracketToken){
				skip(s, rightBracketToken);
				Main.log.leaveParser("list display");
				return ald;
			}

			if(s.curToken().kind != commaToken){
				break;
			}
			skip(s, commaToken);
		}
		skip(s, rightBracketToken);
		Main.log.leaveParser("list display");
		System.out.println( ANSI_GREEN + "DETTE HER ER I  END LISTDISPLAY 3: " +s.curToken().kind.toString() + ANSI_RESET);
		return ald;
	}


/*
	static AspListDisplay parse(Scanner s){
		System.out.println( ANSI_GREEN + "DETTE HER ER I LISTDISPLAY 1: " +s.curToken().kind.toString() + ANSI_RESET);

		Main.log.enterParser("list display");
		AspListDisplay ald = new AspListDisplay(s.curLineNum());
		skip(s, leftBracketToken);



		while(true){
			System.out.println( ANSI_GREEN + "DETTE HER ER I LISTDISPLAY 2: " +s.curToken().kind.toString() + ANSI_RESET);

			Token temp = s.curToken();
			if(testToken(s, rightBracketToken)){
				Main.log.leaveParser("list display");
				return ald;
			}else if(testToken(s, newLineToken)){

				parserError("Expected a " + rightBracketToken + " but found a " +
				s.curToken().kind + "!", s.curLineNum());
			}else{
				while(true){
					ald.bod1 = AspExpr.parse(s);
					s.readNextToken();
					if(s.curToken().kind != commaToken){
						skip(s, rightBracketToken);
						break;
					}
				}
			}
			Main.log.leaveParser("list display");
			System.out.println( ANSI_GREEN + "DETTE HER ER I  END LISTDISPLAY 3: " +s.curToken().kind.toString() + ANSI_RESET);

			return ald;
		}
	}
*/
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {/*
			Main.log.prettyWrite(" list display ");
			bod1.prettyPrint();*/
		}
}
