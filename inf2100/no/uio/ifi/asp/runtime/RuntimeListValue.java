package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeListValue extends RuntimeValue{

  ArrayList<RuntimeValue> aspList = new ArrayList<>();

  public RuntimeListValue (){

  }

  public RuntimeListValue (ArrayList<RuntimeValue> v){
    aspList = v;
  }

  public void addElem(RuntimeValue v){
    aspList.add(v);
  }

  @Override
  protected String typeName() {
    return "list";
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("[...] operand", where);
      int v3 = (int)v2;
      res = aspList.get(v3);
    }else{
      runtimeError("Type error for [...].", where);
    }
    return res;
  }


  @Override
  public String showInfo() {
    String listString = "";
    for(RuntimeValue r : aspList){
      listString += r.showInfo();
      listString += ", ";
    }
    if(aspList.size() == 0){
      listString = "[" + listString;
      listString = listString + "]";
    }
    listString = listString.substring(0,(listString.length()-2));
    listString = "[" + listString;
    listString = listString + "]";
    return listString;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      res = new RuntimeListValue(multiplyList(aspList, v2));

    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }


  public ArrayList multiplyList(ArrayList v, long d){
    ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>();
    temp.addAll(v);
    for(int i = 0; i < d-1; i++){
      v.addAll(temp);
    }
    return v;
  }

}
