/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.BlockingWaitStrategy;
/*     */ import com.lmax.disruptor.EventFactory;
/*     */ import com.lmax.disruptor.EventTranslator;
/*     */ import com.lmax.disruptor.ExceptionHandler;
/*     */ import com.lmax.disruptor.RingBuffer;
/*     */ import com.lmax.disruptor.Sequence;
/*     */ import com.lmax.disruptor.SequenceReportingEventHandler;
/*     */ import com.lmax.disruptor.SleepingWaitStrategy;
/*     */ import com.lmax.disruptor.WaitStrategy;
/*     */ import com.lmax.disruptor.YieldingWaitStrategy;
/*     */ import com.lmax.disruptor.dsl.Disruptor;
/*     */ import com.lmax.disruptor.dsl.ProducerType;
/*     */ import com.lmax.disruptor.util.Util;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ class AsyncLoggerConfigHelper
/*     */ {
/*     */   private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
/*     */   private static final int HALF_A_SECOND = 500;
/*     */   private static final int RINGBUFFER_MIN_SIZE = 128;
/*     */   private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
/*  64 */   private static final Logger LOGGER = ;
/*     */   
/*  66 */   private static ThreadFactory threadFactory = new DaemonThreadFactory("AsyncLoggerConfig-");
/*     */   
/*     */   private static volatile Disruptor<Log4jEventWrapper> disruptor;
/*     */   
/*     */   private static ExecutorService executor;
/*  71 */   private static volatile int count = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   private static final EventFactory<Log4jEventWrapper> FACTORY = new EventFactory()
/*     */   {
/*     */     public AsyncLoggerConfigHelper.Log4jEventWrapper newInstance() {
/*  80 */       return new AsyncLoggerConfigHelper.Log4jEventWrapper(null);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  87 */   private final EventTranslator<Log4jEventWrapper> translator = new EventTranslator()
/*     */   {
/*     */     public void translateTo(AsyncLoggerConfigHelper.Log4jEventWrapper paramAnonymousLog4jEventWrapper, long paramAnonymousLong)
/*     */     {
/*  91 */       paramAnonymousLog4jEventWrapper.event = ((LogEvent)AsyncLoggerConfigHelper.this.currentLogEvent.get());
/*  92 */       paramAnonymousLog4jEventWrapper.loggerConfig = AsyncLoggerConfigHelper.this.asyncLoggerConfig;
/*     */     }
/*     */   };
/*     */   
/*  96 */   private final ThreadLocal<LogEvent> currentLogEvent = new ThreadLocal();
/*     */   private final AsyncLoggerConfig asyncLoggerConfig;
/*     */   
/*     */   public AsyncLoggerConfigHelper(AsyncLoggerConfig paramAsyncLoggerConfig) {
/* 100 */     this.asyncLoggerConfig = paramAsyncLoggerConfig;
/* 101 */     claim();
/*     */   }
/*     */   
/*     */   private static synchronized void initDisruptor() {
/* 105 */     if (disruptor != null) {
/* 106 */       LOGGER.trace("AsyncLoggerConfigHelper not starting new disruptor, using existing object. Ref count is {}.", new Object[] { Integer.valueOf(count) });
/* 107 */       return;
/*     */     }
/* 109 */     LOGGER.trace("AsyncLoggerConfigHelper creating new disruptor. Ref count is {}.", new Object[] { Integer.valueOf(count) });
/* 110 */     int i = calculateRingBufferSize();
/* 111 */     WaitStrategy localWaitStrategy = createWaitStrategy();
/* 112 */     executor = Executors.newSingleThreadExecutor(threadFactory);
/* 113 */     disruptor = new Disruptor(FACTORY, i, executor, ProducerType.MULTI, localWaitStrategy);
/*     */     
/* 115 */     Log4jEventWrapperHandler[] arrayOfLog4jEventWrapperHandler = { new Log4jEventWrapperHandler(null) };
/*     */     
/* 117 */     ExceptionHandler localExceptionHandler = getExceptionHandler();
/* 118 */     disruptor.handleExceptionsWith(localExceptionHandler);
/* 119 */     disruptor.handleEventsWith(arrayOfLog4jEventWrapperHandler);
/*     */     
/* 121 */     LOGGER.debug("Starting AsyncLoggerConfig disruptor with ringbuffer size={}, waitStrategy={}, exceptionHandler={}...", new Object[] { Integer.valueOf(disruptor.getRingBuffer().getBufferSize()), localWaitStrategy.getClass().getSimpleName(), localExceptionHandler });
/*     */     
/*     */ 
/* 124 */     disruptor.start();
/*     */   }
/*     */   
/*     */   private static WaitStrategy createWaitStrategy() {
/* 128 */     String str = System.getProperty("AsyncLoggerConfig.WaitStrategy");
/*     */     
/* 130 */     LOGGER.debug("property AsyncLoggerConfig.WaitStrategy={}", new Object[] { str });
/* 131 */     if ("Sleep".equals(str))
/* 132 */       return new SleepingWaitStrategy();
/* 133 */     if ("Yield".equals(str))
/* 134 */       return new YieldingWaitStrategy();
/* 135 */     if ("Block".equals(str)) {
/* 136 */       return new BlockingWaitStrategy();
/*     */     }
/* 138 */     return new SleepingWaitStrategy();
/*     */   }
/*     */   
/*     */   private static int calculateRingBufferSize() {
/* 142 */     int i = 262144;
/* 143 */     String str = System.getProperty("AsyncLoggerConfig.RingBufferSize", String.valueOf(i));
/*     */     
/*     */     try
/*     */     {
/* 147 */       int j = Integer.parseInt(str);
/* 148 */       if (j < 128) {
/* 149 */         j = 128;
/* 150 */         LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", new Object[] { str, Integer.valueOf(128) });
/*     */       }
/*     */       
/*     */ 
/* 154 */       i = j;
/*     */     } catch (Exception localException) {
/* 156 */       LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", new Object[] { str, Integer.valueOf(i) });
/*     */     }
/*     */     
/* 159 */     return Util.ceilingNextPowerOfTwo(i);
/*     */   }
/*     */   
/*     */   private static ExceptionHandler getExceptionHandler() {
/* 163 */     String str = System.getProperty("AsyncLoggerConfig.ExceptionHandler");
/*     */     
/* 165 */     if (str == null) {
/* 166 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 170 */       Class localClass = Class.forName(str);
/*     */       
/* 172 */       return (ExceptionHandler)localClass.newInstance();
/*     */     }
/*     */     catch (Exception localException) {
/* 175 */       LOGGER.debug("AsyncLoggerConfig.ExceptionHandler not set: error creating " + str + ": ", localException);
/*     */     }
/*     */     
/* 178 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class Log4jEventWrapper
/*     */   {
/*     */     private AsyncLoggerConfig loggerConfig;
/*     */     
/*     */ 
/*     */     private LogEvent event;
/*     */     
/*     */ 
/*     */ 
/*     */     public void clear()
/*     */     {
/* 195 */       this.loggerConfig = null;
/* 196 */       this.event = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class Log4jEventWrapperHandler
/*     */     implements SequenceReportingEventHandler<AsyncLoggerConfigHelper.Log4jEventWrapper>
/*     */   {
/*     */     private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
/*     */     
/*     */     private Sequence sequenceCallback;
/*     */     private int counter;
/*     */     
/*     */     public void setSequenceCallback(Sequence paramSequence)
/*     */     {
/* 211 */       this.sequenceCallback = paramSequence;
/*     */     }
/*     */     
/*     */     public void onEvent(AsyncLoggerConfigHelper.Log4jEventWrapper paramLog4jEventWrapper, long paramLong, boolean paramBoolean)
/*     */       throws Exception
/*     */     {
/* 217 */       AsyncLoggerConfigHelper.Log4jEventWrapper.access$100(paramLog4jEventWrapper).setEndOfBatch(paramBoolean);
/* 218 */       AsyncLoggerConfigHelper.Log4jEventWrapper.access$300(paramLog4jEventWrapper).asyncCallAppenders(AsyncLoggerConfigHelper.Log4jEventWrapper.access$100(paramLog4jEventWrapper));
/* 219 */       paramLog4jEventWrapper.clear();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 224 */       if (++this.counter > 50) {
/* 225 */         this.sequenceCallback.set(paramLong);
/* 226 */         this.counter = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized void claim()
/*     */   {
/* 238 */     count += 1;
/* 239 */     initDisruptor();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static synchronized void release()
/*     */   {
/* 248 */     if (--count > 0) {
/* 249 */       LOGGER.trace("AsyncLoggerConfigHelper: not shutting down disruptor: ref count is {}.", new Object[] { Integer.valueOf(count) });
/* 250 */       return;
/*     */     }
/* 252 */     Disruptor localDisruptor = disruptor;
/* 253 */     if (localDisruptor == null) {
/* 254 */       LOGGER.trace("AsyncLoggerConfigHelper: disruptor already shut down: ref count is {}.", new Object[] { Integer.valueOf(count) });
/* 255 */       return;
/*     */     }
/* 257 */     LOGGER.trace("AsyncLoggerConfigHelper: shutting down disruptor: ref count is {}.", new Object[] { Integer.valueOf(count) });
/*     */     
/*     */ 
/*     */ 
/* 261 */     disruptor = null;
/* 262 */     localDisruptor.shutdown();
/*     */     
/*     */ 
/* 265 */     RingBuffer localRingBuffer = localDisruptor.getRingBuffer();
/* 266 */     for (int i = 0; i < 20; i++) {
/* 267 */       if (localRingBuffer.hasAvailableCapacity(localRingBuffer.getBufferSize())) {
/*     */         break;
/*     */       }
/*     */       try
/*     */       {
/* 272 */         Thread.sleep(500L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */     
/* 277 */     executor.shutdown();
/* 278 */     executor = null;
/*     */   }
/*     */   
/*     */   public void callAppendersFromAnotherThread(LogEvent paramLogEvent) {
/* 282 */     this.currentLogEvent.set(paramLogEvent);
/* 283 */     disruptor.publishEvent(this.translator);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerConfigHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */