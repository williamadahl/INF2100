package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
  String stringValue;


  public RuntimeString(String v) {
    stringValue = v;
  }


  @Override
  protected String typeName() {
    return "string";
  }



  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("+ operand", where);
      res = new RuntimeStringValue(stringValue.concat(v2));
    }else{
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      int v2 = v.getIntValue("* operand", where);
      res = new RuntimeStringValue(getStringValue(stringValue, v2));
    }else{
      runtimeError("Type error for *.", where);
    }
  }

  @Override
public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
  RunetimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue("== operand",where);
    res = new RunBoolValue((stringValue == v2));
  } else{
   runtimeError("Type error for ==.", where);
  }
  return res;
}

@Override
public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
RunetimeValue res = null;
if (v instanceof RuntimeStringValue){
  String v2 = v.getStringValue("< operand",where);
  res = new RunBoolValue((stringValue < v2));
} else{
 runtimeError("Type error for <.", where);
}
return res;
}


  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else {
      return new RuntimeBoolValue(
      boolValue == v.getBoolValue("== operand",where));
    }
  }

  @Override
  public String getStringValue(String what, AspSyntax where){
    return stringValue;
  }

  public String multiplyString(String s, int a){
    String result;

    for(int i = 0; i < a; i++){
      result += s;
    }
    return result;
  }




}
