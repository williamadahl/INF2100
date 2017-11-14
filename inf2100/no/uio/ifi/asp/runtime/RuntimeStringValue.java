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
  public String showInfo() {
    return '"' + stringValue + '"';

  }
  @Override
  public String toString() {
    return '"' + stringValue + '"';
  }

  @Override
    public RuntimeValue evalLen(AspSyntax where){
      RuntimeIntValue v = new RuntimeIntValue(stringValue.length());
      return v;
    }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("[...] operand", where);
      int v3 = (int)v2;
      res = new RuntimeStringValue(Character.toString(stringValue.charAt(v3)));
    }else{
      runtimeError("Type error for [...].", where);
    }
    return res;
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
      res = new RuntimeBoolValue((stringValue.equals(v2)));
    } else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    }  else{
     runtimeError("Type error for ==.", where);
   }
   return res;
 }

 @Override
 public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue("!= operand",where);
    res = new RuntimeBoolValue((!stringValue.equals(v2)));
  } else if (v instanceof RuntimeNoneValue) {
    return new RuntimeBoolValue(true);
  }  else{
    runtimeError("Type error for !=.", where);
  }
  return res;
}

@Override
public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){


System.out.println("kommer til STRING ? ");
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
@Override
public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue("> operand",where);
    System.out.println("Dette er i string : sammenlikner : " + stringValue+ " and " + v2);
    int temp = stringValue.compareTo(v2);
    if(temp > 0){
      res = new RuntimeBoolValue(true);
    } else{
      res = new RuntimeBoolValue(false);
    }
  } else{

    runtimeError("Type error for >.", where);
  }
  return res;
}

@Override
public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue(">= operand",where);
    int temp = stringValue.compareTo(v2);
    if(temp >= 0){
      res = new RuntimeBoolValue(true);
    } else{
      res = new RuntimeBoolValue(false);
    }
  } else{
    runtimeError("Type error for >=.", where);
  }
  return res;
}

@Override
public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeStringValue){
    String v2 = v.getStringValue("<= operand",where);
    int temp = stringValue.compareTo(v2);
    if(temp <= 0){
      res = new RuntimeBoolValue(true);
    } else{
      res = new RuntimeBoolValue(false);
    }
  } else{
    runtimeError("Type error for <.", where);
  }
  return res;
}

@Override
public RuntimeValue evalNot(AspSyntax where) {
  if(stringValue == ""){
    return new RuntimeBoolValue(true);
  }else{
    return new RuntimeBoolValue(false);
  }
}

@Override
public boolean getBoolValue(String what, AspSyntax where) {
  if(stringValue == ""){
    return false;
  }else{
    return true;
  }
}

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
