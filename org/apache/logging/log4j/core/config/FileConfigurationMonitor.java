/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
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
/*    */ 
/*    */ public class FileConfigurationMonitor
/*    */   implements ConfigurationMonitor
/*    */ {
/*    */   private static final int MASK = 15;
/*    */   private static final int MIN_INTERVAL = 5;
/*    */   private static final int MILLIS_PER_SECOND = 1000;
/*    */   private final File file;
/*    */   private long lastModified;
/*    */   private final List<ConfigurationListener> listeners;
/*    */   private final int interval;
/*    */   private long nextCheck;
/* 44 */   private volatile int counter = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private final Reconfigurable reconfigurable;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FileConfigurationMonitor(Reconfigurable paramReconfigurable, File paramFile, List<ConfigurationListener> paramList, int paramInt)
/*    */   {
/* 58 */     this.reconfigurable = paramReconfigurable;
/* 59 */     this.file = paramFile;
/* 60 */     this.lastModified = paramFile.lastModified();
/* 61 */     this.listeners = paramList;
/* 62 */     this.interval = ((paramInt < 5 ? 5 : paramInt) * 1000);
/* 63 */     this.nextCheck = (System.currentTimeMillis() + paramInt);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void checkConfiguration()
/*    */   {
/* 71 */     if ((++this.counter & 0xF) == 0) {
/* 72 */       synchronized (this) {
/* 73 */         long l = System.currentTimeMillis();
/* 74 */         if (l >= this.nextCheck) {
/* 75 */           this.nextCheck = (l + this.interval);
/* 76 */           if (this.file.lastModified() > this.lastModified) {
/* 77 */             this.lastModified = this.file.lastModified();
/* 78 */             for (ConfigurationListener localConfigurationListener : this.listeners) {
/* 79 */               localConfigurationListener.onChange(this.reconfigurable);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\FileConfigurationMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */