package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

class AspPrimary extends AspSyntax{

	ArrayList<AspAtom> aa = new ArrayList<>();
	ArrayList<AspPrimarySuffix> aps = new ArrayList<>();

	AspPrimary(int n){
		super(n);
	}

	static AspPrimary parse(Scanner s){
		Main.log.enterParser("primary");
		AspPrimary ap = new AspPrimary(s.curLineNum());

		ap.aa.add(AspAtom.parse(s));

		while((s.curToken().kind == leftParToken) || (s.curToken().kind == leftBracketToken)){
			if(s.curToken().kind == leftBracketToken){
				ap.aps.add(AspSubscription.parse(s));
			}else{
				ap.aps.add(AspArguments.parse(s));
			}
		}

		Main.log.leaveParser("primary");
		return ap;
	}


	@Override
	void prettyPrint() {
		AspAtom hi = aa.get(0);
		hi.prettyPrint();

		for(AspPrimarySuffix a : aps ){
			a.prettyPrint();
		}
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeValue v = null;

		if(aps.size() != 0){
			v = aa.get(0).eval(curScope);
			for(int i = 0; i < aps.size(); i++){
				if(aps.get(i) instanceof AspSubscription){
					RuntimeValue vOriginal = aps.get(i).eval(curScope);
					RuntimeValue probern = curScope.probeValue(vOriginal.toString(), this);

					if(probern != null){
							v = v.evalSubscription(probern, this);
					}else{
						v = v.evalSubscription(vOriginal, this);
					}
				} else{
					/* maa nesten sjekke curScope*/
					//v = aps.get(i).eval(curScope);

					RuntimeValue x = aps.get(i).eval(curScope);
					RuntimeValue t = curScope.probeValue(v.toString(), this);



					// if the library method len is called in the program :
					if(t.toString().equals("\"len\"")){
						ArrayList<RuntimeValue>listOfValues = new ArrayList<>();
						RuntimeValue convertedValue = null;

						RuntimeListValue rtlv = (RuntimeListValue)x;
						RuntimeValue value = curScope.probeValue(rtlv.getElem(0).toString(), this);
						if(value == null){
							convertedValue = rtlv.getElem(0).evalLen(this);
							return convertedValue;
						}

						if(value instanceof RuntimeListValue){
							convertedValue = (RuntimeListValue)value;
						}else if(value instanceof RuntimeStringValue){
							convertedValue = (RuntimeStringValue)value;
						}else if(value instanceof RuntimeDictValue){
							convertedValue = (RuntimeDictValue)value;
						}else{
							Main.error("Illegal type for funtion len");
						}

						listOfValues.add(convertedValue);
						RuntimeIntValue len = (RuntimeIntValue)t.evalFuncCall(listOfValues, this);

						return len;
					}
					// if the library method int is called in the program :

					if(t.toString().equals("\"int\"")){
						ArrayList<RuntimeValue>listOfValues = new ArrayList<>();
						RuntimeValue convertedValue = null;

							RuntimeListValue rtlv = (RuntimeListValue)x;
							RuntimeValue value = curScope.probeValue(rtlv.getElem(0).toString(), this);

							if(value == null){

								Long temp;
								temp = rtlv.getElem(0).getIntValue("integer",this);
								convertedValue = new RuntimeIntValue(temp);
								return convertedValue;
							}


							if(value instanceof RuntimeIntValue){
								convertedValue = (RuntimeIntValue)value;
							}else if(value instanceof RuntimeStringValue){
								convertedValue = (RuntimeStringValue)value;
							}else if(value instanceof RuntimeFloatValue){
								convertedValue = (RuntimeFloatValue)value;
							}else{
								Main.error("Illegal type for funtion int");
							}

							listOfValues.add(convertedValue);
							RuntimeIntValue len = (RuntimeIntValue)t.evalFuncCall(listOfValues, this);

							return len;
					}

					// if the library method float is called in the program

					if(t.toString().equals("\"float\"")){
						ArrayList<RuntimeValue>listOfValues = new ArrayList<>();
						RuntimeValue convertedValue = null;

							RuntimeListValue rtlv = (RuntimeListValue)x;
							RuntimeValue value = curScope.probeValue(rtlv.getElem(0).toString(), this);

							if(value == null){

								double temp;
								temp = rtlv.getElem(0).getFloatValue("float",this);
								convertedValue = new RuntimeFloatValue(temp);
								return convertedValue;
							}


							if(value instanceof RuntimeIntValue){
								convertedValue = (RuntimeIntValue)value;
							}else if(value instanceof RuntimeStringValue){
								convertedValue = (RuntimeStringValue)value;
							}else if(value instanceof RuntimeFloatValue){
								convertedValue = (RuntimeFloatValue)value;
							}else{
								Main.error("Illegal type for funtion int");
							}

							listOfValues.add(convertedValue);
							RuntimeIntValue len = (RuntimeIntValue)t.evalFuncCall(listOfValues, this);

							return len;
					}
					// if the library method input is called in the program

					if (t.toString().equals("\"input\"")){

						RuntimeStringValue len = (RuntimeStringValue)t.evalFuncCall(null, this);
						return len;
					}

// if the library method str is called in the program
					if (t.toString().equals("\"str\"")){

						ArrayList<RuntimeValue>listOfValues = new ArrayList<>();
						RuntimeValue convertedValue = null;

							RuntimeListValue rtlv = (RuntimeListValue)x;
							RuntimeValue value = curScope.probeValue(rtlv.getElem(0).toString(), this);

							if(value == null){

								String temp;
								temp = rtlv.getElem(0).getStringValue("str",this);
								convertedValue = new RuntimeStringValue(temp);
								return convertedValue;
							}

							listOfValues.add(value);
							RuntimeStringValue len = (RuntimeStringValue)t.evalFuncCall(listOfValues, this);

							return len;

					}

					if (t.toString().equals("\"print\"")){

						ArrayList<RuntimeValue>listOfValues = new ArrayList<>();
						RuntimeValue convertedValue = null;

							RuntimeListValue rtlv = (RuntimeListValue)x;

							for (int j = 0; j<rtlv.getSize(); j++) {
								RuntimeValue value = curScope.probeValue(rtlv.getElem(j).toString(), this);
								if(value == null){
									String temp;
									temp = rtlv.getElem(j).getStringValue("str",this);
									convertedValue = new RuntimeStringValue(temp);
									listOfValues.add(convertedValue);
								} else{
										listOfValues.add(value);
								}
							}

							t.evalFuncCall(listOfValues, this);
							return new RuntimeNoneValue();
					}


					if(t != null){
						if((x instanceof RuntimeListValue) && (t instanceof RuntimeFunc)){

							RuntimeFunc newFunc = (RuntimeFunc)t;
							RuntimeListValue newList = (RuntimeListValue)x;

						} else{
							trace(v.showInfo() + " = " + t.showInfo());
							Main.error("Error, illegal call on no-function");
						}

					}else{
						Main.error("Error, function called not found ");
					}
				}
			}
			return v;
		}

		v = aa.get(0).eval(curScope);
		return v;
	}

}
