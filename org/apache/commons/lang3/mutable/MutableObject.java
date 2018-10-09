/*     */ package org.apache.commons.lang3.mutable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableObject<T>
/*     */   implements Mutable<T>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 86241875189L;
/*     */   private T value;
/*     */   
/*     */   public MutableObject() {}
/*     */   
/*     */   public MutableObject(T paramT)
/*     */   {
/*  55 */     this.value = paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getValue()
/*     */   {
/*  66 */     return (T)this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(T paramT)
/*     */   {
/*  76 */     this.value = paramT;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  94 */     if (paramObject == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     if (this == paramObject) {
/*  98 */       return true;
/*     */     }
/* 100 */     if (getClass() == paramObject.getClass()) {
/* 101 */       MutableObject localMutableObject = (MutableObject)paramObject;
/* 102 */       return this.value.equals(localMutableObject.value);
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 114 */     return this.value == null ? 0 : this.value.hashCode();
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
/* 125 */     return this.value == null ? "null" : this.value.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\mutable\MutableObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */