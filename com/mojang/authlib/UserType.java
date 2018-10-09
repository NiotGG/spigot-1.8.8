/*    */ package com.mojang.authlib;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum UserType
/*    */ {
/*  7 */   LEGACY("legacy"), 
/*  8 */   MOJANG("mojang");
/*    */   
/*    */   private static final Map<String, UserType> BY_NAME;
/*    */   private final String name;
/*    */   
/*    */   private UserType(String paramString) {
/* 14 */     this.name = paramString;
/*    */   }
/*    */   
/*    */   public static UserType byName(String paramString) {
/* 18 */     return (UserType)BY_NAME.get(paramString.toLowerCase());
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 10 */     BY_NAME = new java.util.HashMap();
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
/* 26 */     for (UserType localUserType : values()) {
/* 27 */       BY_NAME.put(localUserType.name, localUserType);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\UserType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */