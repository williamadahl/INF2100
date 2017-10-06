package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspTerm extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	ArrayList<AspFactor> factorTests = new ArrayList<>();
	ArrayList<AspTermOpr> termOprTests = new ArrayList<>();

	AspTerm(int n){
		super(n);
	}

	static AspTerm parse(Scanner s) {
		AspTerm atat = new AspTerm(s.curLineNum());
		//System.out.println("DETTE HER ER I TERM: " + s.curToken().kind.toString());

		Main.log.enterParser("term");
		while (true) {
			atat.factorTests.add(AspFactor.parse(s));
			if(s.curToken().kind == plusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
			}else if(s.curToken().kind == minusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
			}else{
				break;
			}
		}
		Main.log.leaveParser("term");
		return atat;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I TERM");
			int nPrinted = 0;
			System.out.println("Dette er FACTORY size :" + factorTests.size());
			for (AspFactor ant: factorTests){
				System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
				if(nPrinted > 0){
					//System.out.println("KALLER PAA DENNE SIN PRETTYPRINT : " +termOprTests.get(0) );
					// ant.prettyPrint();
					if(!termOprTests.isEmpty()){
						AspTermOpr hi = termOprTests.get(0);
						termOprTests.remove(0);
						hi.prettyPrint();
					}
				}
				++nPrinted;
				ant.prettyPrint();
			}
		}
}
