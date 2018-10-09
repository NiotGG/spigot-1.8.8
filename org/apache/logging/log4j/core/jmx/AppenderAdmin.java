/*    */ package org.apache.logging.log4j.core.jmx;
/*    */ 
/*    */ import javax.management.ObjectName;
/*    */ import org.apache.logging.log4j.core.Appender;
/*    */ import org.apache.logging.log4j.core.helpers.Assert;
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
/*    */ public class AppenderAdmin
/*    */   implements AppenderAdminMBean
/*    */ {
/*    */   private final String contextName;
/*    */   private final Appender appender;
/*    */   private final ObjectName objectName;
/*    */   
/*    */   public AppenderAdmin(String paramString, Appender paramAppender)
/*    */   {
/* 42 */     this.contextName = ((String)Assert.isNotNull(paramString, "contextName"));
/* 43 */     this.appender = ((Appender)Assert.isNotNull(paramAppender, "appender"));
/*    */     try {
/* 45 */       String str1 = Server.escape(this.contextName);
/* 46 */       String str2 = Server.escape(paramAppender.getName());
/* 47 */       String str3 = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", new Object[] { str1, str2 });
/* 48 */       this.objectName = new ObjectName(str3);
/*    */     } catch (Exception localException) {
/* 50 */       throw new IllegalStateException(localException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ObjectName getObjectName()
/*    */   {
/* 61 */     return this.objectName;
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 66 */     return this.appender.getName();
/*    */   }
/*    */   
/*    */   public String getLayout()
/*    */   {
/* 71 */     return String.valueOf(this.appender.getLayout());
/*    */   }
/*    */   
/*    */   public boolean isExceptionSuppressed()
/*    */   {
/* 76 */     return this.appender.ignoreExceptions();
/*    */   }
/*    */   
/*    */   public String getErrorHandler()
/*    */   {
/* 81 */     return String.valueOf(this.appender.getHandler());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\AppenderAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */