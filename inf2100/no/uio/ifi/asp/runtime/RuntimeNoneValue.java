package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeNoneValue extends RuntimeValue {



    @Override
    protected String typeName() {
	     return "None";
    }


    @Override
    public String toString() {
	return "None";
    }


    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
	return false;
    }


    @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
    RunetimeValue res = null;
    switch (v) {
      case v instanceof RuntimeIntValue:
      long v2 = v.getIntValue("== operand",where);
      break;
      case v instanceof RuntimeFloatValue:
      double v2 = v.getFloatValue("== operand",where);
      break;
      case v instanceof RuntimeIntValue:
      String v2 = v.getStringValue("== operand",where);
      break;
      default:
      runtimeError("Type error for ==.",where);

    }
    res = new RuntimeNoneValue(None == v2);
    return res;



    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	return new RuntimeBoolValue(true);
    }


    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
	return new RuntimeBoolValue(!(v instanceof RuntimeNoneValue));
    }
}
