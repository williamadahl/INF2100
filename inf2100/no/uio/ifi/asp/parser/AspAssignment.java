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

		v = test.eval(curScope);
		v = curScope.find(v.showInfo(), this);
		
		System.out.println("AspAssignment sin v value : " + temp);

		for(int i = 0; i < as.size()-1; i++){
			v = v.evalSubscription(as.get(i).eval(curScope), this);
		}

		//System.out.println("Her har du V " + v);

		if(as.isEmpty()){
		//	System.out.println("Her har du nøkkelen " + v);
			curScope.assign(v.showInfo(), test2.eval(curScope));
			System.out.println("Her har du verdien " + curScope.find(v.showInfo(), this));
			System.out.println();
		}else{
		//	System.out.println("Kommer inn hit+++++++");
			v = curScope.find(v.showInfo(), this);
			curScope.assign(v.showInfo(), test2.eval(curScope));

			System.out.println(curScope.find(v.showInfo(), this));
	//		System.out.println("Ferdig med å komme");
//
			System.out.println(curScope.find(v.showInfo(), this).showInfo());

			v.evalAssignElem(as.get(as.size()-1).eval(curScope), test2.eval(curScope), this);

			System.out.println("oxioi"+ curScope.find(v.showInfo(), this).showInfo());
		}

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
