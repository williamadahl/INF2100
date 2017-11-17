package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


class AspDictDisplay extends AspAtom{
	ArrayList<AspStringLiteral> atl = new ArrayList<>();
	ArrayList<AspExpr> ae = new ArrayList<>();
	int counter = 0;

	AspDictDisplay(int n){
		super(n);
	}

	static AspDictDisplay parse(Scanner s){
		Main.log.enterParser("dict display");
		AspDictDisplay add = new AspDictDisplay(s.curLineNum());
		skip(s, leftBraceToken);

		Token temp = s.curToken();

		while(true){
			if(testToken(s, rightBraceToken)){
				Main.log.leaveParser("dict display");
				skip(s, s.curToken().kind);
				Main.log.leaveParser("dict display");
				return add;
			}else{
				add.atl.add(AspStringLiteral.parse(s));
				skip(s, colonToken);
				add.ae.add(AspExpr.parse(s));
				if(s.curToken().kind != commaToken){
					break;
				}
				s.readNextToken();
			}
		}
		skip(s, rightBraceToken);
		Main.log.leaveParser("dict display");
		return add;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope)throws RuntimeReturnValue {
		ArrayList<RuntimeValue> kjeks = new ArrayList<>();
		ArrayList<RuntimeValue> kaffe = new ArrayList<>();
		RuntimeDictValue k = null;

		if(atl.size() != ae.size()){
			System.exit(0);
		}else{
			for(int i = 0; i < atl.size(); i++){
				kjeks.add(atl.get(i).eval(curScope));
				kaffe.add(ae.get(i).eval(curScope));
			}
			k = new RuntimeDictValue(kjeks, kaffe);
		}
		return k;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyWrite(" { ");
		int nPrinted = 0;

		for(AspStringLiteral lit : atl){
			lit.prettyPrint();
			Main.log.prettyWrite(" : ");

			ae.get(counter).prettyPrint();
			counter++;
			if(counter < atl.size()){
				Main.log.prettyWrite(" , ");
			}
		}
		Main.log.prettyWrite(" } ");
	}
}
