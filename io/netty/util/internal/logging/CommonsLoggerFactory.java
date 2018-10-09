/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.logging.LogFactory;
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
/*    */ public class CommonsLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/* 31 */   Map<String, InternalLogger> loggerMap = new HashMap();
/*    */   
/*    */   public InternalLogger newInstance(String paramString)
/*    */   {
/* 35 */     return new CommonsLogger(LogFactory.getLog(paramString), paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\CommonsLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */