class AspAssignment extends AspStmt{
	AspName test;


	AspAssignment(int n){
		super(n);
	}

	static AspAssignment parse(Scanner s){

		Main.log.enterParser("assignment");
		AspAssignment asss = new AspAssignment(s.curLineNum());
		// We know it is a nameToken so we parse it to that class
		asss.test = AspName.parse(s);
		Main.log.exitParser("assignment");



	}
}
