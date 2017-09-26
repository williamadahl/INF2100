class AspStringLiteral extends AspAtom{
	AspStringLiteral(int n){
		super(n);
	}

	static AspStringLiteral parse(Scanner s){
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		Main.log.enterParser("string literal");
		Main.log.leaveParser("string literal");
		return asl;
	}
}
