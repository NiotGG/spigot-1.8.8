/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParserMap
/*    */   extends DeployParser
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   
/*    */   public DeployPropertyParserMap(Map<String, String> map)
/*    */   {
/* 14 */     this.map = map;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Set<String> getIncludes()
/*    */   {
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public String convertWord() {
/* 25 */     String r = getDeployWord(this.word);
/* 26 */     return r == null ? this.word : r;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDeployWord(String expression)
/*    */   {
/* 32 */     String deployExpr = (String)this.map.get(expression);
/* 33 */     if (deployExpr == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     return deployExpr;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParserMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */