/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeLiteral<T>
/*     */   implements Typed<T>
/*     */ {
/*  78 */   private static final TypeVariable<Class<TypeLiteral>> T = TypeLiteral.class.getTypeParameters()[0];
/*     */   
/*     */ 
/*     */ 
/*     */   public final Type value;
/*     */   
/*     */ 
/*     */   private final String toString;
/*     */   
/*     */ 
/*     */ 
/*     */   protected TypeLiteral()
/*     */   {
/*  91 */     this.value = ((Type)Validate.notNull(TypeUtils.getTypeArguments(getClass(), TypeLiteral.class).get(T), "%s does not assign type parameter %s", new Object[] { getClass(), TypeUtils.toLongString(T) }));
/*     */     
/*     */ 
/*     */ 
/*  95 */     this.toString = String.format("%s<%s>", new Object[] { TypeLiteral.class.getSimpleName(), TypeUtils.toString(this.value) });
/*     */   }
/*     */   
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/* 100 */     if (paramObject == this) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (!(paramObject instanceof TypeLiteral)) {
/* 104 */       return false;
/*     */     }
/* 106 */     TypeLiteral localTypeLiteral = (TypeLiteral)paramObject;
/* 107 */     return TypeUtils.equals(this.value, localTypeLiteral.value);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 112 */     return 0x250 | this.value.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 117 */     return this.toString;
/*     */   }
/*     */   
/*     */   public Type getType()
/*     */   {
/* 122 */     return this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\TypeLiteral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */