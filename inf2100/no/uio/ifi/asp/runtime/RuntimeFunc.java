package no.uio.ifi.asp.runtime;



import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
  AspFuncDef def;
  RuntimeScope defScope;
  String name;
  ArrayList <RuntimeValue> list = new ArrayList<>();


  public RuntimeFunc(RuntimeValue rv, ArrayList<RuntimeValue> list, RuntimeScope rp, AspFuncDef def){
    name = rv.showInfo();
    this.list = list;
    defScope = rp;
    this.def = def;
  }

  public  RuntimeFunc(String s ){
    name = s;
  }

  boolean checkPara(RuntimeValue v){
    RuntimeListValue rtv = (RuntimeListValue)v;
    return (rtv.myLength() == list.size());
  }

  @Override
  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> argList, AspSyntax where){
    RuntimeValue hi = null;
    if(argList.size() == list.size()){
      RuntimeScope rss = new RuntimeScope(defScope);
      for(int i = 0; i < list.size(); i ++){
        System.out.println("Her har du list sin elem: " + list.get(i).toString());
        RuntimeValue recValue = null;
        recValue = defScope.probeValue(argList.get(i).toString(), def);
        if(recValue != null){
          rss.assign(list.get(i).toString(), recValue);
        }else {
          System.out.println("Her har du argList sin elem: " + argList.get(i).toString());
          rss.assign(list.get(i).toString(), argList.get(i));
        }

      } try{
        hi = def.runFunction(rss);
      } catch(RuntimeReturnValue rrv){
        return rrv.value;
      }
      //hi = def.runFunction(defScope);

    }
    else{
      Main.error("Error, actual and formal paramathers don't match");
    }
    return hi;
  }



  @Override
  public String toString(){
    return name;
  }

  @Override
  protected String typeName() {
    return "function";
  }

  RuntimeScope returnScope(){
    return defScope;
  }
}
