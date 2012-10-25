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

package jsl.strategies;

import java.util.regex.*;
import jsl.*;

/**
 *
 * @author Joseph Spencer
 */
public class PrintStatement extends Production {
   public PrintStatement(Output output) {
      super(output);
   }
   
   private boolean hasOpenCurly;
   @Override
   void execute(CharWrapper characters, ProductionContext context) throws Exception {
      Output variableAssignmentOutput;
      switch(characters.charAt(0)){
      case ocurly:
         if(!hasOpenCurly){
            hasOpenCurly=true;
            variableAssignmentOutput=new Output();
            characters.shift(1);
            output.
               prepend(js_bld+".append(").
               prepend(variableAssignmentOutput).
               append(");");
            context.addProduction(new VariableExpression(variableAssignmentOutput));
            return;
         }
         break;
      case ccurly:
         if(hasOpenCurly){
            characters.shift(1);
            context.removeProduction();
            return;
         }
         break;
      }
      throw new Exception("Invalid PrintStatement.");
   }

}