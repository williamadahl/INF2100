package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspStmt extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";


	AspStmt(int n){
		super(n);
	}

	static AspStmt parse(Scanner s){
		AspStmt a = null;
		Main.log.enterParser("stmt");
		//System.out.println(ANSI_RED +"DETTE HER ER I STATEMENT: " + s.curToken().kind.toString()+ ANSI_RESET);

		switch (s.curToken().kind) {
			case nameToken:
			case integerToken:
			case stringToken:
			case minusToken:
			case floatToken:
			case notToken:
			case falseToken:
			case leftBracketToken:
			case leftBraceToken:
			if (s.anyEqualToken()){
				//now we know it is an assignment
					a = AspAssignment.parse(s);
			}else{
				// else it is an expression
					a = AspExprStmt.parse(s);
			}
			//System.out.println("REEEEEEEEEEEEEEEEEEE");
			//skip(s, nameToken);
			break;

			case ifToken:
				a = AspIfStmt.parse(s);
				// skip(s, ifToken);
				break;

			case whileToken:
				a = AspWhileStmt.parse(s);
				// skip(s, whileToken);
				break;

			case returnToken:
				a = AspReturnStmt.parse(s);
				// skip(s, returnToken);
				break;

			case passToken:
				a = AspPassStmt.parse(s);
				// skip(s, passToken);
				break;

			case defToken:
				a = AspFuncDef.parse(s);
				// skip(s, defToken);
				break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		//System.out.println("DETTE HER ER I STATEMENT2: " + s.curToken().kind.toString());

		Main.log.leaveParser("stmt");
		return a;
	}
/*
	static AspStmt parse(Scanner s) {

		System.out.println("DETTE HER ER I STMT: " + s.curToken().kind.toString());

		Main.log.enterParser("stmt");
		AspStmt asmt = null;

		if(s.curToken().kind == nameToken){
			if (s.anyEqualToken()){
				//now we know it is an assignment
					asmt.body1 = AspAssignment.parse(s);

			}else{
				// else it is an expression
					asmt.body2 = AspExprStmt.parse(s);
			}
		}else if(s.curToken().kind == ifToken){
				asmt.body3 = AspIfStmt.parse(s);
		}else if(s.curToken().kind == whileToken){
				asmt.body4 = AspWhileStmt.parse(s);
		}else if(s.curToken().kind == returnToken){
				asmt.body5 = AspReturnStmt.parse(s);
		}else if(s.curToken().kind == passToken){
				asmt.body6 = AspPassStmt.parse(s);
		}else if(s.curToken().kind == defToken) {
				asmt.body7 = AspFuncDef.parse(s);
		}
	System.out.println("DETTE HER ER I STMT2: " + s.curToken().kind.toString());
		Main.log.leaveParser("stmt");
		return asmt;

	}
*/
@Override
 void prettyPrint() {
	// System.out.println("{}");
	//
	// Main.log.prettyWrite(" Statement ");

	//a.prettyPrint();

}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}


}
