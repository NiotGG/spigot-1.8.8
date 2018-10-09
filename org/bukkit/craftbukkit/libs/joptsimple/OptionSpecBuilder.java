/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ public class OptionSpecBuilder
/*    */   extends NoArgumentOptionSpec
/*    */ {
/*    */   private final OptionParser parser;
/*    */   
/*    */   OptionSpecBuilder(OptionParser parser, Collection<String> options, String description)
/*    */   {
/* 65 */     super(options, description);
/*    */     
/* 67 */     this.parser = parser;
/* 68 */     attachToParser();
/*    */   }
/*    */   
/*    */   private void attachToParser() {
/* 72 */     this.parser.recognize(this);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArgumentAcceptingOptionSpec<String> withRequiredArg()
/*    */   {
/* 81 */     ArgumentAcceptingOptionSpec<String> newSpec = new RequiredArgumentOptionSpec(options(), description());
/*    */     
/* 83 */     this.parser.recognize(newSpec);
/*    */     
/* 85 */     return newSpec;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArgumentAcceptingOptionSpec<String> withOptionalArg()
/*    */   {
/* 95 */     ArgumentAcceptingOptionSpec<String> newSpec = new OptionalArgumentOptionSpec(options(), description());
/*    */     
/* 97 */     this.parser.recognize(newSpec);
/*    */     
/* 99 */     return newSpec;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSpecBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */