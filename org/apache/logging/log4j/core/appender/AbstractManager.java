/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public abstract class AbstractManager
/*     */ {
/*  35 */   protected static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */ 
/*  39 */   private static final Map<String, AbstractManager> MAP = new HashMap();
/*     */   
/*  41 */   private static final Lock LOCK = new ReentrantLock();
/*     */   
/*     */ 
/*     */   protected int count;
/*     */   
/*     */   private final String name;
/*     */   
/*     */ 
/*     */   protected AbstractManager(String paramString)
/*     */   {
/*  51 */     this.name = paramString;
/*  52 */     LOGGER.debug("Starting {} {}", new Object[] { getClass().getSimpleName(), paramString });
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
/*     */   public static <M extends AbstractManager, T> M getManager(String paramString, ManagerFactory<M, T> paramManagerFactory, T paramT)
/*     */   {
/*  66 */     LOCK.lock();
/*     */     try
/*     */     {
/*  69 */       AbstractManager localAbstractManager1 = (AbstractManager)MAP.get(paramString);
/*  70 */       if (localAbstractManager1 == null) {
/*  71 */         localAbstractManager1 = (AbstractManager)paramManagerFactory.createManager(paramString, paramT);
/*  72 */         if (localAbstractManager1 == null) {
/*  73 */           throw new IllegalStateException("Unable to create a manager");
/*     */         }
/*  75 */         MAP.put(paramString, localAbstractManager1);
/*     */       }
/*  77 */       localAbstractManager1.count += 1;
/*  78 */       return localAbstractManager1;
/*     */     } finally {
/*  80 */       LOCK.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean hasManager(String paramString)
/*     */   {
/*  90 */     LOCK.lock();
/*     */     try {
/*  92 */       return MAP.containsKey(paramString);
/*     */     } finally {
/*  94 */       LOCK.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void releaseSub() {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected int getCount()
/*     */   {
/* 106 */     return this.count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 113 */     LOCK.lock();
/*     */     try {
/* 115 */       this.count -= 1;
/* 116 */       if (this.count <= 0) {
/* 117 */         MAP.remove(this.name);
/* 118 */         LOGGER.debug("Shutting down {} {}", new Object[] { getClass().getSimpleName(), getName() });
/* 119 */         releaseSub();
/*     */       }
/*     */     } finally {
/* 122 */       LOCK.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 131 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 142 */     return new HashMap();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AbstractManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */