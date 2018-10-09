/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggerConfigAdmin
/*     */   implements LoggerConfigAdminMBean
/*     */ {
/*     */   private final String contextName;
/*     */   private final LoggerConfig loggerConfig;
/*     */   private final ObjectName objectName;
/*     */   
/*     */   public LoggerConfigAdmin(String paramString, LoggerConfig paramLoggerConfig)
/*     */   {
/*  45 */     this.contextName = ((String)Assert.isNotNull(paramString, "contextName"));
/*  46 */     this.loggerConfig = ((LoggerConfig)Assert.isNotNull(paramLoggerConfig, "loggerConfig"));
/*     */     try {
/*  48 */       String str1 = Server.escape(this.contextName);
/*  49 */       String str2 = Server.escape(paramLoggerConfig.getName());
/*  50 */       String str3 = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", new Object[] { str1, str2 });
/*  51 */       this.objectName = new ObjectName(str3);
/*     */     } catch (Exception localException) {
/*  53 */       throw new IllegalStateException(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectName getObjectName()
/*     */   {
/*  64 */     return this.objectName;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  69 */     return this.loggerConfig.getName();
/*     */   }
/*     */   
/*     */   public String getLevel()
/*     */   {
/*  74 */     return this.loggerConfig.getLevel().name();
/*     */   }
/*     */   
/*     */   public void setLevel(String paramString)
/*     */   {
/*  79 */     this.loggerConfig.setLevel(Level.valueOf(paramString));
/*     */   }
/*     */   
/*     */   public boolean isAdditive()
/*     */   {
/*  84 */     return this.loggerConfig.isAdditive();
/*     */   }
/*     */   
/*     */   public void setAdditive(boolean paramBoolean)
/*     */   {
/*  89 */     this.loggerConfig.setAdditive(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean isIncludeLocation()
/*     */   {
/*  94 */     return this.loggerConfig.isIncludeLocation();
/*     */   }
/*     */   
/*     */   public String getFilter()
/*     */   {
/*  99 */     return String.valueOf(this.loggerConfig.getFilter());
/*     */   }
/*     */   
/*     */   public String[] getAppenderRefs()
/*     */   {
/* 104 */     List localList = this.loggerConfig.getAppenderRefs();
/* 105 */     String[] arrayOfString = new String[localList.size()];
/* 106 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 107 */       arrayOfString[i] = ((AppenderRef)localList.get(i)).getRef();
/*     */     }
/* 109 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerConfigAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */