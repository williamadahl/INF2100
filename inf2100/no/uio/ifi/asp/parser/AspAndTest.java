package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspAndTest extends AspSyntax {
	ArrayList<AspNotTest> notTests = new ArrayList<>();

	AspAndTest(int n) {
		super(n);
	}

	public static AspAndTest parse(Scanner s) {
		Main.log.enterParser("and test");
		AspAndTest aat = new AspAndTest(s.curLineNum());

		while (true) {
			aat.notTests.add(AspNotTest.parse(s));
			if (s.curToken().kind != andToken) {
				break;
			}
			skip(s, andToken);
		}

		Main.log.leaveParser("and test");
		return aat;
	}

	@Override
	void prettyPrint() {
		int nPrinted = 0;
		for (AspNotTest ant: notTests) {
			if (nPrinted > 0){
				Main.log.prettyWrite(" and ");
			}
			ant.prettyPrint();
			++nPrinted;
		}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = notTests.get(0).eval(curScope);
		for(int i = 1 ; i < notTests.size(); ++i){
			if(! v.getBoolValue("and operator", this)){
				return v;
			}
			v = notTests.get(i).eval(curScope);
		}
		return v;
	}

}
