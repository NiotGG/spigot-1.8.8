/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.DelayQueue;
/*     */ import java.util.concurrent.Delayed;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name="BurstFilter", category="Core", elementType="filter", printObject=true)
/*     */ public final class BurstFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private static final long NANOS_IN_SECONDS = 1000000000L;
/*     */   private static final int DEFAULT_RATE = 10;
/*     */   private static final int DEFAULT_RATE_MULTIPLE = 100;
/*     */   private static final int HASH_SHIFT = 32;
/*     */   private final Level level;
/*     */   private final long burstInterval;
/*  76 */   private final DelayQueue<LogDelay> history = new DelayQueue();
/*     */   
/*  78 */   private final Queue<LogDelay> available = new ConcurrentLinkedQueue();
/*     */   
/*     */   private BurstFilter(Level paramLevel, float paramFloat, long paramLong, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  82 */     super(paramResult1, paramResult2);
/*  83 */     this.level = paramLevel;
/*  84 */     this.burstInterval = ((1.0E9F * ((float)paramLong / paramFloat)));
/*  85 */     for (int i = 0; i < paramLong; i++) {
/*  86 */       this.available.add(new LogDelay());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/*  93 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/*  99 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 105 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/* 110 */     return filter(paramLogEvent.getLevel());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Filter.Result filter(Level paramLevel)
/*     */   {
/* 121 */     if (this.level.isAtLeastAsSpecificAs(paramLevel)) {
/* 122 */       LogDelay localLogDelay = (LogDelay)this.history.poll();
/* 123 */       while (localLogDelay != null) {
/* 124 */         this.available.add(localLogDelay);
/* 125 */         localLogDelay = (LogDelay)this.history.poll();
/*     */       }
/* 127 */       localLogDelay = (LogDelay)this.available.poll();
/* 128 */       if (localLogDelay != null) {
/* 129 */         localLogDelay.setDelay(this.burstInterval);
/* 130 */         this.history.add(localLogDelay);
/* 131 */         return this.onMatch;
/*     */       }
/* 133 */       return this.onMismatch;
/*     */     }
/* 135 */     return this.onMatch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAvailable()
/*     */   {
/* 144 */     return this.available.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 151 */     Iterator localIterator = this.history.iterator();
/* 152 */     while (localIterator.hasNext()) {
/* 153 */       LogDelay localLogDelay = (LogDelay)localIterator.next();
/* 154 */       this.history.remove(localLogDelay);
/* 155 */       this.available.add(localLogDelay);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 161 */     return "level=" + this.level.toString() + ", interval=" + this.burstInterval + ", max=" + this.history.size();
/*     */   }
/*     */   
/*     */ 
/*     */   private class LogDelay
/*     */     implements Delayed
/*     */   {
/*     */     private long expireTime;
/*     */     
/*     */ 
/*     */     public LogDelay() {}
/*     */     
/*     */     public void setDelay(long paramLong)
/*     */     {
/* 175 */       this.expireTime = (paramLong + System.nanoTime());
/*     */     }
/*     */     
/*     */     public long getDelay(TimeUnit paramTimeUnit)
/*     */     {
/* 180 */       return paramTimeUnit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/*     */     public int compareTo(Delayed paramDelayed)
/*     */     {
/* 185 */       if (this.expireTime < ((LogDelay)paramDelayed).expireTime)
/* 186 */         return -1;
/* 187 */       if (this.expireTime > ((LogDelay)paramDelayed).expireTime) {
/* 188 */         return 1;
/*     */       }
/* 190 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 195 */       if (this == paramObject) {
/* 196 */         return true;
/*     */       }
/* 198 */       if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 199 */         return false;
/*     */       }
/*     */       
/* 202 */       LogDelay localLogDelay = (LogDelay)paramObject;
/*     */       
/* 204 */       if (this.expireTime != localLogDelay.expireTime) {
/* 205 */         return false;
/*     */       }
/*     */       
/* 208 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 213 */       return (int)(this.expireTime ^ this.expireTime >>> 32);
/*     */     }
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
/*     */   @PluginFactory
/*     */   public static BurstFilter createFilter(@PluginAttribute("level") String paramString1, @PluginAttribute("rate") String paramString2, @PluginAttribute("maxBurst") String paramString3, @PluginAttribute("onMatch") String paramString4, @PluginAttribute("onMismatch") String paramString5)
/*     */   {
/* 233 */     Filter.Result localResult1 = Filter.Result.toResult(paramString4, Filter.Result.NEUTRAL);
/* 234 */     Filter.Result localResult2 = Filter.Result.toResult(paramString5, Filter.Result.DENY);
/* 235 */     Level localLevel = Level.toLevel(paramString1, Level.WARN);
/* 236 */     float f = paramString2 == null ? 10.0F : Float.parseFloat(paramString2);
/* 237 */     if (f <= 0.0F) {
/* 238 */       f = 10.0F;
/*     */     }
/* 240 */     long l = paramString3 == null ? (f * 100.0F) : Long.parseLong(paramString3);
/* 241 */     return new BurstFilter(localLevel, f, l, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\BurstFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */