/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class OptionParserState
/*    */ {
/*    */   static OptionParserState noMoreOptions()
/*    */   {
/* 39 */     new OptionParserState()
/*    */     {
/*    */       protected void handleArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 42 */         detectedOptions.addNonOptionArgument(arguments.next());
/*    */       }
/*    */     };
/*    */   }
/*    */   
/*    */   static OptionParserState moreOptions(boolean posixlyCorrect) {
/* 48 */     new OptionParserState()
/*    */     {
/*    */       protected void handleArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 51 */         String candidate = arguments.next();
/* 52 */         if (ParserRules.isOptionTerminator(candidate)) {
/* 53 */           parser.noMoreOptions();
/* 54 */         } else if (ParserRules.isLongOptionToken(candidate)) {
/* 55 */           parser.handleLongOptionToken(candidate, arguments, detectedOptions);
/* 56 */         } else if (ParserRules.isShortOptionToken(candidate)) {
/* 57 */           parser.handleShortOptionToken(candidate, arguments, detectedOptions);
/*    */         } else {
/* 59 */           if (this.val$posixlyCorrect) {
/* 60 */             parser.noMoreOptions();
/*    */           }
/* 62 */           detectedOptions.addNonOptionArgument(candidate);
/*    */         }
/*    */       }
/*    */     };
/*    */   }
/*    */   
/*    */   protected abstract void handleArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionParserState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */