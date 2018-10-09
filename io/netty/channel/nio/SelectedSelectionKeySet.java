/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
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
/*     */ final class SelectedSelectionKeySet
/*     */   extends AbstractSet<SelectionKey>
/*     */ {
/*     */   private SelectionKey[] keysA;
/*     */   private int keysASize;
/*     */   private SelectionKey[] keysB;
/*     */   private int keysBSize;
/*  29 */   private boolean isA = true;
/*     */   
/*     */   SelectedSelectionKeySet() {
/*  32 */     this.keysA = new SelectionKey['Ѐ'];
/*  33 */     this.keysB = ((SelectionKey[])this.keysA.clone());
/*     */   }
/*     */   
/*     */   public boolean add(SelectionKey paramSelectionKey)
/*     */   {
/*  38 */     if (paramSelectionKey == null) {
/*  39 */       return false;
/*     */     }
/*     */     int i;
/*  42 */     if (this.isA) {
/*  43 */       i = this.keysASize;
/*  44 */       this.keysA[(i++)] = paramSelectionKey;
/*  45 */       this.keysASize = i;
/*  46 */       if (i == this.keysA.length) {
/*  47 */         doubleCapacityA();
/*     */       }
/*     */     } else {
/*  50 */       i = this.keysBSize;
/*  51 */       this.keysB[(i++)] = paramSelectionKey;
/*  52 */       this.keysBSize = i;
/*  53 */       if (i == this.keysB.length) {
/*  54 */         doubleCapacityB();
/*     */       }
/*     */     }
/*     */     
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   private void doubleCapacityA() {
/*  62 */     SelectionKey[] arrayOfSelectionKey = new SelectionKey[this.keysA.length << 1];
/*  63 */     System.arraycopy(this.keysA, 0, arrayOfSelectionKey, 0, this.keysASize);
/*  64 */     this.keysA = arrayOfSelectionKey;
/*     */   }
/*     */   
/*     */   private void doubleCapacityB() {
/*  68 */     SelectionKey[] arrayOfSelectionKey = new SelectionKey[this.keysB.length << 1];
/*  69 */     System.arraycopy(this.keysB, 0, arrayOfSelectionKey, 0, this.keysBSize);
/*  70 */     this.keysB = arrayOfSelectionKey;
/*     */   }
/*     */   
/*     */   SelectionKey[] flip() {
/*  74 */     if (this.isA) {
/*  75 */       this.isA = false;
/*  76 */       this.keysA[this.keysASize] = null;
/*  77 */       this.keysBSize = 0;
/*  78 */       return this.keysA;
/*     */     }
/*  80 */     this.isA = true;
/*  81 */     this.keysB[this.keysBSize] = null;
/*  82 */     this.keysASize = 0;
/*  83 */     return this.keysB;
/*     */   }
/*     */   
/*     */ 
/*     */   public int size()
/*     */   {
/*  89 */     if (this.isA) {
/*  90 */       return this.keysASize;
/*     */     }
/*  92 */     return this.keysBSize;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public Iterator<SelectionKey> iterator()
/*     */   {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\SelectedSelectionKeySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */