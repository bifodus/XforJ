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

import com.xforj.Output;
import com.xforj.CharWrapper;
import java.util.regex.*;

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
      Matcher match;
      Output statementOutput;

      Matcher spacePrecedingCurly = characters.match(SPACE_PRECEDING_CURLY);
      if(spacePrecedingCurly.find()){
         characters.shift(spacePrecedingCurly.group(1).length());
      }

      if(characters.charAt(0) == '{'){
         switch(characters.charAt(1)){
         case '/':
            context.removeProduction();
            return;
         case 'v':
            match = characters.match(VAR);
            if(match.find()){
               throw new Exception("VariableDeclarations are not allowed in this context.");
            }
            break;
         case 'p':
            match = characters.match(PARAM);
            if(match.find()){
               throw new Exception("ParamDeclarations are not allowed in this context.");
            }
            break;

         case 'i':
            match = characters.match(IF);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput=new Output();
               context.addProduction(new IfStatement(statementOutput));
               output.add(statementOutput);
               return;
            }
            break;
         case 'l':
            match = characters.match(LOG);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput=new Output();
               context.addProduction(new LogStatement(statementOutput, context));
               output.add(statementOutput);
               return;
            }
            break;
         case 'c':
            match = characters.match(CHOOSE);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput= new Output();
               context.addProduction(new ChooseStatement(statementOutput));
               output.add(statementOutput);
               return;
            }
            match = characters.match(CALL);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput= new Output();
               context.addProduction(new CallStatement(statementOutput));
               output.add(statementOutput);
               return;
            }

            break;
         case 'f':
            match = characters.match(FOREACH);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput= new Output();
               context.addProduction(new ForeachStatement(statementOutput, context));
               output.add(statementOutput);
               return;
            }
            break;
         case 't':
            match = characters.match(TEXT);
            if(match.find()){
               characters.shift(match.group(1).length());
               statementOutput= new Output();
               context.addProduction(new TextStatement(statementOutput));
               output.add(statementOutput);
               return;
            }
            break;
         }

         //PrintStatement
         statementOutput=new Output();
         output.add(statementOutput);
         context.addProduction(new PrintStatement(statementOutput));
      } else {
         //InputTokens
         statementOutput=new Output();
         output.add(statementOutput);
         context.addProduction(new InputTokens(statementOutput));
      }
   }
}
