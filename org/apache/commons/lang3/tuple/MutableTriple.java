/*     */ package org.apache.commons.lang3.tuple;
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
/*     */ public class MutableTriple<L, M, R>
/*     */   extends Triple<L, M, R>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L left;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public M middle;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public R right;
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
/*     */   public static <L, M, R> MutableTriple<L, M, R> of(L paramL, M paramM, R paramR)
/*     */   {
/*  58 */     return new MutableTriple(paramL, paramM, paramR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutableTriple() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutableTriple(L paramL, M paramM, R paramR)
/*     */   {
/*  77 */     this.left = paramL;
/*  78 */     this.middle = paramM;
/*  79 */     this.right = paramR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L getLeft()
/*     */   {
/*  88 */     return (L)this.left;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLeft(L paramL)
/*     */   {
/*  97 */     this.left = paramL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public M getMiddle()
/*     */   {
/* 105 */     return (M)this.middle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMiddle(M paramM)
/*     */   {
/* 114 */     this.middle = paramM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public R getRight()
/*     */   {
/* 122 */     return (R)this.right;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRight(R paramR)
/*     */   {
/* 131 */     this.right = paramR;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\MutableTriple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */