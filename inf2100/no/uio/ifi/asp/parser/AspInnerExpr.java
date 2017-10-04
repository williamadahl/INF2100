package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspInnerExpr extends AspAtom{
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

	AspInnerExpr(int n){
		super(n);
	}

	static AspInnerExpr parse(Scanner s){
		skip(s, leftParToken);
		Main.log.enterParser("inner expr");
		AspInnerExpr aie = new AspInnerExpr(s.curLineNum());


		aie.bod1 = AspExpr.parse(s);
		skip(s, rightParToken);
		Main.log.leaveParser("inner expr");
		return aie;


		//while(true){
			// Token temp = s.curToken();
			// if(testToken(s, rightParToken)){
			// 	Main.log.leaveParser("inner expr");
			// 	return aie;
			// }else if(testToken(s, newLineToken)){
			// 	parserError("Expected a " + rightParToken + " but found a " +
			// 	s.curToken().kind + "!", s.curLineNum());
			// }else{
			// 	aie.bod1 = AspExpr.parse(s);
			// 	s.readNextToken();
			// }
		//}
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite(" ( ");
			bod1.prettyPrint();
			Main.log.prettyWrite(" ) ");
		}
}
