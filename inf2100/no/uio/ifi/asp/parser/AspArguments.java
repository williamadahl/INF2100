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
	while(true){
		Token temp = s.curToken();
		if(testToken(s, rightParToken)){
			Main.log.leaveParser("arguments");
			skip(s, rightParToken);
			return arar;
		}else if(testToken(s, newLineToken)){
			parserError("Expected a " + rightBracketToken + " but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}else{
			while(true){
				arar.asex.add(AspExpr.parse(s));
				s.readNextToken();
				if(s.curToken().kind != commaToken){
					break;
				}
			}
		}
		}
	}
	@Override
		public RuntimeValue eval(RuntimeScope curScope){
			return null;
		}

		@Override
		void prettyPrint() {
			int nPrinted = 0;
			for (AspExpr ae: asex) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" arguments ");
				}
				ae.prettyPrint();
				++nPrinted;
			}
		}
}
