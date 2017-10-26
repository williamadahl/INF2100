package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeListValue extends RuntimeValue{

  ArrayList<RuntimeValue> aspList;

  public RuntimeListValue (ArrayList<RuntimeValue> v){
    aspList = v;
  }

  @Override
  protected String typeName() {
    return "list";
  }

  @Override
  public String showInfo() {
    String listString = "";
    for(RuntimeValue r : aspList){
      listString += r.showInfo();
      listString += ", ";
    }
    listString = listString.substring(0,(listString.length()-2));
    listString = "[" + listString;
    listString = listString + "]";
    System.out.println(listString);
    return listString;

    //usikker på om dette funker
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      System.out.println("er jeg he");
      res = new RuntimeListValue(multiplyList(aspList, v2));
    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }


  public ArrayList multiplyList(ArrayList v, long d){
    ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>();
    temp.addAll(v);
    System.out.println("temp");

    for(int i = 0; i < d-1; i++){
      v.addAll(temp);
    }
    //showInfo();
    return v;
  }

}
