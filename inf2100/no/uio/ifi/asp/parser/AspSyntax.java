package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public abstract class AspSyntax {
    public int lineNum;

    AspSyntax(int n) {
	lineNum = n;
    }


    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;


    static void parserError(String message, int lineNum) {
	String m = "Asp parser error";
	if (lineNum > 0) m += " on line " + lineNum;
	m += ": " + message;
	Main.error(m);
    }


    public static void test(Scanner s, TokenKind tk) {
	if (s.curToken().kind != tk)
	    parserError("Expected a " + tk + " but found a " + 
			s.curToken().kind + "!", s.curLineNum());
    }


    public static void test(Scanner s, TokenKind tk1, TokenKind tk2) {
	if (s.curToken().kind!=tk1 && s.curToken().kind!=tk2)
	    parserError("Expected a " + tk1 + " or a " + tk2 + " but found a " + 
			s.curToken().kind + "!", s.curLineNum());
    }


    public static void skip(Scanner s, TokenKind tk) {
	test(s, tk);
	s.readNextToken();
    }


    void trace(String what) {
	Main.log.traceEval(what, this);
    }
}
