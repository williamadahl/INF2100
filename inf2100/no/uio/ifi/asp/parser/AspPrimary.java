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
				ap.aps.add(AspPrimarySuffix.parse(s));
		}

		Main.log.leaveParser("primary");
		System.out.println("gammle aa sizer : " + ap.aa.size());
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
	RuntimeValue eval(RuntimeScope curScope) {
			//System.out.println("AA size :" + aa.size());
		RuntimeValue v = null;
		if(aps.size() != 0){
			v = aps.get(0).eval(curScope);
			for (int i = 1; i < aps.size(); ++i) {
				v = aps.get(i).eval(curScope);
			}
		}
		//System.out.println("AA size :" + aa.size());
		v = aa.get(0).eval(curScope);
		return v;

	}

}
