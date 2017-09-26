class AspFloatLiteral extends AspAtom{
	AspFloatLiteral(int n){
		super(n);
	}
	static AspFloatLiteral parse(Scanner s){
		AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
		Main.log.enterParser("float literal");
		Main.log.leaveParser("float literal");
		return afl;
	}
}
