/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Server
/*     */ {
/*     */   private static final String PROPERTY_DISABLE_JMX = "log4j2.disable.jmx";
/*     */   
/*     */   public static String escape(String paramString)
/*     */   {
/*  65 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length() * 2);
/*  66 */     int i = 0;
/*  67 */     for (int j = 0; j < paramString.length(); j++) {
/*  68 */       char c = paramString.charAt(j);
/*  69 */       switch (c) {
/*     */       case '*': 
/*     */       case ',': 
/*     */       case ':': 
/*     */       case '=': 
/*     */       case '?': 
/*     */       case '\\': 
/*  76 */         localStringBuilder.append('\\');
/*  77 */         i = 1;
/*     */       }
/*  79 */       localStringBuilder.append(c);
/*     */     }
/*  81 */     if (i != 0) {
/*  82 */       localStringBuilder.insert(0, '"');
/*  83 */       localStringBuilder.append('"');
/*     */     }
/*  85 */     return localStringBuilder.toString();
/*     */   }
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
/*     */   public static void registerMBeans(ContextSelector paramContextSelector)
/*     */     throws JMException
/*     */   {
/* 102 */     if (Boolean.getBoolean("log4j2.disable.jmx")) {
/* 103 */       StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
/*     */       
/* 105 */       return;
/*     */     }
/* 107 */     MBeanServer localMBeanServer = ManagementFactory.getPlatformMBeanServer();
/* 108 */     registerMBeans(paramContextSelector, localMBeanServer);
/*     */   }
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
/*     */   public static void registerMBeans(ContextSelector paramContextSelector, final MBeanServer paramMBeanServer)
/*     */     throws JMException
/*     */   {
/* 126 */     if (Boolean.getBoolean("log4j2.disable.jmx")) {
/* 127 */       StatusLogger.getLogger().debug("JMX disabled for log4j2. Not registering MBeans.");
/*     */       
/* 129 */       return;
/*     */     }
/* 131 */     final ExecutorService localExecutorService = Executors.newFixedThreadPool(1);
/* 132 */     registerStatusLogger(paramMBeanServer, localExecutorService);
/* 133 */     registerContextSelector(paramContextSelector, paramMBeanServer, localExecutorService);
/*     */     
/* 135 */     List localList = paramContextSelector.getLoggerContexts();
/* 136 */     registerContexts(localList, paramMBeanServer, localExecutorService);
/*     */     
/* 138 */     for (LoggerContext localLoggerContext : localList) {
/* 139 */       localLoggerContext.addPropertyChangeListener(new PropertyChangeListener()
/*     */       {
/*     */         public void propertyChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent)
/*     */         {
/* 143 */           if (!"config".equals(paramAnonymousPropertyChangeEvent.getPropertyName()))
/*     */           {
/* 145 */             return;
/*     */           }
/*     */           
/*     */ 
/* 149 */           Server.unregisterLoggerConfigs(this.val$context, paramMBeanServer);
/* 150 */           Server.unregisterAppenders(this.val$context, paramMBeanServer);
/*     */           
/*     */ 
/*     */           try
/*     */           {
/* 155 */             Server.registerLoggerConfigs(this.val$context, paramMBeanServer, localExecutorService);
/* 156 */             Server.registerAppenders(this.val$context, paramMBeanServer, localExecutorService);
/*     */           } catch (Exception localException) {
/* 158 */             StatusLogger.getLogger().error("Could not register mbeans", localException);
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void registerStatusLogger(MBeanServer paramMBeanServer, Executor paramExecutor)
/*     */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */   {
/* 169 */     StatusLoggerAdmin localStatusLoggerAdmin = new StatusLoggerAdmin(paramExecutor);
/* 170 */     paramMBeanServer.registerMBean(localStatusLoggerAdmin, localStatusLoggerAdmin.getObjectName());
/*     */   }
/*     */   
/*     */ 
/*     */   private static void registerContextSelector(ContextSelector paramContextSelector, MBeanServer paramMBeanServer, Executor paramExecutor)
/*     */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */   {
/* 177 */     ContextSelectorAdmin localContextSelectorAdmin = new ContextSelectorAdmin(paramContextSelector);
/* 178 */     paramMBeanServer.registerMBean(localContextSelectorAdmin, localContextSelectorAdmin.getObjectName());
/*     */   }
/*     */   
/*     */ 
/*     */   private static void registerContexts(List<LoggerContext> paramList, MBeanServer paramMBeanServer, Executor paramExecutor)
/*     */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */   {
/* 185 */     for (LoggerContext localLoggerContext : paramList) {
/* 186 */       LoggerContextAdmin localLoggerContextAdmin = new LoggerContextAdmin(localLoggerContext, paramExecutor);
/* 187 */       paramMBeanServer.registerMBean(localLoggerContextAdmin, localLoggerContextAdmin.getObjectName());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void unregisterLoggerConfigs(LoggerContext paramLoggerContext, MBeanServer paramMBeanServer)
/*     */   {
/* 193 */     String str1 = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s";
/* 194 */     String str2 = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s", new Object[] { paramLoggerContext.getName(), "*" });
/* 195 */     unregisterAllMatching(str2, paramMBeanServer);
/*     */   }
/*     */   
/*     */   private static void unregisterAppenders(LoggerContext paramLoggerContext, MBeanServer paramMBeanServer)
/*     */   {
/* 200 */     String str1 = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s";
/* 201 */     String str2 = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s", new Object[] { paramLoggerContext.getName(), "*" });
/* 202 */     unregisterAllMatching(str2, paramMBeanServer);
/*     */   }
/*     */   
/*     */   private static void unregisterAllMatching(String paramString, MBeanServer paramMBeanServer) {
/*     */     try {
/* 207 */       ObjectName localObjectName1 = new ObjectName(paramString);
/* 208 */       Set localSet = paramMBeanServer.queryNames(localObjectName1, null);
/* 209 */       for (ObjectName localObjectName2 : localSet) {
/* 210 */         paramMBeanServer.unregisterMBean(localObjectName2);
/*     */       }
/*     */     } catch (Exception localException) {
/* 213 */       StatusLogger.getLogger().error("Could not unregister " + paramString, localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void registerLoggerConfigs(LoggerContext paramLoggerContext, MBeanServer paramMBeanServer, Executor paramExecutor)
/*     */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */   {
/* 221 */     Map localMap = paramLoggerContext.getConfiguration().getLoggers();
/* 222 */     for (String str : localMap.keySet()) {
/* 223 */       LoggerConfig localLoggerConfig = (LoggerConfig)localMap.get(str);
/* 224 */       LoggerConfigAdmin localLoggerConfigAdmin = new LoggerConfigAdmin(paramLoggerContext.getName(), localLoggerConfig);
/* 225 */       paramMBeanServer.registerMBean(localLoggerConfigAdmin, localLoggerConfigAdmin.getObjectName());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void registerAppenders(LoggerContext paramLoggerContext, MBeanServer paramMBeanServer, Executor paramExecutor)
/*     */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */   {
/* 232 */     Map localMap = paramLoggerContext.getConfiguration().getAppenders();
/* 233 */     for (String str : localMap.keySet()) {
/* 234 */       Appender localAppender = (Appender)localMap.get(str);
/* 235 */       AppenderAdmin localAppenderAdmin = new AppenderAdmin(paramLoggerContext.getName(), localAppender);
/* 236 */       paramMBeanServer.registerMBean(localAppenderAdmin, localAppenderAdmin.getObjectName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\Server.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */