package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

  double floatValue;

  public RuntimeFloatValue(double v) {
    floatValue = v;
  }

  @Override
  public String showInfo() {
    return Double.toString(floatValue);
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where) {
    return new RuntimeFloatValue(floatValue);
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where) {
    return new RuntimeFloatValue((floatValue * -1.0));
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("+ operand",where);
      res = new RuntimeFloatValue(floatValue + v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("+ operand",where);
      res = new RuntimeFloatValue(floatValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeFloatValue){
    double v2 = v.getFloatValue("< operand",where);
    res = new RuntimeBoolValue((floatValue < v2));
  } else if (v instanceof RuntimeIntValue) {
    long v2 = v.getIntValue("< operand",where);
    res = new RuntimeBoolValue((floatValue < v2));
  } else{
   runtimeError("Type error for <.", where);
  }
  return res;
}

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("<= operand",where);
      res = new RuntimeBoolValue((floatValue <= v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("<= operand",where);
      res = new RuntimeBoolValue((floatValue <= v2));
    } else{
      runtimeError("Type error for <=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("> operand",where);
      res = new RuntimeBoolValue((floatValue > v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("> operand",where);
      res = new RuntimeBoolValue((floatValue > v2));
    } else{
      runtimeError("Type error for >.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue(">= operand",where);
      res = new RuntimeBoolValue((floatValue >= v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue(">= operand",where);
      res = new RuntimeBoolValue((floatValue >= v2));
    } else{
      runtimeError("Type error for >=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("- operand",where);
      res = new RuntimeFloatValue(floatValue - v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("- operand",where);
      res = new RuntimeFloatValue(floatValue - v2);
    } else {
      runtimeError("Type error for -.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("* operand", where);
      res = new RuntimeFloatValue(floatValue * v2);
    } else if (v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* oprenad", where);
      res = new RuntimeFloatValue(floatValue * v2);
    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }

  @Override
public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
  RuntimeValue res = null;
  if (v instanceof RuntimeFloatValue){
    double v2 = v.getFloatValue("== operand",where);
    res = new RuntimeBoolValue((floatValue == v2));
  } else if (v instanceof RuntimeIntValue) {
    long v2 = v.getIntValue("== operand",where);
    res = new RuntimeBoolValue((floatValue == v2));
  }else if (v instanceof RuntimeNoneValue) {
    return new RuntimeBoolValue(false);
  }
  else{
   runtimeError("Type error for ==.", where);
  }
  return res;
}

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("!= operand",where);
      res = new RuntimeBoolValue((floatValue != v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("!= operand",where);
      res = new RuntimeBoolValue((floatValue != v2));
    }else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    }
    else{
      runtimeError("Type error for !=.", where);
    }
    return res;
  }

@Override
public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
  RuntimeValue res = null;
  if (v instanceof RuntimeFloatValue) {
    double v2 = v.getFloatValue("/ operand",where);
    res = new RuntimeFloatValue(floatValue / v2);
  } else if (v instanceof RuntimeIntValue) {
    long v2 = v.getIntValue("/ operand",where);
    res = new RuntimeFloatValue(floatValue / v2);
  } else {
    runtimeError("Type error for /.", where);
  }
  return res;
}


@Override
public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
  RuntimeValue res = null;
  if (v instanceof RuntimeFloatValue) {
    double v2 = v.getFloatValue("% operand",where);
    res = new RuntimeFloatValue(floatValue - v2 * Math.floor(floatValue/v2));
  } else if (v instanceof RuntimeIntValue) {
    long v2 = v.getIntValue("% operand",where);
    res = new RuntimeFloatValue(floatValue - v2 * Math.floor(floatValue / v2));
  } else {
    runtimeError("Type error for %.", where);
  }
  return res;
}

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("// operand", where);
      res = new RuntimeFloatValue(Math.floor(floatValue / v2));
    } else if (v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("// opernad", where);
      res = new RuntimeFloatValue(Math.floor(floatValue / v2));
    } else{
      runtimeError("Type error for //.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    if(floatValue == 0.0){
      return new RuntimeBoolValue(true);
    }else{
      return new RuntimeBoolValue(false);
    }
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if(floatValue == 0.0){
      return false;
    }else{
      return true;
    }
  }

  @Override
  protected String typeName() {
    return "float";
  }



  @Override
  public long getIntValue(String what, AspSyntax where) {
    return (long)floatValue;
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }
}
