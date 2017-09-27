class AspFuncDef extends AspStmt{
	ArrayList<AspName> aname = new ArrayList<>();
	AspSuite pentHouse;

	AspFuncDef(int n){
  	      super(n);
  }
	static AspFuncDef parse(Scanner s){
		Main.log.enterParser("func def");
		AspFuncDef afd = new AspFuncDef(s.curLineNum());
		skip(s, defToken);
		afd.aname.add(AspName.parse(s));
		skip(s, leftParToken);
		while(true){
			afd.aname.add(AspName.parse(s));
			skip(s, nameToken);
			if(s.curToken().kind != commaToken){
				break;
			}
		}
		skip(s,rightParToken);
		skip(s,commaToken);
		afd.pentHouse = AspSuite.parse(s);
		Main.log.leaveParser("func def");
		return afd;
	}
}
