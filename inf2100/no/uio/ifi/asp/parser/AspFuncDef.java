package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFuncDef extends AspStmt{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
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
		while(true){
			afd.aname.add(AspName.parse(s));
			skip(s, nameToken);
			if(s.curToken().kind != commaToken){
				break;
			}
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
		void prettyPrint() {/*
			int nPrinted = 0;
			for (AspName an: aname) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" func def ");
				}
				an.prettyPrint();
				++nPrinted;
			}
			Main.log.prettyWrite(" func def ");
			pentHouse.prettyPrint();*/
		}
	}
