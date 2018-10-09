/*    */ package org.apache.commons.codec.language.bm;
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
/*    */ public enum NameType
/*    */ {
/* 31 */   ASHKENAZI("ash"), 
/*    */   
/*    */ 
/* 34 */   GENERIC("gen"), 
/*    */   
/*    */ 
/* 37 */   SEPHARDIC("sep");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private NameType(String paramString) {
/* 42 */     this.name = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 51 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\NameType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */