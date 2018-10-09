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
/*    */ abstract class MpscLinkedQueueTailRef<E>
/*    */   extends MpscLinkedQueuePad1<E>
/*    */ {
/*    */   private static final long serialVersionUID = 8717072462993327429L;
/*    */   private static final AtomicReferenceFieldUpdater<MpscLinkedQueueTailRef, MpscLinkedQueueNode> UPDATER;
/*    */   private volatile transient MpscLinkedQueueNode<E> tailRef;
/*    */   
/*    */   static
/*    */   {
/* 31 */     AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueTailRef.class, "tailRef");
/* 32 */     if (localAtomicReferenceFieldUpdater == null) {
/* 33 */       localAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueTailRef.class, MpscLinkedQueueNode.class, "tailRef");
/*    */     }
/*    */     
/* 36 */     UPDATER = localAtomicReferenceFieldUpdater;
/*    */   }
/*    */   
/*    */ 
/*    */   protected final MpscLinkedQueueNode<E> tailRef()
/*    */   {
/* 42 */     return this.tailRef;
/*    */   }
/*    */   
/*    */   protected final void setTailRef(MpscLinkedQueueNode<E> paramMpscLinkedQueueNode) {
/* 46 */     this.tailRef = paramMpscLinkedQueueNode;
/*    */   }
/*    */   
/*    */ 
/*    */   protected final MpscLinkedQueueNode<E> getAndSetTailRef(MpscLinkedQueueNode<E> paramMpscLinkedQueueNode)
/*    */   {
/* 52 */     return (MpscLinkedQueueNode)UPDATER.getAndSet(this, paramMpscLinkedQueueNode);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\MpscLinkedQueueTailRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */