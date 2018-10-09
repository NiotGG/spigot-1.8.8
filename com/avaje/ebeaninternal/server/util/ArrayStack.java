/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EmptyStackException;
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
/*     */ public class ArrayStack<E>
/*     */ {
/*     */   private final ArrayList<E> list;
/*     */   
/*     */   public ArrayStack(int size)
/*     */   {
/*  38 */     this.list = new ArrayList(size);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayStack()
/*     */   {
/*  45 */     this.list = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E push(E item)
/*     */   {
/*  52 */     this.list.add(item);
/*  53 */     return item;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E pop()
/*     */   {
/*  61 */     int len = this.list.size();
/*  62 */     E obj = peek();
/*  63 */     this.list.remove(len - 1);
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   protected E peekZero(boolean retNull) {
/*  68 */     int len = this.list.size();
/*  69 */     if (len == 0) {
/*  70 */       if (retNull) {
/*  71 */         return null;
/*     */       }
/*  73 */       throw new EmptyStackException();
/*     */     }
/*  75 */     return (E)this.list.get(len - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public E peek()
/*     */   {
/*  82 */     return (E)peekZero(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public E peekWithNull()
/*     */   {
/*  90 */     return (E)peekZero(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  97 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public int size() {
/* 101 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 105 */     return this.list.contains(o);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\util\ArrayStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */