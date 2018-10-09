/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.LoggerContext.Status;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory.ConfigurationSource;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
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
/*     */ public class LoggerContextAdmin
/*     */   extends NotificationBroadcasterSupport
/*     */   implements LoggerContextAdminMBean, PropertyChangeListener
/*     */ {
/*     */   private static final int PAGE = 4096;
/*     */   private static final int TEXT_BUFFER = 65536;
/*     */   private static final int BUFFER_SIZE = 2048;
/*  58 */   private static final StatusLogger LOGGER = ;
/*     */   
/*  60 */   private final AtomicLong sequenceNo = new AtomicLong();
/*     */   
/*     */ 
/*     */   private final ObjectName objectName;
/*     */   
/*     */ 
/*     */   private final LoggerContext loggerContext;
/*     */   
/*     */   private String customConfigText;
/*     */   
/*     */ 
/*     */   public LoggerContextAdmin(LoggerContext paramLoggerContext, Executor paramExecutor)
/*     */   {
/*  73 */     super(paramExecutor, new MBeanNotificationInfo[] { createNotificationInfo() });
/*  74 */     this.loggerContext = ((LoggerContext)Assert.isNotNull(paramLoggerContext, "loggerContext"));
/*     */     try {
/*  76 */       String str1 = Server.escape(paramLoggerContext.getName());
/*  77 */       String str2 = String.format("org.apache.logging.log4j2:type=LoggerContext,ctx=%s", new Object[] { str1 });
/*  78 */       this.objectName = new ObjectName(str2);
/*     */     } catch (Exception localException) {
/*  80 */       throw new IllegalStateException(localException);
/*     */     }
/*  82 */     paramLoggerContext.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private static MBeanNotificationInfo createNotificationInfo() {
/*  86 */     String[] arrayOfString = { "com.apache.logging.log4j.core.jmx.config.reconfigured" };
/*     */     
/*  88 */     String str1 = Notification.class.getName();
/*  89 */     String str2 = "Configuration reconfigured";
/*  90 */     return new MBeanNotificationInfo(arrayOfString, str1, "Configuration reconfigured");
/*     */   }
/*     */   
/*     */   public String getStatus()
/*     */   {
/*  95 */     return this.loggerContext.getStatus().toString();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 100 */     return this.loggerContext.getName();
/*     */   }
/*     */   
/*     */   private Configuration getConfig() {
/* 104 */     return this.loggerContext.getConfiguration();
/*     */   }
/*     */   
/*     */   public String getConfigLocationURI()
/*     */   {
/* 109 */     if (this.loggerContext.getConfigLocation() != null) {
/* 110 */       return String.valueOf(this.loggerContext.getConfigLocation());
/*     */     }
/* 112 */     if (getConfigName() != null) {
/* 113 */       return String.valueOf(new File(getConfigName()).toURI());
/*     */     }
/* 115 */     return "";
/*     */   }
/*     */   
/*     */   public void setConfigLocationURI(String paramString)
/*     */     throws URISyntaxException, IOException
/*     */   {
/* 121 */     LOGGER.debug("---------");
/* 122 */     LOGGER.debug("Remote request to reconfigure using location " + paramString);
/*     */     
/* 124 */     URI localURI = new URI(paramString);
/*     */     
/*     */ 
/*     */ 
/* 128 */     localURI.toURL().openStream().close();
/*     */     
/* 130 */     this.loggerContext.setConfigLocation(localURI);
/* 131 */     LOGGER.debug("Completed remote request to reconfigure.");
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*     */   {
/* 136 */     if (!"config".equals(paramPropertyChangeEvent.getPropertyName())) {
/* 137 */       return;
/*     */     }
/*     */     
/* 140 */     if (this.loggerContext.getConfiguration().getName() != null) {
/* 141 */       this.customConfigText = null;
/*     */     }
/* 143 */     Notification localNotification = new Notification("com.apache.logging.log4j.core.jmx.config.reconfigured", getObjectName(), nextSeqNo(), now(), null);
/*     */     
/* 145 */     sendNotification(localNotification);
/*     */   }
/*     */   
/*     */   public String getConfigText() throws IOException
/*     */   {
/* 150 */     return getConfigText(Charsets.UTF_8.name());
/*     */   }
/*     */   
/*     */   public String getConfigText(String paramString) throws IOException
/*     */   {
/* 155 */     if (this.customConfigText != null) {
/* 156 */       return this.customConfigText;
/*     */     }
/*     */     try {
/* 159 */       Charset localCharset = Charset.forName(paramString);
/* 160 */       return readContents(new URI(getConfigLocationURI()), localCharset);
/*     */     } catch (Exception localException) {
/* 162 */       StringWriter localStringWriter = new StringWriter(2048);
/* 163 */       localException.printStackTrace(new PrintWriter(localStringWriter));
/* 164 */       return localStringWriter.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setConfigText(String paramString1, String paramString2)
/*     */   {
/* 170 */     String str = this.customConfigText;
/* 171 */     this.customConfigText = ((String)Assert.isNotNull(paramString1, "configText"));
/* 172 */     LOGGER.debug("---------");
/* 173 */     LOGGER.debug("Remote request to reconfigure from config text.");
/*     */     try
/*     */     {
/* 176 */       ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString1.getBytes(paramString2));
/*     */       
/* 178 */       localObject = new ConfigurationFactory.ConfigurationSource(localByteArrayInputStream);
/* 179 */       Configuration localConfiguration = ConfigurationFactory.getInstance().getConfiguration((ConfigurationFactory.ConfigurationSource)localObject);
/*     */       
/* 181 */       this.loggerContext.start(localConfiguration);
/* 182 */       LOGGER.debug("Completed remote request to reconfigure from config text.");
/*     */     } catch (Exception localException) {
/* 184 */       this.customConfigText = str;
/* 185 */       Object localObject = "Could not reconfigure from config text";
/* 186 */       LOGGER.error("Could not reconfigure from config text", localException);
/* 187 */       throw new IllegalArgumentException("Could not reconfigure from config text", localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String readContents(URI paramURI, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 199 */     InputStream localInputStream = null;
/* 200 */     InputStreamReader localInputStreamReader = null;
/*     */     try {
/* 202 */       localInputStream = paramURI.toURL().openStream();
/* 203 */       localInputStreamReader = new InputStreamReader(localInputStream, paramCharset);
/* 204 */       StringBuilder localStringBuilder = new StringBuilder(65536);
/* 205 */       char[] arrayOfChar = new char['က'];
/* 206 */       int i = -1;
/* 207 */       while ((i = localInputStreamReader.read(arrayOfChar)) >= 0) {
/* 208 */         localStringBuilder.append(arrayOfChar, 0, i);
/*     */       }
/* 210 */       return localStringBuilder.toString();
/*     */     } finally {
/* 212 */       Closer.closeSilent(localInputStream);
/* 213 */       Closer.closeSilent(localInputStreamReader);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getConfigName()
/*     */   {
/* 219 */     return getConfig().getName();
/*     */   }
/*     */   
/*     */   public String getConfigClassName()
/*     */   {
/* 224 */     return getConfig().getClass().getName();
/*     */   }
/*     */   
/*     */   public String getConfigFilter()
/*     */   {
/* 229 */     return String.valueOf(getConfig().getFilter());
/*     */   }
/*     */   
/*     */   public String getConfigMonitorClassName()
/*     */   {
/* 234 */     return getConfig().getConfigurationMonitor().getClass().getName();
/*     */   }
/*     */   
/*     */   public Map<String, String> getConfigProperties()
/*     */   {
/* 239 */     return getConfig().getProperties();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectName getObjectName()
/*     */   {
/* 249 */     return this.objectName;
/*     */   }
/*     */   
/*     */   private long nextSeqNo() {
/* 253 */     return this.sequenceNo.getAndIncrement();
/*     */   }
/*     */   
/*     */   private long now() {
/* 257 */     return System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerContextAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */