package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
  String stringValue;


  public RuntimeStringValue(String v) {
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
      long v2 = v.getIntValue("* operand", where);
      res = new RuntimeStringValue(multiplyString(stringValue, v2));
    }else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }

  @Override
public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue("== operand",where);
    res = new RuntimeBoolValue((stringValue == v2));
  } else if (v instanceof RuntimeNoneValue) {
    return new RuntimeBoolValue(false);
  }  else{
   runtimeError("Type error for ==.", where);
  }
  return res;
}

@Override
public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
RuntimeValue res = null;
if (v instanceof RuntimeStringValue){
  String v2 = v.getStringValue("< operand",where);
  int temp = stringValue.compareTo(v2);
  if(temp < 0){
    res = new RuntimeBoolValue(true);
  } else{
    res = new RuntimeBoolValue(false);
  }
} else{
 runtimeError("Type error for <.", where);
}
return res;
}


  // @Override
  // public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
  //     // return new RuntimeBoolValue(boolValue == v.getBoolValue("== operand",where));
  // }


  @Override
  public String getStringValue(String what, AspSyntax where){
    return stringValue;
  }

  public String multiplyString(String s, long a){
    String result = "";

    for(int i = 0; i < a; i++){
      result += s;
    }
    return result;
  }




}
