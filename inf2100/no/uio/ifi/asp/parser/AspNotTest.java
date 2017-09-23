class AspNotTest extends AspSyntax{
	AspComparison body1;

	AspNotTest(int n){
		super(n)
	}

	static AspNotTest parse(Scanner s) {
		AspNotTest nut = new AspNotTest(s.curLineNum());
		Main.log.enterParser("not test");
		Token temp = s.getNextToken();
		if(temp.kind == notToken){
			skip(s, notToken);
			nut.body1 = AspComparison.parse(s);
		}else{
			nut.body1 = AspComparison.parse(s);
		}
		Main.log.leaveParser("not test");
		return nut;
	}
}
