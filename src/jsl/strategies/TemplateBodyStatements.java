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
public class TemplateBodyStatements extends Production {
   public TemplateBodyStatements(Output output) {
      super(output);
   }
   


   @Override
   public void execute(CharWrapper characters, ProductionContext context) throws Exception {
      if(characters.charAt(0) == open){
         //closing
         if(characters.charAt(1) == forward){
               context.removeProduction();
               return;
         } else {//assuming access to data here;
            characters.shift(1);
            Matcher name = characters.match(NAME);
            if(name.find()){
               String nm = name.group(1);
               characters.shift(nm.length());
               output.prepend("bld.append(data."+nm+");");
               characters.shift(1);//close block
               context.removeProduction();
               return;
            }
         }
      } else {
         Matcher inputTokens = characters.match(INPUT_TOKENS);
         if(inputTokens.find()){
            String oldTokens = inputTokens.group(1);
            String newTokens;
            if(context.stripNewLines){
               newTokens = oldTokens.replaceAll("\\n|\\r", "");
            } else {
               newTokens = oldTokens.replaceAll("\\n|\\r", "\\\\\n");

            }
            if(context.minifyHTML){
               newTokens = newTokens.replaceAll("(>|<)\\s++|\\s++(>|<)", "$1$2");
            }
            newTokens = newTokens.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'");
            characters.shift(oldTokens.length());
            output.prepend("bld.append('"+newTokens+"');");
            return;
         }
      }
      throw new Exception("Invalid Template");
   }
}