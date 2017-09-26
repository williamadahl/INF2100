import static no.uio.ifi.asp.scanner.TokenKind.*;
abstract class AspAtom extends AspSyntax{
	AspAtom(int n) {
		super(n);
	}

	static AspAtom parse(Scanner s){
		AspAtom aat = new AspAtom(s.curLineNum());
		Main.log.enterParser("atom");
		switch (s.curToken().kind) {
			case falseToken:
			case trueToken:
			a = AspBooleanLiteral.parse(s);
			skip(s, s.curToken.kind); break;
			case floatToken:
			a = AspFloatLiteral.parse(s);
			skip(s, floatToken); break;
			case integerToken:
			a = AspIntegerLiteral.parse(s);
			skip(s, integerToken); break;
			case leftBraceToken:
			a = AspDictDisplay.parse(s);
			skip(s, leftBraceToken);  break;
			case leftBracketToken:
			a = AspListDisplay.parse(s);
			skip(s, leftBracketToken);  break;
			case leftParToken:
			a = AspInnerExpr.parse(s);
			skip(s, leftParToken);  break;
			case nameToken:
			a = AspName.parse(s);
			skip(s, nameToken);  break;
			case noneToken:
			a = AspNoneLiteral.parse(s);
			skip(s, noneToken);  break;
			case stringToken:
			a = AspStringLiteral.parse(s);
			skip(s, stringToken);  break;
			default:
			parserError("Expected an expression atom but found a " +
			s.curToken().kind + "!", s.curLineNum());
		}
		/*
		if(s.curToken().kind == nameToken){
				Main.log.enterParser("name");
				Main.log.leaveParser("name");
				skip(s, nameToken);
		}else if(s.curToken().kind == integerToken){
			Main.log.enterParser("integer literal");
			Main.log.leaveParser("integer literal");
			skip(s, integerToken);
		}else if(s.curToken().kind == floatToken){
			Main.log.enterParser("float literal");
			Main.log.leaveParser("float literal");
			skip(s, floatToken);
		}else if(s.curToken.kind == stringToken){
			Main.log.enterParser("string literal");
			Main.log.leaveParser("string literal");
			skip(s, stringToken);
		}else if((s.curToken.kind == falseToken) || (s.curToken.kind == falseToken) ){
			Main.log.enterParser("boolean literal");
			Main.log.leaveParser("boolean literal");
			skip(s, s.curToken.kind);
		}else if((s.curToken.kind == noneToken ){
			Main.log.enterParser("none literal");
			Main.log.leaveParser("none literal");
			skip(s, noneToken);
		}else if(s.curToken.toString().charAt(0) == '('){
			skip(s, leftParToken);
			aat.body1 = AspInnerExpr.parse(s);
		}else if(s.curToken.toString().charAt(0) == '['){
			skip(s, leftBracketToken);
			aat.body3 = AspListDisplay.parse(s);
		}else if(s.curToken.toString().charAt(0) == '{'){
			skip(s, leftBraceToken);
			aat.body2 = AspListDisplay.parse(s);
		}else{
			parserError("no elem ",s.curLineNum());
		}*/
		Main.log.leaveParser("atom");
		return aat;
	}

}
