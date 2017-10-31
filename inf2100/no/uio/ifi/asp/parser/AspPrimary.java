package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPrimary extends AspSyntax{

	ArrayList<AspAtom> aa = new ArrayList<>();
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa.add(AspAtom.parse(s));

		while((s.curToken().kind == leftParToken) || (s.curToken().kind == leftBracketToken)){
			if(s.curToken().kind == leftBracketToken){
				ap.aps.add(AspSubscription.parse(s));
			}else{
				ap.aps.add(AspArguments.parse(s));
			}
				//ap.aps.add(AspPrimarySuffix.parse(s));
		}

		Main.log.leaveParser("primary");
		return ap;
	}


	@Override
	void prettyPrint() {
		AspAtom hi = aa.get(0);
		//aa.remove(0);
		hi.prettyPrint();

		for(AspPrimarySuffix a : aps ){
			a.prettyPrint();
		}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = null;

		if(aps.size() != 0){
			v = aa.get(0).eval(curScope);
			v = v.evalSubscription(aps.get(0).eval(curScope), this);
			return v;
		}
		v = aa.get(0).eval(curScope);
		return v;
	}

}
