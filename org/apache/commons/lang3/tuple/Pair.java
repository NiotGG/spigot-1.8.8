/*     */ package org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Map.Entry;
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
/*     */ public abstract class Pair<L, R>
/*     */   implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4954918890077093841L;
/*     */   
/*     */   public static <L, R> Pair<L, R> of(L paramL, R paramR)
/*     */   {
/*  60 */     return new ImmutablePair(paramL, paramR);
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
/*     */   public abstract L getLeft();
/*     */   
/*     */ 
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
/*     */ 
/*     */   public final L getKey()
/*     */   {
/*  92 */     return (L)getLeft();
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
/*     */   public R getValue()
/*     */   {
/* 105 */     return (R)getRight();
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
/*     */   public int compareTo(Pair<L, R> paramPair)
/*     */   {
/* 118 */     return new CompareToBuilder().append(getLeft(), paramPair.getLeft()).append(getRight(), paramPair.getRight()).toComparison();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 131 */     if (paramObject == this) {
/* 132 */       return true;
/*     */     }
/* 134 */     if ((paramObject instanceof Map.Entry)) {
/* 135 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 136 */       return (ObjectUtils.equals(getKey(), localEntry.getKey())) && (ObjectUtils.equals(getValue(), localEntry.getValue()));
/*     */     }
/*     */     
/* 139 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 151 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 162 */     return '(' + getLeft() + ',' + getRight() + ')';
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
/*     */   public String toString(String paramString)
/*     */   {
/* 177 */     return String.format(paramString, new Object[] { getLeft(), getRight() });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\tuple\Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */