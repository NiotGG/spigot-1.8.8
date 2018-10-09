/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*     */ @Plugin(name="Failover", category="Core", elementType="appender", printObject=true)
/*     */ public final class FailoverAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_INTERVAL_SECONDS = 60;
/*     */   private final String primaryRef;
/*     */   private final String[] failovers;
/*     */   private final Configuration config;
/*     */   private AppenderControl primary;
/*  55 */   private final List<AppenderControl> failoverAppenders = new ArrayList();
/*     */   
/*     */   private final long intervalMillis;
/*     */   
/*  59 */   private long nextCheckMillis = 0L;
/*     */   
/*  61 */   private volatile boolean failure = false;
/*     */   
/*     */   private FailoverAppender(String paramString1, Filter paramFilter, String paramString2, String[] paramArrayOfString, int paramInt, Configuration paramConfiguration, boolean paramBoolean)
/*     */   {
/*  65 */     super(paramString1, paramFilter, null, paramBoolean);
/*  66 */     this.primaryRef = paramString2;
/*  67 */     this.failovers = paramArrayOfString;
/*  68 */     this.config = paramConfiguration;
/*  69 */     this.intervalMillis = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public void start()
/*     */   {
/*  75 */     Map localMap = this.config.getAppenders();
/*  76 */     int i = 0;
/*  77 */     if (localMap.containsKey(this.primaryRef)) {
/*  78 */       this.primary = new AppenderControl((Appender)localMap.get(this.primaryRef), null, null);
/*     */     } else {
/*  80 */       LOGGER.error("Unable to locate primary Appender " + this.primaryRef);
/*  81 */       i++;
/*     */     }
/*  83 */     for (String str : this.failovers) {
/*  84 */       if (localMap.containsKey(str)) {
/*  85 */         this.failoverAppenders.add(new AppenderControl((Appender)localMap.get(str), null, null));
/*     */       } else {
/*  87 */         LOGGER.error("Failover appender " + str + " is not configured");
/*     */       }
/*     */     }
/*  90 */     if (this.failoverAppenders.size() == 0) {
/*  91 */       LOGGER.error("No failover appenders are available");
/*  92 */       i++;
/*     */     }
/*  94 */     if (i == 0) {
/*  95 */       super.start();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/* 105 */     if (!isStarted()) {
/* 106 */       error("FailoverAppender " + getName() + " did not start successfully");
/* 107 */       return;
/*     */     }
/* 109 */     if (!this.failure) {
/* 110 */       callAppender(paramLogEvent);
/*     */     } else {
/* 112 */       long l = System.currentTimeMillis();
/* 113 */       if (l >= this.nextCheckMillis) {
/* 114 */         callAppender(paramLogEvent);
/*     */       } else {
/* 116 */         failover(paramLogEvent, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void callAppender(LogEvent paramLogEvent) {
/*     */     try {
/* 123 */       this.primary.callAppender(paramLogEvent);
/*     */     } catch (Exception localException) {
/* 125 */       this.nextCheckMillis = (System.currentTimeMillis() + this.intervalMillis);
/* 126 */       this.failure = true;
/* 127 */       failover(paramLogEvent, localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void failover(LogEvent paramLogEvent, Exception paramException) {
/* 132 */     Object localObject1 = paramException != null ? new LoggingException(paramException) : (paramException instanceof LoggingException) ? (LoggingException)paramException : null;
/*     */     
/* 134 */     int i = 0;
/* 135 */     Object localObject2 = null;
/* 136 */     for (AppenderControl localAppenderControl : this.failoverAppenders) {
/*     */       try {
/* 138 */         localAppenderControl.callAppender(paramLogEvent);
/* 139 */         i = 1;
/*     */       }
/*     */       catch (Exception localException) {
/* 142 */         if (localObject2 == null) {
/* 143 */           localObject2 = localException;
/*     */         }
/*     */       }
/*     */     }
/* 147 */     if ((i == 0) && (!ignoreExceptions())) {
/* 148 */       if (localObject1 != null) {
/* 149 */         throw ((Throwable)localObject1);
/*     */       }
/* 151 */       throw new LoggingException("Unable to write to failover appenders", (Throwable)localObject2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 158 */     StringBuilder localStringBuilder = new StringBuilder(getName());
/* 159 */     localStringBuilder.append(" primary=").append(this.primary).append(", failover={");
/* 160 */     int i = 1;
/* 161 */     for (String str : this.failovers) {
/* 162 */       if (i == 0) {
/* 163 */         localStringBuilder.append(", ");
/*     */       }
/* 165 */       localStringBuilder.append(str);
/* 166 */       i = 0;
/*     */     }
/* 168 */     localStringBuilder.append("}");
/* 169 */     return localStringBuilder.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static FailoverAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("primary") String paramString2, @PluginElement("Failovers") String[] paramArrayOfString, @PluginAttribute("retryInterval") String paramString3, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString4)
/*     */   {
/* 193 */     if (paramString1 == null) {
/* 194 */       LOGGER.error("A name for the Appender must be specified");
/* 195 */       return null;
/*     */     }
/* 197 */     if (paramString2 == null) {
/* 198 */       LOGGER.error("A primary Appender must be specified");
/* 199 */       return null;
/*     */     }
/* 201 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 202 */       LOGGER.error("At least one failover Appender must be specified");
/* 203 */       return null;
/*     */     }
/*     */     
/* 206 */     int i = parseInt(paramString3, 60);
/*     */     int j;
/* 208 */     if (i >= 0) {
/* 209 */       j = i * 1000;
/*     */     } else {
/* 211 */       LOGGER.warn("Interval " + paramString3 + " is less than zero. Using default");
/* 212 */       j = 60000;
/*     */     }
/*     */     
/* 215 */     boolean bool = Booleans.parseBoolean(paramString4, true);
/*     */     
/* 217 */     return new FailoverAppender(paramString1, paramFilter, paramString2, paramArrayOfString, j, paramConfiguration, bool);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FailoverAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */