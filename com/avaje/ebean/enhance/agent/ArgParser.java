/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArgParser
/*    */ {
/*    */   public static HashMap<String, String> parse(String args)
/*    */   {
/* 15 */     HashMap<String, String> map = new HashMap();
/*    */     
/* 17 */     if (args != null) {
/* 18 */       String[] split = args.split(";");
/* 19 */       for (String nameValuePair : split) {
/* 20 */         String[] nameValue = nameValuePair.split("=");
/* 21 */         if (nameValue.length == 2) {
/* 22 */           map.put(nameValue[0].toLowerCase(), nameValue[1]);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 27 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\ArgParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */