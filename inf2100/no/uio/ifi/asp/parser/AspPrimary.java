package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPrimary extends AspSyntax{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	//AspAtom aa;
	ArrayList<AspAtom> aa = new ArrayList<>();
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();
	// static int counter = 0;
	//static int counter = 0;

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		//System.out.println("DETTE HER ER I PRIMARY: " + s.curToken().kind.toString());
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());
		//ap.aa = AspAtom.parse(s);
		ap.aa.add(AspAtom.parse(s));
		//System.out.println("DETTE HER ER I PRIMARY 2: " + s.curToken().kind.toString());
		//skip(s, s.curToken().kind);

		while((s.curToken().kind == leftParToken) || (s.curToken().kind == leftBracketToken)){
				ap.aps.add(AspPrimarySuffix.parse(s));
				// counter++;
				//System.out.println("DETTE HER ER I PRIMARY 3: " + s.curToken().kind.toString());
				//s.readNextToken();
		}

		Main.log.leaveParser("primary");
		return ap;
	}

	@Override
		RuntimeValue eval(RuntimeScope curScope) {
			return null;
		}

		@Override
		void prettyPrint() {

			AspAtom hi = aa.get(0);
			aa.remove(0);
			hi.prettyPrint();


			//aa.prettyPrint();

			/*
			if(!aps.isEmpty()){
				System.out.println(ANSI_BLUE + " VI HAR PRIMARYSUFFIX I PRIMARY: " + ANSI_RESET);
				AspPrimarySuffix kek = aps.get(0);
				aps.remove(0);
				kek.prettyPrint();
			*/

				for(AspPrimarySuffix a : aps ){
					System.out.println(ANSI_BLUE + " VI HAR PRIMARYSUFFIX I PRIMARY: " + ANSI_RESET);
					a.prettyPrint();
				}

			}

			//System.out.println("HER SKAL VI SJEKKE BS");
			// if(!(aps.isEmpty())){
			// 	System.out.println("PRIMARY SUFFIX OPPFYLLES");
			// 	//aps.get(counter).prettyPrint();
			// 	//counter++;
			// 	for(AspPrimarySuffix kek : aps){
			// 		kek.prettyPrint();
			// 	}
			// }
		// 	if(counter != 0){
		// 		for(AspPrimarySuffix kek : aps){
		//
		// 			System.out.println("KALLER PAA DENNE PRIMARYSUFFIX SIN PRETTYPRINT : " + kek);
		// 			kek.prettyPrint();
		// 		}
		// 	// }
		// 	System.out.println("Ikke flere PrimarySuffix aa printe");
		// 	//System.out.println("BØR OPPFYLLES");
		// }
}
