/*     */ package io.netty.util;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ @Deprecated
/*     */ public class UniqueName
/*     */   implements Comparable<UniqueName>
/*     */ {
/*  29 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */   
/*     */ 
/*     */ 
/*     */   private final int id;
/*     */   
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */ 
/*     */   public UniqueName(ConcurrentMap<String, Boolean> paramConcurrentMap, String paramString, Object... paramVarArgs)
/*     */   {
/*  42 */     if (paramConcurrentMap == null) {
/*  43 */       throw new NullPointerException("map");
/*     */     }
/*  45 */     if (paramString == null) {
/*  46 */       throw new NullPointerException("name");
/*     */     }
/*  48 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0)) {
/*  49 */       validateArgs(paramVarArgs);
/*     */     }
/*     */     
/*  52 */     if (paramConcurrentMap.putIfAbsent(paramString, Boolean.TRUE) != null) {
/*  53 */       throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[] { paramString }));
/*     */     }
/*     */     
/*  56 */     this.id = nextId.incrementAndGet();
/*  57 */     this.name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void validateArgs(Object... paramVarArgs) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String name()
/*     */   {
/*  77 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int id()
/*     */   {
/*  86 */     return this.id;
/*     */   }
/*     */   
/*     */   public final int hashCode()
/*     */   {
/*  91 */     return super.hashCode();
/*     */   }
/*     */   
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/*  96 */     return super.equals(paramObject);
/*     */   }
/*     */   
/*     */   public int compareTo(UniqueName paramUniqueName)
/*     */   {
/* 101 */     if (this == paramUniqueName) {
/* 102 */       return 0;
/*     */     }
/*     */     
/* 105 */     int i = this.name.compareTo(paramUniqueName.name);
/* 106 */     if (i != 0) {
/* 107 */       return i;
/*     */     }
/*     */     
/* 110 */     return Integer.valueOf(this.id).compareTo(Integer.valueOf(paramUniqueName.id));
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 115 */     return name();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\UniqueName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */