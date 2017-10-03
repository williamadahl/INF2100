package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspArguments extends AspPrimarySuffix{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	ArrayList<AspExpr> asex = new ArrayList<>();

	AspArguments(int n){
		super(n);
	}



	static AspArguments parse(Scanner s){
		//System.out.println("DETTE HER ER I ARGUMENTS 1: " + s.curToken().kind.toString());

		Main.log.enterParser("arguments");
		AspArguments arar = new AspArguments(s.curLineNum());
		skip(s, leftParToken);

		if(s.curToken().kind == rightParToken){
			skip(s, rightParToken);
			Main.log.leaveParser("arguments");
			return arar;
		}

		while (true){
			arar.asex.add(AspExpr.parse(s));

			if(s.curToken().kind == rightParToken){
				skip(s, rightParToken);
				Main.log.leaveParser("arguments");
				return arar;
			}

			if(s.curToken().kind != commaToken){
				break;
			}
			skip(s, commaToken);
		}

		skip(s, rightParToken);
		Main.log.leaveParser("arguments");
		return arar;

		// while(true){
		// 	Token temp = s.curToken();
		// 	System.out.println("HER HAR DU TEMP INNI ARGUMENTS: " + temp.kind.toString());
		// 	if(testToken(s, rightParToken)){
		// 		Main.log.leaveParser("arguments");
		// 		skip(s, rightParToken);
		// 		System.out.println("NAA TREFFER VI RIGHTPARTOKEN HERRRRRRRRR: " + s.curToken().kind.toString());
		// 		return arar;
		// 	}else if(testToken(s, newLineToken)){
		// 		//System.out.println("DETTE HER ER I ARGUMENTS 22222222222222: " + s.curToken().kind.toString());
		//
		// 		System.out.println(s.curToken().kind.toString());
		// 		Main.log.leaveParser("arguments");
		// 		return arar;
		// 		// FORTSETT HERFRA AXAXAXAX
		// 		//
		// 		//parserError("Expected a " + rightBracketToken + " but found a " +
		// 		//s.curToken().kind + "!", s.curLineNum());
		// 	}else{
		// 		System.out.println(ANSI_PURPLE + "ETTER ELSE I ARGUMENTS: "+s.curToken().kind.toString() + ANSI_RESET);
		// 		while(true){
		// 			arar.asex.add(AspExpr.parse(s));
		// 			s.readNextToken();
		// 			if(s.curToken().kind != commaToken){
		// 				break;
		// 			}
		// 		}
		// 	}
		// 	// return arar;
		// 	Main.log.leaveParser("arguments");
		// 	return arar;
		// }

	}


	@Override
		public RuntimeValue eval(RuntimeScope curScope){
			return null;
		}

		@Override
		void prettyPrint() {/*
			int nPrinted = 0;
			for (AspExpr ae: asex) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" arguments ");
				}
				ae.prettyPrint();
				++nPrinted;
			}*/
		}
}
