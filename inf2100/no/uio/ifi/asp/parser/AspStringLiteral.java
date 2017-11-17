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

		// ////("streng");
		// // if(v instanceof RuntimeStringValue){
    // //
		// // 	//("im a string");
		// // 	RuntimeValue k = null;
		// // 	//("----------------");
		// // 	//("I will now seach for this value : " + v.toString());
		// //("WE CAME IN HER");
		// 	if(curScope.probeValue(kek.toString(), this) == null ){
		// 		//("did not find value : " + kek.toString() );
		// 	} else{
    //
		// 		//("found varibel in scope: " + kek.toString());
		// 		//("value of variable : " + curScope.find(kek.toString(), this));
    //
		// 	}
		// 	// k = curScope.probeValue(v.toString(), this);
		// //	//(k.toString());
		// // }



		return new RuntimeStringValue(kek);
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(str.get(counter));
		counter ++;
	}
}
