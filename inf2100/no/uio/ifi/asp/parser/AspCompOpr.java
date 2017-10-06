package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspCompOpr extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	AspCompOpr(int n){
		super(n);
	}
	String johnnyGuitar = "";

	static AspCompOpr parse(Scanner s) {
		AspCompOpr aco = new AspCompOpr(s.curLineNum());
		//System.out.println("DETTE HER ER I COMPOPR: " +s.curToken().kind.toString());

		Main.log.enterParser("comp opr");
		aco.johnnyGuitar = s.curToken().toString();
		skip(s, s.curToken().kind);
		Main.log.leaveParser("comp opr");
		return aco;
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope){
		return null;
	}

	@Override
	void prettyPrint() {
		System.out.println("INNE I AspSyntax, writer :" + johnnyGuitar);
		Main.log.prettyWrite(johnnyGuitar);

	}


}
