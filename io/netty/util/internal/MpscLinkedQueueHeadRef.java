/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ abstract class MpscLinkedQueueHeadRef<E>
/*    */   extends MpscLinkedQueuePad0<E>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8467054865577874285L;
/*    */   private static final AtomicReferenceFieldUpdater<MpscLinkedQueueHeadRef, MpscLinkedQueueNode> UPDATER;
/*    */   private volatile transient MpscLinkedQueueNode<E> headRef;
/*    */   
/*    */   static
/*    */   {
/* 33 */     AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(MpscLinkedQueueHeadRef.class, "headRef");
/* 34 */     if (localAtomicReferenceFieldUpdater == null) {
/* 35 */       localAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueueHeadRef.class, MpscLinkedQueueNode.class, "headRef");
/*    */     }
/*    */     
/* 38 */     UPDATER = localAtomicReferenceFieldUpdater;
/*    */   }
/*    */   
/*    */ 
/*    */   protected final MpscLinkedQueueNode<E> headRef()
/*    */   {
/* 44 */     return this.headRef;
/*    */   }
/*    */   
/*    */   protected final void setHeadRef(MpscLinkedQueueNode<E> paramMpscLinkedQueueNode) {
/* 48 */     this.headRef = paramMpscLinkedQueueNode;
/*    */   }
/*    */   
/*    */   protected final void lazySetHeadRef(MpscLinkedQueueNode<E> paramMpscLinkedQueueNode) {
/* 52 */     UPDATER.lazySet(this, paramMpscLinkedQueueNode);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\MpscLinkedQueueHeadRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */