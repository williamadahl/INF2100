class AspTermOpr extends AspSyntax{
	AspTermOpr(int n){
		super(n)
	}
	static AspTermOpr parse(Scanner s) {
		AspTermOpr ato = new AspTermOpr(s.curLineNum());
		Main.log.enterParser("term opr");
		Main.log.leaveParser("term opr");
		return ato;
	}
}
