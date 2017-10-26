package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeListValue extends RuntimeValue{

  ArrayList<Object> aspList;

  public RuntimeListValue (ArrayList<Object> v){
    aspList = v;
  }

  @Override
  protected String typeName() {
    return "list";
  }

  @Override
  public String showInfo() {
    String listString = "";
    for(Object o : aspList){
      listString += o.toString();
      listString += ", ";
    }
    listString = listString.substring(0,(listString.length()-2));
    listString = "[" + listString;
    listString = listString + "]";
    return listString;

    //usikker på om dette funker
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
    ArrayList<Object> temp = new ArrayList<Object>();
    temp.addAll(v);

    for(int i = 0; i < d; i++){
      v.addAll(temp);
    }
    return v;
  }

}
