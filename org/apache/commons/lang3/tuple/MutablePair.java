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
/*     */ public class MutablePair<L, R>
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
/*     */ 
/*     */   public static <L, R> MutablePair<L, R> of(L paramL, R paramR)
/*     */   {
/*  53 */     return new MutablePair(paramL, paramR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutablePair() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutablePair(L paramL, R paramR)
/*     */   {
/*  71 */     this.left = paramL;
/*  72 */     this.right = paramR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L getLeft()
/*     */   {
/*  81 */     return (L)this.left;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLeft(L paramL)
/*     */   {
/*  90 */     this.left = paramL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public R getRight()
/*     */   {
/*  98 */     return (R)this.right;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRight(R paramR)
/*     */   {
/* 107 */     this.right = paramR;
/*     */   }
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
/* 119 */     Object localObject = getRight();
/* 120 */     setRight(paramR);
/* 121 */     return (R)localObject;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\MutablePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */