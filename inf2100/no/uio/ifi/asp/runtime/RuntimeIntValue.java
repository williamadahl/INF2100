package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {

  long intValue;

  public RuntimeIntValue(long v) {
    intValue = v;
  }

  @Override
  public String toString() {
    return Long.toString(intValue);
  }

  @Override
  public String showInfo() {
    return Long.toString(intValue);
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {


    RuntimeValue res = null;
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("+ operand",where);
      res = new RuntimeIntValue(intValue + v2);
      System.out.println("hello boys, im in add, I will attemt to add these numbers  : " + intValue + " and " + v2 + " result:  " + res);

    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("+ operand",where);
      res = new RuntimeFloatValue(intValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where) {
    return new RuntimeIntValue(intValue);
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where) {
    return new RuntimeIntValue((intValue * -1));
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
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("!= operand",where);
      res = new RuntimeBoolValue((intValue != v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("!= operand",where);
      res = new RuntimeBoolValue((intValue != v2));
    }else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(true);
    }
    else{
      runtimeError("Type error for !=.", where);
    }
    return res;
  }


@Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
    System.out.println("kommer til INT ? ");
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
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("<= operand",where);
      res = new RuntimeBoolValue((intValue <= v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("<= operand",where);
      res = new RuntimeBoolValue((intValue <= v2));
    } else{
      runtimeError("Type error for <=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;
    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue(">= operand",where);
      res = new RuntimeBoolValue((intValue >= v2));
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue(">= operand",where);
      res = new RuntimeBoolValue((intValue >= v2));
    } else{
      runtimeError("Type error for >=.", where);
    }
    return res;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
    RuntimeValue res = null;

    if (v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("> operand",where);
      res = new RuntimeBoolValue((intValue > v2));
    } else if (v instanceof RuntimeIntValue) {
        long v2 = v.getIntValue("> operand",where);
      res = new RuntimeBoolValue((intValue > v2));
    } else{
      runtimeError("Type error for >.", where);
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
  public boolean getBoolValue(String what, AspSyntax where){
    if(intValue == 0){
      return false;
    }else {
      return true;
    }
  }


  @Override
public RuntimeValue evalNot(AspSyntax where) {
  if(intValue == 0){
    return new RuntimeBoolValue(true);
  }else{
    return new RuntimeBoolValue(false);
  }
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
