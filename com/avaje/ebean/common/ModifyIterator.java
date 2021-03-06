/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import java.util.Iterator;
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
/*    */ class ModifyIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   private final BeanCollection<E> owner;
/*    */   private final Iterator<E> it;
/*    */   private E last;
/*    */   
/*    */   ModifyIterator(BeanCollection<E> owner, Iterator<E> it)
/*    */   {
/* 49 */     this.owner = owner;
/* 50 */     this.it = it;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 54 */     return this.it.hasNext();
/*    */   }
/*    */   
/*    */   public E next() {
/* 58 */     this.last = this.it.next();
/* 59 */     return (E)this.last;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 63 */     this.owner.modifyRemoval(this.last);
/* 64 */     this.it.remove();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\ModifyIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */