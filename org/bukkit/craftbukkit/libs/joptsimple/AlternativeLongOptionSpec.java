/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class AlternativeLongOptionSpec
/*    */   extends ArgumentAcceptingOptionSpec<String>
/*    */ {
/*    */   AlternativeLongOptionSpec()
/*    */   {
/* 40 */     super(Collections.singletonList("W"), true, "Alternative form of long options");
/*    */     
/* 42 */     describedAs("opt=value");
/*    */   }
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions)
/*    */   {
/* 47 */     if (!arguments.hasMore()) {
/* 48 */       throw new OptionMissingRequiredArgumentException(options());
/*    */     }
/* 50 */     arguments.treatNextAsLongOption();
/*    */   }
/*    */   
/*    */   void accept(OptionSpecVisitor visitor)
/*    */   {
/* 55 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\AlternativeLongOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */