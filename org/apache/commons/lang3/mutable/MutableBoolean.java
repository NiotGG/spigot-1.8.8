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
/*     */ 
/*     */ 
/*     */ public class MutableBoolean
/*     */   implements Mutable<Boolean>, Serializable, Comparable<MutableBoolean>
/*     */ {
/*     */   private static final long serialVersionUID = -4830728138360036487L;
/*     */   private boolean value;
/*     */   
/*     */   public MutableBoolean() {}
/*     */   
/*     */   public MutableBoolean(boolean paramBoolean)
/*     */   {
/*  57 */     this.value = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutableBoolean(Boolean paramBoolean)
/*     */   {
/*  68 */     this.value = paramBoolean.booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean getValue()
/*     */   {
/*  79 */     return Boolean.valueOf(this.value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(boolean paramBoolean)
/*     */   {
/*  88 */     this.value = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFalse()
/*     */   {
/*  97 */     this.value = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTrue()
/*     */   {
/* 106 */     this.value = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Boolean paramBoolean)
/*     */   {
/* 117 */     this.value = paramBoolean.booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTrue()
/*     */   {
/* 128 */     return this.value == true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFalse()
/*     */   {
/* 138 */     return !this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean booleanValue()
/*     */   {
/* 148 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean toBoolean()
/*     */   {
/* 159 */     return Boolean.valueOf(booleanValue());
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
/* 173 */     if ((paramObject instanceof MutableBoolean)) {
/* 174 */       return this.value == ((MutableBoolean)paramObject).booleanValue();
/*     */     }
/* 176 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 186 */     return this.value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
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
/*     */   public int compareTo(MutableBoolean paramMutableBoolean)
/*     */   {
/* 199 */     boolean bool = paramMutableBoolean.value;
/* 200 */     return this.value ? 1 : this.value == bool ? 0 : -1;
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
/* 211 */     return String.valueOf(this.value);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\mutable\MutableBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */