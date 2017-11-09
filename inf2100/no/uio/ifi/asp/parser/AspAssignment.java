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
	RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue{
		RuntimeValue v = null;
		RuntimeValue k = null;
		boolean multipleSubscription = false;

		v = test.eval(curScope);

		for(int i = 0; i < as.size()-1; i++){
			if(i == 0){
				k = curScope.find(v.toString(), this);
				k = k.evalSubscription(as.get(i).eval(curScope), this);
				multipleSubscription = true;
			}else{
				k = k.evalSubscription(as.get(i).eval(curScope), this);
			}
		}
		if(as.isEmpty()){
			curScope.assign(v.toString(), test2.eval(curScope));
			System.out.println("Inne i enkel assignment : "  + v.toString());
		}else{
			if(!multipleSubscription){
				k = curScope.find(v.toString(), this);
			}
			k.evalAssignElem(as.get(as.size()-1).eval(curScope), test2.eval(curScope), this);
			System.out.println("dette er det vi vil bytte til : " + curScope.find(v.toString() ,this));

		}
		System.out.println("Dette er verdien til V(A) : " + v);


		return v;
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
