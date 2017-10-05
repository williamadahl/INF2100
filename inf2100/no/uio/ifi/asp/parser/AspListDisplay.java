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

	ArrayList<AspExpr> boi = new ArrayList<>();

	AspListDisplay(int n){
		super(n);
	}

	static AspListDisplay parse(Scanner s){
		//System.out.println( ANSI_GREEN + "DETTE HER ER I LISTDISPLAY 1: " +s.curToken().kind.toString() + ANSI_RESET);

		Main.log.enterParser("list display");
		AspListDisplay ald = new AspListDisplay(s.curLineNum());
		skip(s, leftBracketToken);

		if(s.curToken().kind == rightBracketToken){
			skip(s, rightBracketToken);
			Main.log.leaveParser("list display");
			return ald;
		}

		while (true){
			ald.boi.add(AspExpr.parse(s));
			// ald.bod1.parse(s);
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
		//System.out.println( ANSI_GREEN + "DETTE HER ER I  END LISTDISPLAY 3: " +s.curToken().kind.toString() + ANSI_RESET);
		return ald;
	}


	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
		@Override
		void prettyPrint() {
			Main.log.prettyWrite(" [ ");
			int nPrinted = 0;
			if(!boi.isEmpty()){
				for(AspExpr lol : boi){
					if(nPrinted > 0){
						Main.log.prettyWrite(" , ");
					}
				System.out.println("SKAL NAA KALLE PAA DENNE  EXPRESSION SIN PRETTYPRINT :" + lol);
					lol.prettyPrint();
					++nPrinted;
				}
			}
			Main.log.prettyWrite(" ] ");
		}
}
