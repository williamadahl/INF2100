class AspPrimarySuffix extends AspSyntax{
	AspSubscription as;
	AspArguments aa;

	AspPrimarySuffix(int n){
		super(n);
	}

	static AspPrimarySuffix parse(Scanner s){
		AspPrimarySuffix aps = new AspPrimarySuffix(s.curLineNum());

		Main.log.enterParser("primary suffix");
		if(s.curToken().kind == leftParToken){
				aps.aa = AspArguments.parse(s);
				skip(s, rightParToken);

		}else if(s.curToken().kind == leftBracketToken){
				aps.as = AspSubscription.parse(s);
				skip(s, rightBracketToken);
		}else{
			Main.parserError("No brackets", s.curLineNum());
		}
		Main.log.leaveParser("primary suffix");
		return aps;
	}
}
