/*
 * Copyright 2012 Joseph Spencer
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
package jsl.strategies;

import java.util.regex.Matcher;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class Program extends Production implements Characters {
   private Output output;
   public Program(Output output){
      super(output);
      this.output=output;
      output.
         prepend("(function(){").
         append("function StringBuffer(){var v=[],i=0;this.append=function(s){v[i++]=s||'';};this.toString=function(){return v.join('');};}})();");
   }

   private boolean hasJSLNamespace;
   private boolean hasTemplates;

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      String exception = "The opening of JSL templtes must be a JSL declaration.";

      if(characters.charAt(0) == open && !hasJSLNamespace){
         hasJSLNamespace=true;
         context.addProduction(new ProgramNamespace(output));
         return;
      } else if(hasJSLNamespace){
         characters.removeSpace();
         if(characters.charAt(0) == open){
            if(!hasTemplates){
               if(characters.charAt(1) == i){
                  context.addProduction(new ImportStatements(output));
                  return;
               }
            }
            context.addProduction(new GlobalStatements(output));
            return;
         }
      }
      throw new Exception(exception);
   }
}
