/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.status.StatusData;
/*     */ import org.apache.logging.log4j.status.StatusListener;
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
/*     */ public class StatusLoggerAdmin
/*     */   extends NotificationBroadcasterSupport
/*     */   implements StatusListener, StatusLoggerAdminMBean
/*     */ {
/*  38 */   private final AtomicLong sequenceNo = new AtomicLong();
/*     */   private final ObjectName objectName;
/*  40 */   private Level level = Level.WARN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StatusLoggerAdmin(Executor paramExecutor)
/*     */   {
/*  49 */     super(paramExecutor, new MBeanNotificationInfo[] { createNotificationInfo() });
/*     */     try {
/*  51 */       this.objectName = new ObjectName("org.apache.logging.log4j2:type=StatusLogger");
/*     */     } catch (Exception localException) {
/*  53 */       throw new IllegalStateException(localException);
/*     */     }
/*  55 */     StatusLogger.getLogger().registerListener(this);
/*     */   }
/*     */   
/*     */   private static MBeanNotificationInfo createNotificationInfo() {
/*  59 */     String[] arrayOfString = { "com.apache.logging.log4j.core.jmx.statuslogger.data", "com.apache.logging.log4j.core.jmx.statuslogger.message" };
/*     */     
/*  61 */     String str1 = Notification.class.getName();
/*  62 */     String str2 = "StatusLogger has logged an event";
/*  63 */     return new MBeanNotificationInfo(arrayOfString, str1, "StatusLogger has logged an event");
/*     */   }
/*     */   
/*     */   public String[] getStatusDataHistory()
/*     */   {
/*  68 */     List localList = getStatusData();
/*  69 */     String[] arrayOfString = new String[localList.size()];
/*  70 */     for (int i = 0; i < arrayOfString.length; i++) {
/*  71 */       arrayOfString[i] = ((StatusData)localList.get(i)).getFormattedStatus();
/*     */     }
/*  73 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public List<StatusData> getStatusData()
/*     */   {
/*  78 */     return StatusLogger.getLogger().getStatusData();
/*     */   }
/*     */   
/*     */   public String getLevel()
/*     */   {
/*  83 */     return this.level.name();
/*     */   }
/*     */   
/*     */   public Level getStatusLevel()
/*     */   {
/*  88 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(String paramString)
/*     */   {
/*  93 */     this.level = Level.toLevel(paramString, Level.ERROR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void log(StatusData paramStatusData)
/*     */   {
/* 105 */     Notification localNotification1 = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.message", getObjectName(), nextSeqNo(), now(), paramStatusData.getFormattedStatus());
/*     */     
/* 107 */     sendNotification(localNotification1);
/*     */     
/* 109 */     Notification localNotification2 = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.data", getObjectName(), nextSeqNo(), now());
/*     */     
/* 111 */     localNotification2.setUserData(paramStatusData);
/* 112 */     sendNotification(localNotification2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectName getObjectName()
/*     */   {
/* 122 */     return this.objectName;
/*     */   }
/*     */   
/*     */   private long nextSeqNo() {
/* 126 */     return this.sequenceNo.getAndIncrement();
/*     */   }
/*     */   
/*     */   private long now() {
/* 130 */     return System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\StatusLoggerAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */