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

  public ArrayList<RuntimeValue> getList(){
    return aspList;
  }

  public RuntimeValue getElem(int pos){
    return aspList.get(pos);

  }

  @Override
  protected String typeName() {
    return "list";
  }

@Override
  public RuntimeValue evalLen(AspSyntax where){
    RuntimeIntValue v = new RuntimeIntValue(aspList.size());

    return v;
  }

public int getSize(){
  return aspList.size();
}


  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("[...] operand", where);
      int v3 = (int)v2;

      if(v3 > aspList.size()-1){
        Main.error("Array size is " + aspList.size() + " but you're trying to get element at position " + v3);
      }else{
        res = aspList.get(v3);
      }
    }else{
      runtimeError("Type error for [...].", where);
    }
    return res;
  }


  @Override
  public String getStringValue(String what, AspSyntax where) {
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
  public String toString() {
    String listString = "";
    for(RuntimeValue r : aspList){
      listString += r.toString();
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

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(aspList.isEmpty()){
      return false;
    }else {
      return true;
    }
  }

@Override
  public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
System.out.println("Hei fro RuntimeListValue");
    long v2 = inx.getIntValue("[...] operand", where);
    int v3 = (int)v2;

    aspList.remove(v3);
    aspList.add(v3, val);
  }

  public int myLength(){
    return aspList.size();
  }


}
