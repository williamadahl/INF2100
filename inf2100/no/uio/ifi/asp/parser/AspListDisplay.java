package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspListDisplay extends AspAtom{
	AspExpr bod1;
	//TokenKind kind;

	ArrayList<AspExpr> boi = new ArrayList<>();

	AspListDisplay(int n){
		super(n);
	}

	static AspListDisplay parse(Scanner s){
		Main.log.enterParser("list display");
		AspListDisplay ald = new AspListDisplay(s.curLineNum());
		skip(s, leftBracketToken);

		if(s.curToken().kind == rightBracketToken){
			skip(s, rightBracketToken);
			Main.log.leaveParser("list display");
			return ald;
		}

		while (true){
			ald.boi.add(AspExpr.parse(s));
			if(s.curToken().kind == rightBracketToken){
				skip(s, rightBracketToken);
				Main.log.leaveParser("list display");
				return ald;
			}

			if(s.curToken().kind != commaToken){
				break;
			}
			skip(s, commaToken);
		}
		skip(s, rightBracketToken);
		Main.log.leaveParser("list display");
		return ald;
	}


	@Override
	void prettyPrint() {
		Main.log.prettyWrite(" [ ");
		int nPrinted = 0;

		for(AspExpr lol : boi){
			if(nPrinted > 0){
				Main.log.prettyWrite(" , ");
			}
			lol.prettyPrint();
			++nPrinted;
		}
		Main.log.prettyWrite(" ] ");
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		ArrayList<RuntimeValue> kjeks = new ArrayList<>();

		for(int i = 0; i < boi.size(); i++){
			kjeks.add(boi.get(i).eval(curScope));
		}

		RuntimeListValue k = new RuntimeListValue();
		for(RuntimeValue x : kjeks){
			k.addElem(x);
		}

		return k;
	}

}
