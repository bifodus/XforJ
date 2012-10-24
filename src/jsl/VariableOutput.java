/*
 * Copyright 2012 Joseph Spencer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joseph Spencer
 */
public class VariableOutput extends Output {
   private VariableOutput parentScope;
   protected Map<String, Output> variables = new HashMap<String, Output>();
   protected ArrayList<String> keys = new ArrayList<>();

   public VariableOutput() {}

   public VariableOutput(VariableOutput parentScope) {
      this.parentScope = parentScope;
   }

   public VariableOutput add(String name, Output value) throws Exception {
      if(variables.containsKey("__"+name)){
         throw new Exception("The following variable has been declared twice: "+name);
      }
      if(value == null){
         throw new Exception("Null value was discovered for the following variable: \""+name+"\"");
      }
      variables.put("__"+name, value);
      keys.add("__"+name);
      return this;
   }

   public boolean hasVariableBeenDeclared(String name){
      if(null == parentScope){
         if(variables.containsKey("__"+name)){
            return true;
         } else {
            return false;
         }
      }
      return parentScope.hasVariableBeenDeclared("__"+name);
   }

   public boolean lastVariableNameEquals(String name){
      int size = keys.size();
      if(size > 0){
         String lastName = keys.get(size-1);
         return lastName.equals("__"+name);
      }
      return false;
   }

   @Override
   public String toString(){
      if(keys.size() > 0){
         String first = keys.remove(0);
         prepend("var "+first+"="+variables.get(first).toString());
         for(String key : keys){
            prepend(","+key+"="+variables.get(key).toString());
         }
         prepend(";");
         keys.clear();
         variables.clear();
      }
      return super.toString();
   }

}
