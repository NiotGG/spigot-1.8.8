/*    */ package org.apache.logging.log4j.core;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.message.Message;
/*    */ import org.apache.logging.log4j.util.EnglishEnums;
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
/*    */ public abstract interface Filter
/*    */ {
/*    */   public abstract Result getOnMismatch();
/*    */   
/*    */   public abstract Result getOnMatch();
/*    */   
/*    */   public abstract Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs);
/*    */   
/*    */   public abstract Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable);
/*    */   
/*    */   public abstract Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable);
/*    */   
/*    */   public abstract Result filter(LogEvent paramLogEvent);
/*    */   
/*    */   public static enum Result
/*    */   {
/* 42 */     ACCEPT, 
/*    */     
/*    */ 
/*    */ 
/* 46 */     NEUTRAL, 
/*    */     
/*    */ 
/*    */ 
/* 50 */     DENY;
/*    */     
/*    */ 
/*    */ 
/*    */     private Result() {}
/*    */     
/*    */ 
/*    */     public static Result toResult(String paramString)
/*    */     {
/* 59 */       return toResult(paramString, null);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     public static Result toResult(String paramString, Result paramResult)
/*    */     {
/* 70 */       return (Result)EnglishEnums.valueOf(Result.class, paramString, paramResult);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\Filter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */