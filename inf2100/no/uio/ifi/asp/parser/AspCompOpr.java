class AspCompOpr extends AspSyntax{
	AspCompOpr(int n){
		super(n)
	}
	static AspCompOpr parse(Scanner s) {
		AspCompOpr aco = new AspCompOpr(s.curLineNum());
		Main.log.enterParser("comp opr");
		Main.log.leaveParser("comp opr");
		return aco;
	}
}
