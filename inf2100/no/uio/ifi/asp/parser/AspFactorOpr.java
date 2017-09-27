class AspFactorOpr extends AspSyntax{
	AspFactorOpr(int n){
		super(n);
	}

	static AspFactorOpr parse(Scanner s){
		AspFactorOpr noe = new AspFactorOpr(s.curLineNum());
		Main.log.enterParser("Factor opr");
		Main.log.leaveParser("Factor opr");
		return noe;
	}

}
