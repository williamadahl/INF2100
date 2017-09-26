import no.uio.ifi.asp.scanner.*;

class AspComparison extends AspSyntax{
	ArrayList<AspTerm> termTests = new ArrayList<>();
	ArrayList<AspCompOpr> compOprTests = new ArrayList<>();

	//char[] compOperators = new char[]{ '<', '>', '==', '>=', '<=', '!=' };

	AspComparison(int n){
		super(n);
	}

	static AspComparison parse(Scanner s) {
		AspComparison acp = new AspComparison(s.curLineNum());

		Main.log.enterParser("comparison");
		while (true) {
			acp.termTests.add(AspTerm.parse(s));

			if(s.curToken().kind == lessToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, lessToken);
			}else if(s.curToken().kind == greaterToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, greaterToken);
			}else if(s.curToken().kind == doubleEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, doubleEqualToken);
			}else if(s.curToken().kind == greaterEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, greaterEqualToken);
			}else if(s.curToken().kind == lessEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, lessEqualToken);
			}else if(s.curToken().kind == notEqualToken){
				acp.compOprTests.add(AspCompOpr.parse(s));
				skip(s, notEqualToken);
			}else{
				break;
			}
		}
		Main.log.leaveParser("comparison");
		return nut;
	}
}
