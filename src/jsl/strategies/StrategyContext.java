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

import java.util.ArrayList;
import jsl.*;
import jsl.Output;

/**
 *
 * @author Joseph Spencer
 */
public class StrategyContext {
   private Strategy currentStrategy;
   private ArrayList<Strategy> strategyStack = new ArrayList<>();

   public final Output output = new Output().prepend("(function(){").append("})();");

   private boolean JSTLNameSpaceDeclared;

   private String NS;
   private String FullNS;
   public final String filePath;

   public StrategyContext(String absoluteFilePath) {
      currentStrategy= new Program(output);
      strategyStack.add(currentStrategy);
      filePath=absoluteFilePath;
   }

   public StrategyContext addNS(String NS) throws Exception{
      if(!JSTLNameSpaceDeclared){
         String[] split = NS.split("\\.");
         String newNS = "JSL";
         String builtNS = "";
         int len = split.length;
         this.NS = NS;
         this.FullNS = newNS + "." + NS;

         output.prepend("var currentNS;try{JSL}catch(e){JSL={}}");
         for(int i=0;i<len;i++){
            builtNS += "."+split[i];
            String tempNS = newNS + builtNS;
            output.prepend("if(!"+tempNS+")"+
               ((i==(len-1))?"currentNS=":"")+
               tempNS+"={};"
            );
         }
      } else {
         throw new Exception("Namespace already declared for this template");
      }
      return this;
   }
   public String getNS(){
      return FullNS;
   }

   //STRATEGY
   public StrategyContext addStrategy(Strategy add){
      strategyStack.add(add);
      currentStrategy=add;
      return this;
   }
   public StrategyContext removeStrategy(){
      strategyStack.remove(strategyStack.size()-1);
      currentStrategy=strategyStack.get(strategyStack.size()-1);
      return this;
   }
   public StrategyContext executeCurrent(CharWrapper wrap) throws Exception{
      currentStrategy.execute(wrap, this);
      return this;
   }

   //OUTPUT
   public StrategyContext addOutput(Output out){

      return this;
   }
   public StrategyContext removeOutput(Output out){

      return this;
   }
}
