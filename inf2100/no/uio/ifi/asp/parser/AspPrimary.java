class AspPrimary extends AspSyntax{
	AspAtom aa;

	AspPrimary(int n){
		super(n)
	}

	static AspPrimary parse(Scanner s){
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);
		Main.log.leaveParser("primary");
	}
}
