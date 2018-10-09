/*    */ package com.avaje.ebean.common;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
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
/*    */ class ModifyHolder<E>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2572572897923801083L;
/* 43 */   private Set<E> modifyDeletions = new LinkedHashSet();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 48 */   private Set<E> modifyAdditions = new LinkedHashSet();
/*    */   
/*    */   void reset() {
/* 51 */     this.modifyDeletions = new LinkedHashSet();
/* 52 */     this.modifyAdditions = new LinkedHashSet();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   void modifyAdditionAll(Collection<? extends E> c)
/*    */   {
/* 59 */     if (c != null) {
/* 60 */       for (E e : c) {
/* 61 */         modifyAddition(e);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   void modifyAddition(E bean) {
/* 67 */     if (bean != null)
/*    */     {
/* 69 */       if (!this.modifyDeletions.remove(bean))
/*    */       {
/* 71 */         this.modifyAdditions.add(bean);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   void modifyRemoval(Object bean)
/*    */   {
/* 78 */     if (bean != null)
/*    */     {
/* 80 */       if (!this.modifyAdditions.remove(bean)) {
/* 81 */         this.modifyDeletions.add(bean);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   Set<E> getModifyAdditions() {
/* 87 */     return this.modifyAdditions;
/*    */   }
/*    */   
/*    */   Set<E> getModifyRemovals() {
/* 91 */     return this.modifyDeletions;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\ModifyHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */