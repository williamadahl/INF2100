package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspStringLiteral extends AspAtom{
	ArrayList<String> str = new ArrayList<>();
	int counter = 0;
	String bing = "";
	TokenKind kind;

	AspStringLiteral(int n){
		super(n);
	}

	static AspStringLiteral parse(Scanner s){
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		asl.str.add(s.curToken().stringLit);

		Main.log.enterParser("string literal");
		Main.log.leaveParser("string literal");
		asl.kind = s.curToken().kind;
		skip(s, stringToken);
		return asl;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
		String kek = str.get(0).substring(1, str.get(0).length()-1);

		// //System.out.println("streng");
		// // if(v instanceof RuntimeStringValue){
    // //
		// // 	System.out.println("im a string");
		// // 	RuntimeValue k = null;
		// // 	System.out.println("----------------");
		// // 	System.out.println("I will now seach for this value : " + v.toString());
		// System.out.println("WE CAME IN HER");
		// 	if(curScope.probeValue(kek.toString(), this) == null ){
		// 		System.out.println("did not find value : " + kek.toString() );
		// 	} else{
    //
		// 		System.out.println("found varibel in scope: " + kek.toString());
		// 		System.out.println("value of variable : " + curScope.find(kek.toString(), this));
    //
		// 	}
		// 	// k = curScope.probeValue(v.toString(), this);
		// //	System.out.println(k.toString());
		// // }



		return new RuntimeStringValue(kek);
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(str.get(counter));
		counter ++;
	}
}
