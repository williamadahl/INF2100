class AspPrimary extends AspSyntax{
	AspAtom aa;
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);
		skip(s, s.curToken.Kind);

		while(true){
			ap.aps.add(AspPrimarySuffix.parse(s));
			if(s.curToken().Kind != leftParToken ||
					s.curToken().Kind != leftBracketToken){
						break;
					}
			s.readNextToken();
		}




		Main.log.leaveParser("primary");
	}
}
