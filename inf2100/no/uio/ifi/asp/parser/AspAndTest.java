class AspAndTest extends AspSyntax {
	ArrayList<AspNotTest> notTests = new ArrayList<>();

	AspAndTest(int n) {
		super(n);
	}

	static AspAndTest parse(Scanner s) {
		Main.log.enterParser("and test");
		AspAndTest aat = new AspAndTest(s.curLineNum());
		while (true) {
			aat.notTests.add(AspNotTest.parse(s));
			if (s.curToken().kind != andToken) break;
			// wtf vil ikke man skippe dobbelt naar man evnt skipper i notTests
			skip(s, andToken);
		}

		Main.log.leaveParser("and test");
		return aat;
	}
}
