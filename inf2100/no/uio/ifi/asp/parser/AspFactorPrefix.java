class AspFactorPrefix extends AspSyntax{
	AspFactorPrefix(int n){
		super(n)
	}

	static AspFactorPrefix parse(Scanner s){
		AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
		Main.log.enterParser("factor prefix");
		Main.log.leaveParser("factor prefix");
		return afp;

	}
}
