/*     */ package org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.commons.lang3.builder.CompareToBuilder;
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
/*     */ public abstract class Triple<L, M, R>
/*     */   implements Comparable<Triple<L, M, R>>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static <L, M, R> Triple<L, M, R> of(L paramL, M paramM, R paramR)
/*     */   {
/*  61 */     return new ImmutableTriple(paramL, paramM, paramR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract L getLeft();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract M getMiddle();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract R getRight();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int compareTo(Triple<L, M, R> paramTriple)
/*     */   {
/*  97 */     return new CompareToBuilder().append(getLeft(), paramTriple.getLeft()).append(getMiddle(), paramTriple.getMiddle()).append(getRight(), paramTriple.getRight()).toComparison();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 111 */     if (paramObject == this) {
/* 112 */       return true;
/*     */     }
/* 114 */     if ((paramObject instanceof Triple)) {
/* 115 */       Triple localTriple = (Triple)paramObject;
/* 116 */       return (ObjectUtils.equals(getLeft(), localTriple.getLeft())) && (ObjectUtils.equals(getMiddle(), localTriple.getMiddle())) && (ObjectUtils.equals(getRight(), localTriple.getRight()));
/*     */     }
/*     */     
/*     */ 
/* 120 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 130 */     return (getLeft() == null ? 0 : getLeft().hashCode()) ^ (getMiddle() == null ? 0 : getMiddle().hashCode()) ^ (getRight() == null ? 0 : getRight().hashCode());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 142 */     return '(' + getLeft() + ',' + getMiddle() + ',' + getRight() + ')';
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
/*     */ 
/*     */ 
/*     */   public String toString(String paramString)
/*     */   {
/* 158 */     return String.format(paramString, new Object[] { getLeft(), getMiddle(), getRight() });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\Triple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */