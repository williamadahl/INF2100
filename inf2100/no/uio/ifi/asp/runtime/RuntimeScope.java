package no.uio.ifi.asp.runtime;

// For part 4:

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeScope {
    RuntimeScope outer;
    HashMap<String,RuntimeValue> decls = new HashMap<>();

    public RuntimeScope() {
	outer = null;
    }


    public RuntimeScope(RuntimeScope oScope) {
	     outer = oScope;
    }


    public void assign(String id, RuntimeValue val) {
      /*
      * Check if an element in the map exists, if it does, remove it for the
      * new value
      */
      System.out.println("assigner id : " +  id + " til velue: " + val.toString() );
      RuntimeValue v = decls.get(id);
      if(v == null){
        System.out.println("ingen verdi opprettet");
        decls.put(id, val);
      } else{
        System.out.println("fant en gammel verdi");
        decls.replace(id,val);
      }
    }


    public RuntimeValue probeValue(String id, AspSyntax where){
      RuntimeValue v = decls.get(id);


      if (v != null)
          return v;
      if (outer != null)
          return outer.probeValue(id, where);

      return null;
    }

    public RuntimeValue find(String id, AspSyntax where) {
	RuntimeValue v = decls.get(id);


	if (v != null)
	    return v;
	if (outer != null)
	    return outer.find(id, where);

	RuntimeValue.runtimeError("Name " + id + " not defined!", where);
	return null;  // Required by the compiler.
}

public void printScope(){
  for(String r  : decls.keySet()){
    System.out.println("Strng : " +  r);
    System.out.println("value : " + decls.get(r));

  }
}



}
