/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiffResult
/*     */   implements Iterable<Diff<?>>
/*     */ {
/*     */   public static final String OBJECTS_SAME_STRING = "";
/*     */   private static final String DIFFERS_STRING = "differs from";
/*     */   private final List<Diff<?>> diffs;
/*     */   private final Object lhs;
/*     */   private final Object rhs;
/*     */   private final ToStringStyle style;
/*     */   
/*     */   DiffResult(Object paramObject1, Object paramObject2, List<Diff<?>> paramList, ToStringStyle paramToStringStyle)
/*     */   {
/*  75 */     if (paramObject1 == null) {
/*  76 */       throw new IllegalArgumentException("Left hand object cannot be null");
/*     */     }
/*     */     
/*  79 */     if (paramObject2 == null) {
/*  80 */       throw new IllegalArgumentException("Right hand object cannot be null");
/*     */     }
/*     */     
/*  83 */     if (paramList == null) {
/*  84 */       throw new IllegalArgumentException("List of differences cannot be null");
/*     */     }
/*     */     
/*     */ 
/*  88 */     this.diffs = paramList;
/*  89 */     this.lhs = paramObject1;
/*  90 */     this.rhs = paramObject2;
/*     */     
/*  92 */     if (paramToStringStyle == null) {
/*  93 */       this.style = ToStringStyle.DEFAULT_STYLE;
/*     */     } else {
/*  95 */       this.style = paramToStringStyle;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Diff<?>> getDiffs()
/*     */   {
/* 108 */     return Collections.unmodifiableList(this.diffs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNumberOfDiffs()
/*     */   {
/* 119 */     return this.diffs.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ToStringStyle getToStringStyle()
/*     */   {
/* 130 */     return this.style;
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
/*     */   public String toString()
/*     */   {
/* 166 */     return toString(this.style);
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
/*     */   public String toString(ToStringStyle paramToStringStyle)
/*     */   {
/* 181 */     if (this.diffs.size() == 0) {
/* 182 */       return "";
/*     */     }
/*     */     
/* 185 */     ToStringBuilder localToStringBuilder1 = new ToStringBuilder(this.lhs, paramToStringStyle);
/* 186 */     ToStringBuilder localToStringBuilder2 = new ToStringBuilder(this.rhs, paramToStringStyle);
/*     */     
/* 188 */     for (Diff localDiff : this.diffs) {
/* 189 */       localToStringBuilder1.append(localDiff.getFieldName(), localDiff.getLeft());
/* 190 */       localToStringBuilder2.append(localDiff.getFieldName(), localDiff.getRight());
/*     */     }
/*     */     
/* 193 */     return String.format("%s %s %s", new Object[] { localToStringBuilder1.build(), "differs from", localToStringBuilder2.build() });
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
/*     */   public Iterator<Diff<?>> iterator()
/*     */   {
/* 206 */     return this.diffs.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\DiffResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */