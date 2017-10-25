package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {

  long intValue;

  public RuntimeIntValue(long v) {
    intValue = v;
  }


  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("+ operand",where);
      res = new RuntimeIntValue(intValue + v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("+ operand",where);
      res = new RuntimeFloatValue(intValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("- operand",where);
      res = new RuntimeFloatValue(intValue - v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("- operand",where);
      res = new RuntimeIntValue(intValue - v2);
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
      res = new RuntimeFloatValue(intValue * v2);
    } else if (v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      res = new RuntimeIntValue(intValue * v2);
    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("// operand", where);
      res = new RuntimeFloatValue(Math.floor(intValue / v2));
    } else if (v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("// operand", where);
      res = new RuntimeIntValue(Math.floorDiv(intValue , v2));
    } else{
      runtimeError("Type error for //.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("== operand",where);
      res = new RuntimeBoolValue((intValue == v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("== operand",where);
      res = new RuntimeBoolValue((intValue == v2));
    }else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    }
    else{
     runtimeError("Type error for ==.", where);
    }
    return res;
  }


@Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("< operand",where);
      res = new RuntimeBoolValue((intValue < v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("< operand",where);
      res = new RuntimeBoolValue((intValue < v2));
    } else{
     runtimeError("Type error for <.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("/ operand",where);
      res = new RuntimeFloatValue(intValue / v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("/ operand",where);
      res = new RuntimeFloatValue(intValue / v2);
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
      res = new RuntimeFloatValue(intValue - v2 * Math.floor(intValue/v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("% operand",where);
      res = new RuntimeIntValue(Math.floorMod(intValue, v2));
    } else {
      runtimeError("Type error for %.", where);
    }
    return res;
  }


  @Override
  protected String typeName() {
    return "integer";
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return intValue;
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return (double)intValue;
  }
}
