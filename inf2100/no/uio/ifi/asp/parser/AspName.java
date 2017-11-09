package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspName extends AspAtom{
	ArrayList<String> guitar = new ArrayList<>();
	TokenKind kind;
	AspName(int n){
		super(n);
	}

	static AspName parse(Scanner s){
		AspName an = new AspName(s.curLineNum());
		Main.log.enterParser("name");
		Main.log.leaveParser("name");
		an.guitar.add(s.curToken().name);

		an.kind = s.curToken().kind;

		skip(s, nameToken);

		return an;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = new RuntimeStringValue(guitar.get(0));

			// System.out.println("im a string");
			RuntimeValue k = null;
			System.out.println("----------------");
			// System.out.println("I will now seach for this value : " + v.toString());
			k = curScope.probeValue(v.toString(), this);

			if(k == null){
				System.out.println("did not find value : " + v.toString() );
			} else{
				/*
				Need to test for all different Runtimevalues, and evaluate the value they are pointing to, not their name (if they indeed are pointing to a value)
				*/
				System.out.println("found a value  : " + v.toString());
				if(k instanceof RuntimeIntValue){
					RuntimeIntValue irv = (RuntimeIntValue)k;
					// System.out.println("found varibel in scope: " + v.toString());
					// System.out.println("value of variable : " + curScope.find(v.toString(), this));
					return irv;
				}
				else if(k instanceof RuntimeFloatValue){
					RuntimeFloatValue frv = (RuntimeFloatValue)k;
					// System.out.println("found varibel in scope: " + v.toString());
					// System.out.println("value of variable : " + curScope.find(v.toString(), this));
					return frv;
				}

			}
			// k = curScope.probeValue(v.toString(), this);
		//	System.out.println(k.toString());


		System.out.println("This is the value Im pointing at in Comparison : " + v.showInfo());


		Main.log.traceEval("name ", this);
		//return curScope.find(guitar.get(0) ,this);
		return v;
	}

	@Override
	void prettyPrint() {
		//System.out.println("HER HAR DU NAME " + guitar.get(0);
		Main.log.prettyWrite(guitar.get(0));
	}
}
