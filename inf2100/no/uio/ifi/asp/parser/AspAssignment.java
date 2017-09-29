package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspAssignment extends AspStmt{
	AspName test;
	AspExpr test2;

	ArrayList<AspSubscription> as = new ArrayList<>();

	AspAssignment(int n){
		super(n);
	}

	static AspAssignment parse(Scanner s){
		AspAssignment asss = new AspAssignment(s.curLineNum());
		System.out.println("DETTE HER ER I ASSIGNMENT: " + s.curToken().kind.toString());

		Main.log.enterParser("assignment");
		// We know it is a nameToken so we parse it to that class
		asss.test = AspName.parse(s);
		skip(s, nameToken);

		if(s.curToken().kind == leftBracketToken){
			while(true){
				asss.as.add(AspSubscription.parse(s));
				if(s.curToken().kind == equalToken){
					break;
				}
			}
		}else{
			skip(s, equalToken);

			asss.test2 = AspExpr.parse(s);
			//skip(s, newLineToken);
		}

		Main.log.leaveParser("assignment");
		System.out.println("DETTE HER ER I ASSIGNMENT2: " + s.curToken().kind.toString());

		return asss;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	void prettyPrint() {
		test.prettyPrint();


		int nPrinted = 0;
		for (AspSubscription asus : as) {
			if (nPrinted > 0){
				Main.log.prettyWrite(" assignment ");
			}
			asus.prettyPrint();
			++nPrinted;
		}
		if(test == null){
			test2.prettyPrint();
		}else if(test2 == null){
			test.prettyPrint();
		}
	}

}
