import no.uio.ifi.asp.scanner.*;

class AspFactor extends AspSyntax{
	ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
	ArrayList<AspPrimary> primaryTests = new ArrayList<>();
	ArrayList<AspFactorPrefix> prefixTests = new ArrayList<>();

	AspFactor(int n){
		super(n)
	}

	static AspFactor parse(Scanner s) {
		Main.log.enterParser("factor");
		AspFactor af = new AspFactor(s.curLineNum());

		Token temp = s.getNextToken();
		if(temp.kind == plusToken){
			prefixTests.add(AspFactorPrefix.parse(s))
			skip(s, plusToken);
		}else if(temp.kind == minusToken){
			prefixTests.add(AspFactorPrefix.parse(s))
			skip(s, minusToken);
		}else{
				while(true){
					primaryTests.add(AspPrimary.parse(s));
					if(s.curToken().kind == astToken){
						factorOprTests.add(AspFactorOpr.parse(s));
						skip(s, astToken);
					}else if(s.curToken().kind == slashToken){
						factorOprTests.add(AspFactorOpr.parse(s));
						skip(s, slashToken);
					}else if(s.curToken().kind == percentToken){
						factorOprTests.add(AspFactorOpr.parse(s));
						skip(s, percentToken);
					}else if(s.curToken.kind == doubleSlashToken){
						factorOprTests.add(AspFactorOpr.parse(s));
						skip(s, doubleSlashToken);
					}else {
						break;
					}
				}
		}
		Main.log.leaveParser("factor");
		return af;
	}
}
