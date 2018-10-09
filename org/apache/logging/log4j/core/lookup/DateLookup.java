/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ @Plugin(name="date", category="Lookup")
/*    */ public class DateLookup
/*    */   implements StrLookup
/*    */ {
/* 34 */   private static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String lookup(String paramString)
/*    */   {
/* 42 */     return formatDate(System.currentTimeMillis(), paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String lookup(LogEvent paramLogEvent, String paramString)
/*    */   {
/* 53 */     return formatDate(paramLogEvent.getMillis(), paramString);
/*    */   }
/*    */   
/*    */   private String formatDate(long paramLong, String paramString) {
/* 57 */     Object localObject = null;
/* 58 */     if (paramString != null) {
/*    */       try {
/* 60 */         localObject = new SimpleDateFormat(paramString);
/*    */       } catch (Exception localException) {
/* 62 */         LOGGER.error("Invalid date format: \"" + paramString + "\", using default", localException);
/*    */       }
/*    */     }
/* 65 */     if (localObject == null) {
/* 66 */       localObject = DateFormat.getInstance();
/*    */     }
/* 68 */     return ((DateFormat)localObject).format(new Date(paramLong));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\DateLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */