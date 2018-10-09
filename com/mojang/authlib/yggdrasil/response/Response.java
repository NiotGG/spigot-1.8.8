/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ public class Response {
/*    */   private String error;
/*    */   private String errorMessage;
/*    */   private String cause;
/*    */   
/*    */   public String getError() {
/*  9 */     return this.error;
/*    */   }
/*    */   
/*    */   public String getCause() {
/* 13 */     return this.cause;
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 17 */     return this.errorMessage;
/*    */   }
/*    */   
/*    */   protected void setError(String paramString) {
/* 21 */     this.error = paramString;
/*    */   }
/*    */   
/*    */   protected void setErrorMessage(String paramString) {
/* 25 */     this.errorMessage = paramString;
/*    */   }
/*    */   
/*    */   protected void setCause(String paramString) {
/* 29 */     this.cause = paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\response\Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */