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

import com.xforj.*;
import java.util.regex.*;

/**
 *
 * @author Joseph Spencer
 */
public class ForeachStatement extends AbstractConditionBlock {
   public ForeachStatement(Output output) {
      super(output);
      output.
         prepend(js_foreach+"(").
            prepend(expressionOutput).
            prepend(",function("+js_context+","+js_position+","+js_last+"){").
         prepend(bodyOutput).
         prepend("});");
   }

   @Override
   protected Production getVariableExpression(Output output) {
      return new ContextSelector(output, false);
   }

   @Override
   protected Production getBodyStatements(Output output) {
      return new TemplateBodyStatements(output);
   }

   @Override
   protected Pattern getClosingPattern() {
      return FOREACH_CLOSING;
   }

}
