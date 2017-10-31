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
		Main.log.enterParser("assignment");
		//Parse to AspName, according to the Jernbanediagrammet
		asss.test = AspName.parse(s);

		//Checks if there's a subscription
		if(s.curToken().kind == leftBracketToken){
			//Loops until there are to subscriptions
			while(true){
				asss.as.add(AspSubscription.parse(s));
				if(s.curToken().kind == equalToken){
					skip(s, equalToken);
					asss.test2 = AspExpr.parse(s);
					skip(s, newLineToken);
					Main.log.leaveParser("assignment");
					return asss;
				}
			}
		}
		else{
			skip(s, equalToken);
			asss.test2 = AspExpr.parse(s);
			skip(s, newLineToken);
			Main.log.leaveParser("assignment");
			return asss;
		}
	}


	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	public void prettyPrint() {
		test.prettyPrint();
		for(AspSubscription phub : as){
			phub.prettyPrint();
		}
		Main.log.prettyWrite(" = ");
		test2.prettyPrint();
		Main.log.prettyWriteLn();

	}
}
