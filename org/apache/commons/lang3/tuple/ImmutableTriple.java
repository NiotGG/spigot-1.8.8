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
/*     */ 
/*     */ public final class ImmutableTriple<L, M, R>
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
/*     */ 
/*     */   public final L left;
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
/*     */   public final M middle;
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
/*     */   public final R right;
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
/*     */   public static <L, M, R> ImmutableTriple<L, M, R> of(L paramL, M paramM, R paramR)
/*     */   {
/*  63 */     return new ImmutableTriple(paramL, paramM, paramR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ImmutableTriple(L paramL, M paramM, R paramR)
/*     */   {
/*  75 */     this.left = paramL;
/*  76 */     this.middle = paramM;
/*  77 */     this.right = paramR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L getLeft()
/*     */   {
/*  86 */     return (L)this.left;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public M getMiddle()
/*     */   {
/*  94 */     return (M)this.middle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public R getRight()
/*     */   {
/* 102 */     return (R)this.right;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\ImmutableTriple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */