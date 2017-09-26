class AspNoneLiteral extends AspAtom{
	AspNoneLiteral(int n){
		super(n);
	}
	static AspNoneLiteral parse(Scanner s){
		AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
		if(s.curToken.kind == noneToken){
			Main.log.enterParser("none");
			Main.log.leaveParser("none");
			return anl;
		}else{
			Main.parserError("Not none token :(", s.curLineNum());
		}
	}
}
