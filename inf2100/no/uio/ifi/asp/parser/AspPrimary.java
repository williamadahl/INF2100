package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPrimary extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	AspAtom aa;
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		//System.out.println("DETTE HER ER I PRIMARY: " + s.curToken().kind.toString());
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);
		//System.out.println("DETTE HER ER I PRIMARY 2: " + s.curToken().kind.toString());
		//skip(s, s.curToken().kind);

		while((s.curToken().kind == leftParToken) || (s.curToken().kind == leftBracketToken)){
				ap.aps.add(AspPrimarySuffix.parse(s));
				//System.out.println("DETTE HER ER I PRIMARY 3: " + s.curToken().kind.toString());
				//s.readNextToken();
		}

		Main.log.leaveParser("primary");
		return ap;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {
			// int nPrinted = 0;
			// Main.log.prettyWrite(" primary suffix ");
			// aa.prettyPrint();
			//
			// for (AspPrimarySuffix ap: aps) {
			// 	if (nPrinted > 0){
			// 		Main.log.prettyWrite(" pirmary suffix ");
			// 	}
			// 	ap.prettyPrint();
			// 	++nPrinted;
			// }
		}

}
