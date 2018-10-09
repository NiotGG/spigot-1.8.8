/*     */ package org.apache.commons.io.comparator;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
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
/*     */ public class CompositeFileComparator
/*     */   extends AbstractFileComparator
/*     */   implements Serializable
/*     */ {
/*  47 */   private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
/*     */   
/*     */ 
/*     */ 
/*     */   private final Comparator<File>[] delegates;
/*     */   
/*     */ 
/*     */ 
/*     */   public CompositeFileComparator(Comparator<File>... paramVarArgs)
/*     */   {
/*  57 */     if (paramVarArgs == null) {
/*  58 */       this.delegates = ((Comparator[])NO_COMPARATORS);
/*     */     } else {
/*  60 */       this.delegates = ((Comparator[])new Comparator[paramVarArgs.length]);
/*  61 */       System.arraycopy(paramVarArgs, 0, this.delegates, 0, paramVarArgs.length);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CompositeFileComparator(Iterable<Comparator<File>> paramIterable)
/*     */   {
/*  72 */     if (paramIterable == null) {
/*  73 */       this.delegates = ((Comparator[])NO_COMPARATORS);
/*     */     } else {
/*  75 */       ArrayList localArrayList = new ArrayList();
/*  76 */       for (Comparator localComparator : paramIterable) {
/*  77 */         localArrayList.add(localComparator);
/*     */       }
/*  79 */       this.delegates = ((Comparator[])localArrayList.toArray(new Comparator[localArrayList.size()]));
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
/*     */   public int compare(File paramFile1, File paramFile2)
/*     */   {
/*  92 */     int i = 0;
/*  93 */     for (Comparator localComparator : this.delegates) {
/*  94 */       i = localComparator.compare(paramFile1, paramFile2);
/*  95 */       if (i != 0) {
/*     */         break;
/*     */       }
/*     */     }
/*  99 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     StringBuilder localStringBuilder = new StringBuilder();
/* 110 */     localStringBuilder.append(super.toString());
/* 111 */     localStringBuilder.append('{');
/* 112 */     for (int i = 0; i < this.delegates.length; i++) {
/* 113 */       if (i > 0) {
/* 114 */         localStringBuilder.append(',');
/*     */       }
/* 116 */       localStringBuilder.append(this.delegates[i]);
/*     */     }
/* 118 */     localStringBuilder.append('}');
/* 119 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\comparator\CompositeFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */