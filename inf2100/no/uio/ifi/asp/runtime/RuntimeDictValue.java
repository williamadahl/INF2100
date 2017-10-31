package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeDictValue extends RuntimeValue{

  ArrayList<RuntimeValue> key = new ArrayList<>();
  ArrayList<RuntimeValue> value = new ArrayList<>();

  public RuntimeDictValue (ArrayList<RuntimeValue> key, ArrayList<RuntimeValue> value){
    this.key = key;
    this.value = value;
  }

  @Override
  protected String typeName() {
    return "dict";
  }

  @Override
  public String showInfo() {
    String listString = "";

    if(key.size() == 0 || value.size() == 0){
      listString = "{" + listString;
      listString = listString + "}";

    }else{
      int i = 0;
      listString += "{";
      for(RuntimeValue r : key){
        listString += r.showInfo();
        listString += " : ";
        listString += value.get(i).showInfo();
        listString += " , ";
        i++;
      }
      listString = listString.substring(0, listString.length() - 2);
      listString += "}";
    }
    return listString;
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("[...] operand", where);

      int position = 0;

      for (RuntimeValue x : key) {
        if(x.getStringValue("String", where).equals(v2)){
          res = value.get(position);
          return res;
        }
        position ++;
      }
      runtimeError("Key not found.", where);
    }else{
      runtimeError("Type error for {...}.", where);
    }
    return res;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(key.isEmpty() || value.isEmpty()){
      return false;
    }else {
      return true;
    }
  }
}
