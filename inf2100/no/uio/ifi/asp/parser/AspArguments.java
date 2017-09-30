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
		System.out.println("DETTE HER ER I ARGUMENTS 1: " + s.curToken().kind.toString());

		Main.log.enterParser("arguments");
		AspArguments arar = new AspArguments(s.curLineNum());
		skip(s, leftParToken);
	while(true){
		Token temp = s.curToken();
		System.out.println("HER HAR DU TEMP: " + temp.kind.toString());
		if(testToken(s, rightParToken)){
			Main.log.leaveParser("arguments");
			skip(s, rightParToken);
			System.out.println("DETTE HER ER I ARGUMENTS 2: " + s.curToken().kind.toString());
			return arar;
		}else if(testToken(s, newLineToken)){
			Main.log.leaveParser("arguments");
			return arar;
			// FORTSETT HERFRA AXAXAXAX
			//
			//parserError("Expected a " + rightBracketToken + " but found a " +
			//s.curToken().kind + "!", s.curLineNum());
		}else{
			while(true){
				arar.asex.add(AspExpr.parse(s));
				s.readNextToken();
				if(s.curToken().kind != commaToken){
					break;
				}
			}
		}
		// return arar;
		}

	}


	@Override
		public RuntimeValue eval(RuntimeScope curScope){
			return null;
		}

		@Override
		void prettyPrint() {/*
			int nPrinted = 0;
			for (AspExpr ae: asex) {
				if (nPrinted > 0){
					Main.log.prettyWrite(" arguments ");
				}
				ae.prettyPrint();
				++nPrinted;
			}*/
		}
}
