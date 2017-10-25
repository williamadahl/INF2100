package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFuncDef extends AspStmt{
	ArrayList<AspName> aname = new ArrayList<>();
	AspSuite pentHouse;

	AspFuncDef(int n){
  	      super(n);
  }
	static AspFuncDef parse(Scanner s){
		Main.log.enterParser("func def");
		AspFuncDef afd = new AspFuncDef(s.curLineNum());
		skip(s, defToken);
		afd.aname.add(AspName.parse(s));
		skip(s, leftParToken);

		if(s.curToken().kind == rightParToken){
			skip(s,rightParToken);
			skip(s,colonToken);
			afd.pentHouse = AspSuite.parse(s);
			Main.log.leaveParser("func def");
			return afd;
		}

		while(true){
			afd.aname.add(AspName.parse(s));
			if(s.curToken().kind != commaToken){
				break;
			}
			skip(s,commaToken);
		}
		skip(s,rightParToken);
		skip(s,colonToken);
		afd.pentHouse = AspSuite.parse(s);
		Main.log.leaveParser("func def");

		return afd;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint() {
			Main.log.prettyWrite("def ");
			AspName kek = aname.get(0);

			kek.prettyPrint();
			Main.log.prettyWrite(" (");

			int nPrinted = 0;

			for (int i = 1;i<aname.size();i++) {
				if(nPrinted > 0){
					Main.log.prettyWrite(", ");
				}
				aname.get(i).prettyPrint();
				++nPrinted;
			}

			Main.log.prettyWrite(")");
			Main.log.prettyWrite(":");
			pentHouse.prettyPrint();
			Main.log.prettyWriteLn();
		}
	}
