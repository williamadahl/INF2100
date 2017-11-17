package no.uio.ifi.asp.runtime;


import java.util.ArrayList;
import java.util.Scanner;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
      //len

      assign("\"len\"", new RuntimeFunc("\"len\"") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
            checkNumParams(actualParams, 1, "len", where);
            return actualParams.get(0).evalLen(where);
        }});

        assign("\"int\"", new RuntimeFunc("\"int\"") {
          @Override
          public RuntimeValue evalFuncCall(
            ArrayList<RuntimeValue> actualParams,
            AspSyntax where) {
              checkNumParams(actualParams, 1, "int", where);
              RuntimeIntValue i = new RuntimeIntValue(actualParams.get(0).getIntValue("long",where));
              return i;
          }});

          assign("\"float\"", new RuntimeFunc("\"float\"") {
            @Override
            public RuntimeValue evalFuncCall(
              ArrayList<RuntimeValue> actualParams,
              AspSyntax where) {
                checkNumParams(actualParams, 1, "float", where);
                RuntimeFloatValue i = new RuntimeFloatValue(actualParams.get(0).getFloatValue("double",where));
                return i;
            }});

            assign("\"input\"", new RuntimeFunc("\"input\"") {
              @Override
              public RuntimeValue evalFuncCall(
                ArrayList<RuntimeValue> actualParams,
                AspSyntax where) {

                  RuntimeStringValue i = new RuntimeStringValue(keyboard.nextLine());
      
                  return i;
              }});

              assign("\"str\"", new RuntimeFunc("\"str\"") {
                @Override
                public RuntimeValue evalFuncCall(
                  ArrayList<RuntimeValue> actualParams,
                  AspSyntax where) {
                    checkNumParams(actualParams, 1, "str", where);
                    RuntimeStringValue i = new RuntimeStringValue(actualParams.get(0).getStringValue("str",where));
                    return i;
                }});

                assign("\"print\"", new RuntimeFunc("\"print\"") {
                  @Override
                  public RuntimeValue evalFuncCall(
                    ArrayList<RuntimeValue> actualParams,
                    AspSyntax where) {
                      for(RuntimeValue rv : actualParams){
                        System.out.print(rv.toString() + " ");
                      }
                      System.out.println();
                      return new RuntimeNoneValue();
                  }});





    }



    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
				int nCorrect, String id, AspSyntax where) {
	if (actArgs.size() != nCorrect)
	    RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
