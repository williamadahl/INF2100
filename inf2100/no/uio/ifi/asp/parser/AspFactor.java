package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspFactor extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	ArrayList<AspFactorOpr> factorOprTests = new ArrayList<>();
	ArrayList<AspPrimary> primaryTests = new ArrayList<>();
	ArrayList<AspFactorPrefix> prefixTests = new ArrayList<>();

	AspFactor(int n){
		super(n);
	}

	static AspFactor parse(Scanner s) {
		AspFactor af = new AspFactor(s.curLineNum());
		Main.log.enterParser("factor");
	//	System.out.println("DETTE HER ER I FACTOR: " + s.curToken().kind.toString());

		//s.readNextToken();
		Token temp = s.curToken();
		if(temp.kind == plusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			while(true){
				af.primaryTests.add(AspPrimary.parse(s));
				if(s.curToken().kind == astToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, astToken);
				}else if(s.curToken().kind == slashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, slashToken);
				}else if(s.curToken().kind == percentToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, percentToken);
				}else if(s.curToken().kind == doubleSlashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, doubleSlashToken);
				}else {
					break;
				}
			}

			// skip(s, plusToken);
		}else if(temp.kind == minusToken){
			af.prefixTests.add(AspFactorPrefix.parse(s));
			// skip(s, minusToken);
			while(true){
				af.primaryTests.add(AspPrimary.parse(s));
				if(s.curToken().kind == astToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, astToken);
				}else if(s.curToken().kind == slashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, slashToken);
				}else if(s.curToken().kind == percentToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, percentToken);
				}else if(s.curToken().kind == doubleSlashToken){
					af.factorOprTests.add(AspFactorOpr.parse(s));
					//skip(s, doubleSlashToken);
				}else {
					break;
				}
			}
		}else{
				while(true){
					af.primaryTests.add(AspPrimary.parse(s));
					if(s.curToken().kind == astToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, astToken);
					}else if(s.curToken().kind == slashToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, slashToken);
					}else if(s.curToken().kind == percentToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, percentToken);
					}else if(s.curToken().kind == doubleSlashToken){
						af.factorOprTests.add(AspFactorOpr.parse(s));
						//skip(s, doubleSlashToken);
					}else {
						break;
					}
				}
		}
		Main.log.leaveParser("factor");
		return af;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {
			System.out.println("KOMMER INN I FACTORY");
			System.out.println("DETTE ER LENGIDEN AV prefixTests :" + prefixTests.size());
			for(AspFactorPrefix afp : prefixTests){
				afp.prettyPrint();
			}
			for(AspPrimary ap : primaryTests ){
				ap.prettyPrint();
				if(!factorOprTests.isEmpty()){
					factorOprTests.get(0).prettyPrint();
					factorOprTests.remove(0);
				}
			}
			// if(!prefixTests.isEmpty()){
			// 	System.out.println("PREFIX ER IKKE TOM OG VI KALLER ASPREFIX ");
			// 	prefixTests.get(0).prettyPrint();
			// }
			//
			// if(!factorOprTests.isEmpty()){
			// 	System.out.println("FACTOROPR ER IKKE TOM ");
			// 	int nPrinted = 0;
			// 	int i = 0;
			// 	for(AspPrimary ant : primaryTests){
			// 		if(nPrinted > 0){
			// 			System.out.println("SKAL KALLE PAA DENNE FACTOROPR SIN PRETTYPRINT: " + factorOprTests.get(i));
			// 			factorOprTests.get(i).prettyPrint();
			// 		}
			// 		System.out.println("SKAL KALLE PAA DENNE PRIMARY SIN TEST: " + ant);
			// 		ant.prettyPrint();
			// 		i++;
			// 		++nPrinted;
			// 	}
			// }else{
			// 	System.out.println("FACTOROPR VAR TOM! ");
			// 	for(AspPrimary ant : primaryTests){
			// 		System.out.println("KALLEP PAA DENNE PRIMARY TEST : " + ant);
			// 			ant.prettyPrint();
			// 		}
			// }
		}
}
