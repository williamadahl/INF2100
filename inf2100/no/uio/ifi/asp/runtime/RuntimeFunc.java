package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
  AspFuncDef def;
  RuntimeScope defScope;
  String name;
  ArrayList <RuntimeValue> list = new ArrayList<>();


  RuntimeFunc(RuntimeValue rv, ArrayList<RuntimeValue> list, RuntimeScope rs){
    name = rv.showInfo();
    this.list = list;

  }

  boolean checkPara(RuntimeValue v){
    RuntimeListValue rtv = (RuntimeListValue)v;
    return (rtv.myLength() == list.size());
  }

  void evalFuncCall(ArrayList<RuntimeValue> argList, AspSyntax where, RuntimeScope rs){
    if(argList.size() == list.size()){
      defScope = new RuntimeScope(rs);
      for(int i = 0; i < list.size(); i ++){
        list.set(i, argList.get(i));
      }
      
    }
    else{
      Main.error("Error, actual and formal paramathers don't match");
    }

  }


  @Override
  protected String typeName() {
    return "function";
  }
}
