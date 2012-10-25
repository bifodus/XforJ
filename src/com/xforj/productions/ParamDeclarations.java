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

package com.xforj.productions;

import com.xforj.CharWrapper;
import com.xforj.VariableOutput;
import java.util.regex.Matcher;

/**
 *
 * @author Joseph Spencer
 */
public class ParamDeclarations extends Production {
   VariableOutput variableOutput;
   public ParamDeclarations(VariableOutput variableOutput) {
      super(variableOutput);
      this.variableOutput=variableOutput;
   }

   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      characters.removeSpace();
      if(characters.charAt(0) == open && characters.charAt(1) == p){
         Matcher param = characters.match(PARAM);
         if(param.find()){
            context.addProduction(new ParamDeclaration(variableOutput));
            return;
         }
      }
      context.removeProduction();
   }

}