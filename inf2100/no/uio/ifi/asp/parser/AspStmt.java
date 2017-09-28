package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspStmt extends AspSyntax{

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
		AspStmt asmt = new AspStmt(s.curLineNum());

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

}
