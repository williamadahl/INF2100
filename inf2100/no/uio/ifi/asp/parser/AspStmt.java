package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

abstract class AspStmt extends AspSyntax{

	AspAssignment body1;
	AspExprStmt body2;
	AspIfStmt body3;
	AspWhileStmt body4;
	AspReturnStmt body5;
	AspPassStmt body6;
	AspFuncDef body7;


	AspStmt(int n){
		super(n);
	}

	static AspStmt parse(Scanner s) {


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
		}else{
				asmt.body7 = AspFuncDef.parse(s);
		}
		Main.log.leaveParser("stmt");

		return asmt;

	}
	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}
	/*@Override
		void prettyPrint() {

			Main.log.prettyWrite(" assignment ");
			body1.prettyPrint();
			Main.log.prettyWrite(" expr ");
			body2.prettyPrint();
			Main.log.prettyWrite(" if ");
			body3.prettyPrint();
			Main.log.prettyWrite(" while ");
			body4.prettyPrint();
			Main.log.prettyWrite(" return ");
			body5.prettyPrint();
			Main.log.prettyWrite(" pass ");
			body6.prettyPrint();
			Main.log.prettyWrite(" func ");
			body7.prettyPrint();

		}*/


}
