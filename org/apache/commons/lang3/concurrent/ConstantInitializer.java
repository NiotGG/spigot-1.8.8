/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import org.apache.commons.lang3.ObjectUtils;
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
/*     */ public class ConstantInitializer<T>
/*     */   implements ConcurrentInitializer<T>
/*     */ {
/*     */   private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
/*     */   private final T object;
/*     */   
/*     */   public ConstantInitializer(T paramT)
/*     */   {
/*  58 */     this.object = paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final T getObject()
/*     */   {
/*  69 */     return (T)this.object;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T get()
/*     */     throws ConcurrentException
/*     */   {
/*  81 */     return (T)getObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  92 */     return getObject() != null ? getObject().hashCode() : 0;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 107 */     if (this == paramObject) {
/* 108 */       return true;
/*     */     }
/* 110 */     if (!(paramObject instanceof ConstantInitializer)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     ConstantInitializer localConstantInitializer = (ConstantInitializer)paramObject;
/* 115 */     return ObjectUtils.equals(getObject(), localConstantInitializer.getObject());
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
/* 127 */     return String.format("ConstantInitializer@%d [ object = %s ]", new Object[] { Integer.valueOf(System.identityHashCode(this)), String.valueOf(getObject()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\ConstantInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */