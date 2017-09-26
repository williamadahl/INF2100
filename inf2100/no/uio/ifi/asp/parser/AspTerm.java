import no.uio.ifi.asp.scanner.*;

class AspTerm extends AspSyntax{
	ArrayList<AspFactor> factorTests = new ArrayList<>();
	ArrayList<AspTermOpr> termOprTests = new ArrayList<>();

	AspTerm(int n){
		super(n);
	}

	static AspTerm parse(Scanner s) {
		Main.log.enterParser("term");
		AspTerm atat = new AspTerm(s.curLineNum());
		while (true) {
			atat.factorTests.add(AspFactor.parse(s));
			if(s.curToken().kind == plusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
				skip(s, plusToken);
			}else if(s.curToken().kind == minusToken){
				atat.termOprTests.add(AspTermOpr.parse(s));
				skip(s, minusToken);
			}else{
				break;
			}
		}
		Main.log.leaveParser("term");
		return atat;
	}
}
