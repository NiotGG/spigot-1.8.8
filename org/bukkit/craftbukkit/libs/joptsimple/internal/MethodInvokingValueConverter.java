/*    */ package org.bukkit.craftbukkit.libs.joptsimple.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.ValueConverter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MethodInvokingValueConverter<V>
/*    */   implements ValueConverter<V>
/*    */ {
/*    */   private final Method method;
/*    */   private final Class<V> clazz;
/*    */   
/*    */   MethodInvokingValueConverter(Method method, Class<V> clazz)
/*    */   {
/* 43 */     this.method = method;
/* 44 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public V convert(String value) {
/* 48 */     return (V)this.clazz.cast(Reflection.invoke(this.method, new Object[] { value }));
/*    */   }
/*    */   
/*    */   public Class<V> valueType() {
/* 52 */     return this.clazz;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\internal\MethodInvokingValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */