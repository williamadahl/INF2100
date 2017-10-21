package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {

  long intValue;

  public RuntimeFloatValue(long v) {
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
      res = new RuntimeFloatValue((long)(intValue + v2));
    } else {
      runtimeError("Type error for +.", where);
    }
    return res;
  }

  @Override
  protected String typeName() {
    return "float";
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
