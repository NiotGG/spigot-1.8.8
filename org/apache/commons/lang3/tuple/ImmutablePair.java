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
/*     */ 
/*     */ 
/*     */ public final class ImmutablePair<L, R>
/*     */   extends Pair<L, R>
/*     */ {
/*     */   private static final long serialVersionUID = 4954918890077093841L;
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
/*     */ 
/*     */   public static <L, R> ImmutablePair<L, R> of(L paramL, R paramR)
/*     */   {
/*  58 */     return new ImmutablePair(paramL, paramR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ImmutablePair(L paramL, R paramR)
/*     */   {
/*  69 */     this.left = paramL;
/*  70 */     this.right = paramR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L getLeft()
/*     */   {
/*  79 */     return (L)this.left;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public R getRight()
/*     */   {
/*  87 */     return (R)this.right;
/*     */   }
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
/*     */   public R setValue(R paramR)
/*     */   {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\ImmutablePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */