/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.Recycler.Handle;
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
/*    */ public abstract class RecyclableMpscLinkedQueueNode<T>
/*    */   extends MpscLinkedQueueNode<T>
/*    */ {
/*    */   private final Recycler.Handle handle;
/*    */   
/*    */   protected RecyclableMpscLinkedQueueNode(Recycler.Handle paramHandle)
/*    */   {
/* 29 */     if (paramHandle == null) {
/* 30 */       throw new NullPointerException("handle");
/*    */     }
/* 32 */     this.handle = paramHandle;
/*    */   }
/*    */   
/*    */   final void unlink()
/*    */   {
/* 37 */     super.unlink();
/* 38 */     recycle(this.handle);
/*    */   }
/*    */   
/*    */   protected abstract void recycle(Recycler.Handle paramHandle);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\RecyclableMpscLinkedQueueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */