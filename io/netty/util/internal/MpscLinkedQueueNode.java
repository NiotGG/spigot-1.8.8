/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
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
/*    */ public abstract class MpscLinkedQueueNode<T>
/*    */ {
/*    */   private static final AtomicReferenceFieldUpdater<MpscLinkedQueueNode, MpscLinkedQueueNode> nextUpdater;
/*    */   private volatile MpscLinkedQueueNode<T> next;
/*    */   
/*    */   static
/*    */   {
/* 30 */     AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueNode.class, "next");
/* 31 */     if (localAtomicReferenceFieldUpdater == null) {
/* 32 */       localAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueNode.class, MpscLinkedQueueNode.class, "next");
/*    */     }
/* 34 */     nextUpdater = localAtomicReferenceFieldUpdater;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   final MpscLinkedQueueNode<T> next()
/*    */   {
/* 41 */     return this.next;
/*    */   }
/*    */   
/*    */ 
/*    */   final void setNext(MpscLinkedQueueNode<T> paramMpscLinkedQueueNode)
/*    */   {
/* 47 */     nextUpdater.lazySet(this, paramMpscLinkedQueueNode);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected T clearMaybe()
/*    */   {
/* 56 */     return (T)value();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   void unlink()
/*    */   {
/* 63 */     setNext(null);
/*    */   }
/*    */   
/*    */   public abstract T value();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\MpscLinkedQueueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */