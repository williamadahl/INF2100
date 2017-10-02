package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactor extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
	ArrayList<AspPrimary> primaryTests = new ArrayList<>();
	ArrayList<AspFactorPrefix> prefixTests = new ArrayList<>();

	AspFactor(int n){
		super(n);
	}

	static AspFactor parse(Scanner s) {
		Main.log.enterParser("factor");
		System.out.println("DETTE HER ER I FACTOR: " + s.curToken().kind.toString());

		AspFactor af = new AspFactor(s.curLineNum());
		//s.readNextToken();
		Token temp = s.curToken();
		if(temp.kind == plusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			skip(s, plusToken);
		}else if(temp.kind == minusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			skip(s, minusToken);
		}else{
				while(true){
					af.primaryTests.add(AspPrimary.parse(s));
					if(s.curToken().kind == astToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, astToken);
					}else if(s.curToken().kind == slashToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, slashToken);
					}else if(s.curToken().kind == percentToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, percentToken);
					}else if(s.curToken().kind == doubleSlashToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, doubleSlashToken);
					}else {
						break;
					}
				}
		}
		Main.log.leaveParser("factor");
		return af;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			// int nPrinted = 0;
			// for (AspFactorOpr afo: factorOprTests) {
			// 	if (nPrinted > 0){
			// 		Main.log.prettyWrite(" factor ");
			// 	}
			// 	afo.prettyPrint();
			// 	++nPrinted;
			// }
			// int nPrinted2 = 0;
			// for (AspPrimary ap: primaryTests) {
			// 	if (nPrinted2 > 0){
			// 		Main.log.prettyWrite(" factor ");
			// 	}
			// 	ap.prettyPrint();
			// 	++nPrinted2;
			// }
			// int nPrinted3 = 0;
			// for (AspFactorPrefix afp: prefixTests) {
			// 	if (nPrinted3 > 0){
			// 		Main.log.prettyWrite(" factor ");
			// 	}
			// 	afp.prettyPrint();
			// 	++nPrinted3;
			// }
		}
}
