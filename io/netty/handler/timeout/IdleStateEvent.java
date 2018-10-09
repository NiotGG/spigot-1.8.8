/*    */ package io.netty.handler.timeout;
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
/*    */ public final class IdleStateEvent
/*    */ {
/* 24 */   public static final IdleStateEvent FIRST_READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, true);
/* 25 */   public static final IdleStateEvent READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, false);
/* 26 */   public static final IdleStateEvent FIRST_WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, true);
/* 27 */   public static final IdleStateEvent WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, false);
/* 28 */   public static final IdleStateEvent FIRST_ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, true);
/* 29 */   public static final IdleStateEvent ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, false);
/*    */   private final IdleState state;
/*    */   private final boolean first;
/*    */   
/*    */   private IdleStateEvent(IdleState paramIdleState, boolean paramBoolean)
/*    */   {
/* 35 */     this.state = paramIdleState;
/* 36 */     this.first = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IdleState state()
/*    */   {
/* 43 */     return this.state;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isFirst()
/*    */   {
/* 50 */     return this.first;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\timeout\IdleStateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */