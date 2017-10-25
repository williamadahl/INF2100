package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspArguments extends AspPrimarySuffix{
	ArrayList<AspExpr> asex = new ArrayList<>();

	AspArguments(int n){
		super(n);
	}


	static AspArguments parse(Scanner s){
		Main.log.enterParser("arguments");
		AspArguments arar = new AspArguments(s.curLineNum());
		skip(s, leftParToken);

		if(s.curToken().kind == rightParToken){
			skip(s, rightParToken);
			Main.log.leaveParser("arguments");
			return arar;
		}

		while (true){
			arar.asex.add(AspExpr.parse(s));

			if(s.curToken().kind == rightParToken){
				skip(s, rightParToken);
				Main.log.leaveParser("arguments");
				return arar;
			}

			if(s.curToken().kind != commaToken){
				break;
			}
			skip(s, commaToken);
		}

		skip(s, rightParToken);
		Main.log.leaveParser("arguments");
		return arar;

	}

		@Override
		void prettyPrint() {
			Main.log.prettyWrite("(");
			int nPrinted = 0;
			for(AspExpr lol : asex){
				if(nPrinted > 0){
					Main.log.prettyWrite(", ");
				}
				lol.prettyPrint();
				++nPrinted;
			}
			Main.log.prettyWrite(")");
		}


		@Override
		RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
			System.out.println("KOMMER INN I ARGUMENTS");
			RuntimeValue v = asex.get(0).eval(curScope);

			for(int i = 1 ; i < asex.size(); ++i){
				v = asex.get(i).eval(curScope);
			}
			return v;
		}
}
