package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactor extends AspSyntax{
	ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
	ArrayList<AspPrimary> primaryTests = new ArrayList<>();
	ArrayList<AspFactorPrefix> prefixTests = new ArrayList<>();

	AspFactor(int n){
		super(n);
	}

	static AspFactor parse(Scanner s) {
		AspFactor af = new AspFactor(s.curLineNum());
		Main.log.enterParser("factor");

		Token temp = s.curToken();
		if(temp.kind == plusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			while(true){
				af.primaryTests.add(AspPrimary.parse(s));
				if(s.curToken().kind == astToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == slashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == percentToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == doubleSlashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else {
					break;
				}
			}
		}else if(temp.kind == minusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			while(true){
				af.primaryTests.add(AspPrimary.parse(s));
				if(s.curToken().kind == astToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == slashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == percentToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == doubleSlashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else {
					break;
				}
			}
		}else{
			while(true){
				af.primaryTests.add(AspPrimary.parse(s));
				if(s.curToken().kind == astToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == slashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == percentToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else if(s.curToken().kind == doubleSlashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
				}else {
					break;
				}
			}
		}
		Main.log.leaveParser("factor");
		return af;
	}


	@Override
	void prettyPrint() {
		if(!prefixTests.isEmpty()){
			prefixTests.get(0).prettyPrint();
		}
		if(primaryTests.size() == 1){
			primaryTests.get(0).prettyPrint();
		}else{
			primaryTests.get(0).prettyPrint();
			for(int i = 1; i< primaryTests.size(); i++){
				factorOprTests.get(i-1).prettyPrint();
				primaryTests.get(i).prettyPrint();
			}
		}
	}


	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = primaryTests.get(0).eval(curScope);
		RuntimeValue next = null;

		if(prefixTests.size() != 0) {
			TokenKind gender = prefixTests.get(0).kind;
			switch(gender){
				case minusToken:
				v = v.evalNegate(this); break;

				case plusToken:
				v = v.evalPositive(this); break;
				default:
				Main.panic("Illegal term operator: " + gender + "!");
			}
		}

		for (int i = 1; i < primaryTests.size(); ++i) {
			TokenKind k = factorOprTests.get(i-1).kind;
			next = primaryTests.get(i).eval(curScope);



			RuntimeValue vpotential = curScope.probeValue(v.toString(), this);
			// System.out.println("Value of V : " + v.toString());
			RuntimeValue nextpotential = curScope.probeValue(next.toString(), this);


			if((vpotential == null) && (nextpotential == null)){
				// v = primaryTests.get(0).eval(curScope);
				// next = primaryTests.get(i).eval(curScope);

			}
			else if(vpotential == null){
				// v = primaryTests.get(0).eval(curScope);
				// System.out.println("HHHHHHHHHHHHHHHHH" + v);
				next = nextpotential;
			}else if(nextpotential == null){
				//next = primaryTests.get(i).eval(curScope);
				v = vpotential;
			}else{
				// v = curScope.probeValue();
				v = vpotential;
				next = nextpotential;

			}

			switch (k) {
				case astToken:
				v = v.evalMultiply(next, this);
				break;
				case slashToken:
				v = v.evalDivide(next, this); break;
				case percentToken:
				v = v.evalModulo(next, this); break;
				case doubleSlashToken:
				v = v.evalIntDivide(next, this); break;
				default:
				Main.panic("Illegal term operator: " + k + "!");
			}
		}
		return v;
	}
}
