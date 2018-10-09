/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.BlockingWaitStrategy;
/*     */ import com.lmax.disruptor.ExceptionHandler;
/*     */ import com.lmax.disruptor.RingBuffer;
/*     */ import com.lmax.disruptor.SleepingWaitStrategy;
/*     */ import com.lmax.disruptor.WaitStrategy;
/*     */ import com.lmax.disruptor.YieldingWaitStrategy;
/*     */ import com.lmax.disruptor.dsl.Disruptor;
/*     */ import com.lmax.disruptor.dsl.ProducerType;
/*     */ import com.lmax.disruptor.util.Util;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.Logger.PrivateConfig;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.helpers.Clock;
/*     */ import org.apache.logging.log4j.core.helpers.ClockFactory;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncLogger
/*     */   extends Logger
/*     */ {
/*     */   private static final int HALF_A_SECOND = 500;
/*     */   private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
/*     */   private static final int RINGBUFFER_MIN_SIZE = 128;
/*     */   private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
/*  79 */   private static final StatusLogger LOGGER = ;
/*     */   
/*     */   private static volatile Disruptor<RingBufferLogEvent> disruptor;
/*  82 */   private static Clock clock = ClockFactory.getClock();
/*     */   
/*  84 */   private static ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory("AsyncLogger-"));
/*     */   
/*  86 */   private final ThreadLocal<Info> threadlocalInfo = new ThreadLocal();
/*     */   
/*     */   static {
/*  89 */     int i = calculateRingBufferSize();
/*     */     
/*  91 */     WaitStrategy localWaitStrategy = createWaitStrategy();
/*  92 */     disruptor = new Disruptor(RingBufferLogEvent.FACTORY, i, executor, ProducerType.MULTI, localWaitStrategy);
/*     */     
/*     */ 
/*  95 */     RingBufferLogEventHandler[] arrayOfRingBufferLogEventHandler = { new RingBufferLogEventHandler() };
/*     */     
/*  97 */     disruptor.handleExceptionsWith(getExceptionHandler());
/*  98 */     disruptor.handleEventsWith(arrayOfRingBufferLogEventHandler);
/*     */     
/* 100 */     LOGGER.debug("Starting AsyncLogger disruptor with ringbuffer size {}...", new Object[] { Integer.valueOf(disruptor.getRingBuffer().getBufferSize()) });
/*     */     
/*     */ 
/* 103 */     disruptor.start();
/*     */   }
/*     */   
/*     */   private static int calculateRingBufferSize() {
/* 107 */     int i = 262144;
/* 108 */     String str = System.getProperty("AsyncLogger.RingBufferSize", String.valueOf(i));
/*     */     try
/*     */     {
/* 111 */       int j = Integer.parseInt(str);
/* 112 */       if (j < 128) {
/* 113 */         j = 128;
/* 114 */         LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", new Object[] { str, Integer.valueOf(128) });
/*     */       }
/*     */       
/*     */ 
/* 118 */       i = j;
/*     */     } catch (Exception localException) {
/* 120 */       LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", new Object[] { str, Integer.valueOf(i) });
/*     */     }
/*     */     
/* 123 */     return Util.ceilingNextPowerOfTwo(i);
/*     */   }
/*     */   
/*     */   private static WaitStrategy createWaitStrategy() {
/* 127 */     String str = System.getProperty("AsyncLogger.WaitStrategy");
/* 128 */     LOGGER.debug("property AsyncLogger.WaitStrategy={}", new Object[] { str });
/* 129 */     if ("Sleep".equals(str)) {
/* 130 */       LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
/* 131 */       return new SleepingWaitStrategy(); }
/* 132 */     if ("Yield".equals(str)) {
/* 133 */       LOGGER.debug("disruptor event handler uses YieldingWaitStrategy");
/* 134 */       return new YieldingWaitStrategy(); }
/* 135 */     if ("Block".equals(str)) {
/* 136 */       LOGGER.debug("disruptor event handler uses BlockingWaitStrategy");
/* 137 */       return new BlockingWaitStrategy();
/*     */     }
/* 139 */     LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
/* 140 */     return new SleepingWaitStrategy();
/*     */   }
/*     */   
/*     */   private static ExceptionHandler getExceptionHandler() {
/* 144 */     String str = System.getProperty("AsyncLogger.ExceptionHandler");
/* 145 */     if (str == null) {
/* 146 */       LOGGER.debug("No AsyncLogger.ExceptionHandler specified");
/* 147 */       return null;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 152 */       Class localClass = Class.forName(str);
/*     */       
/* 154 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)localClass.newInstance();
/* 155 */       LOGGER.debug("AsyncLogger.ExceptionHandler=" + localExceptionHandler);
/* 156 */       return localExceptionHandler;
/*     */     } catch (Exception localException) {
/* 158 */       LOGGER.debug("AsyncLogger.ExceptionHandler not set: error creating " + str + ": ", localException);
/*     */     }
/*     */     
/* 161 */     return null;
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
/*     */   public AsyncLogger(LoggerContext paramLoggerContext, String paramString, MessageFactory paramMessageFactory)
/*     */   {
/* 175 */     super(paramLoggerContext, paramString, paramMessageFactory);
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
/*     */   public void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 189 */     Info localInfo = (Info)this.threadlocalInfo.get();
/* 190 */     if (localInfo == null) {
/* 191 */       localInfo = new Info(null);
/* 192 */       localInfo.translator = new RingBufferLogEventTranslator();
/* 193 */       localInfo.cachedThreadName = Thread.currentThread().getName();
/* 194 */       this.threadlocalInfo.set(localInfo);
/*     */     }
/*     */     
/* 197 */     boolean bool = this.config.loggerConfig.isIncludeLocation();
/* 198 */     localInfo.translator.setValues(this, getName(), paramMarker, paramString, paramLevel, paramMessage, paramThrowable, ThreadContext.getImmutableContext(), ThreadContext.getImmutableStack(), localInfo.cachedThreadName, bool ? location(paramString) : null, clock.currentTimeMillis());
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
/* 223 */     disruptor.publishEvent(localInfo.translator);
/*     */   }
/*     */   
/*     */   private StackTraceElement location(String paramString) {
/* 227 */     return Log4jLogEvent.calcLocation(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void actualAsyncLog(RingBufferLogEvent paramRingBufferLogEvent)
/*     */   {
/* 237 */     Map localMap = this.config.loggerConfig.getProperties();
/* 238 */     paramRingBufferLogEvent.mergePropertiesIntoContextMap(localMap, this.config.config.getStrSubstitutor());
/*     */     
/* 240 */     this.config.logEvent(paramRingBufferLogEvent);
/*     */   }
/*     */   
/*     */   public static void stop() {
/* 244 */     Disruptor localDisruptor = disruptor;
/*     */     
/*     */ 
/*     */ 
/* 248 */     disruptor = null;
/* 249 */     localDisruptor.shutdown();
/*     */     
/*     */ 
/* 252 */     RingBuffer localRingBuffer = localDisruptor.getRingBuffer();
/* 253 */     for (int i = 0; i < 20; i++) {
/* 254 */       if (localRingBuffer.hasAvailableCapacity(localRingBuffer.getBufferSize())) {
/*     */         break;
/*     */       }
/*     */       try
/*     */       {
/* 259 */         Thread.sleep(500L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */     
/* 264 */     executor.shutdown();
/*     */   }
/*     */   
/*     */   private static class Info
/*     */   {
/*     */     private RingBufferLogEventTranslator translator;
/*     */     private String cachedThreadName;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */