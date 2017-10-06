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

	static AspPrimarySuffix aps = null;
	//static ArrayList<AspPrimarySuffix> aps = new ArrayList<>();


	//static boolean printed = false;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		Main.log.enterParser("primary suffix");
		//System.out.println("DETTE HER ER I primary suffix: " + s.curToken().kind.toString());
		switch(s.curToken().kind){
			case leftParToken:
			aps = AspArguments.parse(s);

			//aps.add(AspArguments.parse(s));


			//System.out.println("DETTE HER ER I PIMARYSUFFIX arguments: " + s.curToken().kind.toString());
			//skip(s, s.curToken().kind);
			break;
			case leftBracketToken:
			aps = AspSubscription.parse(s);

			//aps.add(AspSubscription.parse(s));

			//System.out.println(ANSI_RED + "DETTE HER ER I PIMARYSUFFIX subscription: " + s.curToken().kind.toString() + ANSI_RESET);
			//skip(s, s.curToken().kind);
			break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return aps;

	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint(){
			/*System.out.println("KALLER PAA DENNE SIN PRIMARYSUFFIX  :" + aps);
			//for (AspPrimarySuffix hue : aps){
				aps.get(0).prettyPrint();
				aps.remove(0);
			//}
			*/
			aps.prettyPrint();
		}

}
