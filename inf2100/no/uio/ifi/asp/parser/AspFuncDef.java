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
	//	System.out.println(ANSI_BLUE + "DETT ER I FUNCDEF: " + s.curToken().kind.toString() + ANSI_RESET);
		AspFuncDef afd = new AspFuncDef(s.curLineNum());
		skip(s, defToken);
		afd.aname.add(AspName.parse(s));
		skip(s, leftParToken);

		if(s.curToken().kind == rightParToken){
			skip(s,rightParToken);
			skip(s,colonToken);
			afd.pentHouse = AspSuite.parse(s);
			Main.log.leaveParser("func def");
			//System.out.println(ANSI_BLUE + "DETT ER I FUNCDEF LEAVE: " + s.curToken().kind.toString() + ANSI_RESET);

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
		//System.out.println(ANSI_BLUE + "DETT ER I FUNCDEF LEAVE: " + s.curToken().kind.toString() + ANSI_RESET);

		return afd;
	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


	@Override
		void prettyPrint() {
			int counter = 1;
			Main.log.prettyWrite(" def ");
			aname.get(0).prettyPrint();
			Main.log.prettyWrite(" ( ");

			if(!(aname.size() < 2)){
				int nPrinted = 0;
				for(int i = 1; i<aname.size(); i++){
					if(nPrinted > 0){
						Main.log.prettyWrite(" , ");
					}
					aname.get(i).prettyPrint();
					++nPrinted;
				}
			}
			Main.log.prettyWrite(" ) ");
			Main.log.prettyWrite(" : ");
			pentHouse.prettyPrint();
		}
	}
