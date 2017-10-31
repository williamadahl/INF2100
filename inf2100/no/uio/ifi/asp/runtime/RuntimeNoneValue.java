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
    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("== operand",where);

    }else if(v instanceof RuntimeFloatValue){
      double v2 = v.getFloatValue("== operand",where);

    }else if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("== operand",where);

    }else if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(true);

    }else{
      runtimeError("Type error for ==.",where);
    }
    return new RuntimeBoolValue(false);
}

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        RuntimeValue res = null;
        if(v instanceof RuntimeIntValue){
            long v2 = v.getIntValue("!= operand",where);

        }else if(v instanceof RuntimeFloatValue){
            double v2 = v.getFloatValue("!= operand",where);

        }else if(v instanceof RuntimeStringValue){
            String v2 = v.getStringValue("!= operand",where);

        }else if(v instanceof RuntimeNoneValue){
            //res = new RuntimeNoneValue(None == v2);
            return new RuntimeBoolValue(false);

        }else{
            runtimeError("Type error for !=.",where);
        }
        return new RuntimeBoolValue(true);
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	return new RuntimeBoolValue(true);
    }
}
