class AspStmt extends AspSyntax{

	AspAssingment body1;
	AspExpr body2;
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

		if(s.curToken().TokenKind == nameToken){
			if (s.anyEqualToken()){
				//now we know it is an assignment
					asmt.body1 = AspAssingment.parse(s);
			}else{
				// else it is an expression
					asmt.body2 = AspExpr.parse(s);
			}
		}else if(s.curToken().TokenKind == ifToken){
				asmt.body3 = AspIfStmt.parse(s);
		}else if(s.curToken().TokenKind == whileToken){
				asmt.body4 = AspWhileStmt.parse(s);
		}else if(s.curToken().TokenKind == returnToken){
				asmt.body5 = AspReturnStmt.parse(s);
		}else if(s.curToken().TokenKind == passToken){
				asmt.body6 = AspPassStmt.parse(s);
		}else{
				asmt.body7 = AspFuncDef.parse(s);
		}
		Main.log.leaveParser("stmt");

		return asmt;

	}

}
