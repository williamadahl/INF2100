package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspPrimarySuffix extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspSubscription as;
	AspArguments aa;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		AspPrimarySuffix aps = null;
		Main.log.enterParser("primary suffix");
		System.out.println("DETTE HER ER I primary suffix: " + s.curToken().kind.toString());
		switch(s.curToken().kind){
			case leftParToken:
			aps = AspArguments.parse(s);
			System.out.println("DETTE HER ER I PIMARYSUFFIX arguments: " + s.curToken().kind.toString());
			//skip(s, s.curToken().kind);
			break;
			case leftBracketToken:
			aps = AspSubscription.parse(s);
			System.out.println(ANSI_RED + "DETTE HER ER I PIMARYSUFFIX subscription: " + s.curToken().kind.toString() + ANSI_RESET);
			//skip(s, s.curToken().kind);
			break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return aps;

	}

	//
	// static AspPrimarySuffix parse(Scanner s){
	// 	AspPrimarySuffix aps = null;
	// 	System.out.println("DETTE HER ER I PIMARYSUFFIX 1: " + s.curToken().kind.toString());
	//
	// 	Main.log.enterParser("primary suffix");
	// 	if(s.curToken().kind == leftParToken){
	// 			aps.aa = AspArguments.parse(s);
	// 			System.out.println("DETTE HER ER I PIMARYSUFFIX ARGUMENTS: " + s.curToken().kind.toString());
	// 			skip(s, rightParToken);
	//
	//
	// 	}else if(s.curToken().kind == leftBracketToken){
	// 			aps.as = AspSubscription.parse(s);
	// 			skip(s, rightBracketToken);
	// 	}else{
	// 		//Main.parserError("No brackets", s.curLineNum());
	// 		Main.log.leaveParser("primary suffix");
	// 		return aps;
	// 	}
	// 	Main.log.leaveParser("primary suffix");
	// 	System.out.println("DETTE HER ER I PIMARYSUFFIX 2: " + s.curToken().kind.toString());
	//
	// 	return aps;
	// }
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
}
