/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import com.lmax.disruptor.Sequence;
/*    */ import com.lmax.disruptor.SequenceReportingEventHandler;
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
/*    */ public class RingBufferLogEventHandler
/*    */   implements SequenceReportingEventHandler<RingBufferLogEvent>
/*    */ {
/*    */   private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
/*    */   private Sequence sequenceCallback;
/*    */   private int counter;
/*    */   
/*    */   public void setSequenceCallback(Sequence paramSequence)
/*    */   {
/* 37 */     this.sequenceCallback = paramSequence;
/*    */   }
/*    */   
/*    */   public void onEvent(RingBufferLogEvent paramRingBufferLogEvent, long paramLong, boolean paramBoolean)
/*    */     throws Exception
/*    */   {
/* 43 */     paramRingBufferLogEvent.execute(paramBoolean);
/* 44 */     paramRingBufferLogEvent.clear();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 49 */     if (++this.counter > 50) {
/* 50 */       this.sequenceCallback.set(paramLong);
/* 51 */       this.counter = 0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\RingBufferLogEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */