package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;

class AspComparison extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	ArrayList<AspTerm> termTests = new ArrayList<>();
	ArrayList<AspCompOpr> compOprTests = new ArrayList<>();

	//char[] compOperators = new char[]{ '<', '>', '==', '>=', '<=', '!=' };

	AspComparison(int n){
		super(n);
	}

	static AspComparison parse(Scanner s) {
		AspComparison acp = new AspComparison(s.curLineNum());
		//System.out.println("DETTE HER ER I COMPARISON: " + s.curToken().kind.toString());

		Main.log.enterParser("comparison");
		while (true) {
			acp.termTests.add(AspTerm.parse(s));

			if(s.curToken().kind == lessToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, lessToken);
			}else if(s.curToken().kind == greaterToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, greaterToken);
			}else if(s.curToken().kind == doubleEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, doubleEqualToken);
			}else if(s.curToken().kind == greaterEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, greaterEqualToken);
			}else if(s.curToken().kind == lessEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, lessEqualToken);
			}else if(s.curToken().kind == notEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				// skip(s, notEqualToken);
			}else{
				break;
			}
		}
		Main.log.leaveParser("comparison");
		return acp;
	}
	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	void prettyPrint() {
		System.out.println("KOMMER INN I COMPARISON");
		int nPrinted = 0;
		for (AspTerm ant: termTests) {
			System.out.println("KALLER PÅ TERMTESTSTSTSTSTSTSTSTSTSTSTSTSTSST");
			if (nPrinted > 0){
				System.out.println("KALLER NAA PAA DENNE COMPOPR SIN PRETTYPRINT: "+ compOprTests.get(0));

				if(!compOprTests.isEmpty()){
					AspCompOpr hi = compOprTests.get(0);
					compOprTests.remove(0);
					hi.prettyPrint();
				}
			}
			System.out.println("KALLER NAA PAA DENNE TERM SIN PRETTYPRINT: "+ ant);
			ant.prettyPrint();
			++nPrinted;
		}
	}
}
