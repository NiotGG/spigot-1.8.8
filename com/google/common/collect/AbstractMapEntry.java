/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Objects;
/*    */ import java.util.Map.Entry;
/*    */ import javax.annotation.Nullable;
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
/*    */ @GwtCompatible
/*    */ abstract class AbstractMapEntry<K, V>
/*    */   implements Map.Entry<K, V>
/*    */ {
/*    */   public abstract K getKey();
/*    */   
/*    */   public abstract V getValue();
/*    */   
/*    */   public V setValue(V value)
/*    */   {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 47 */     if ((object instanceof Map.Entry)) {
/* 48 */       Map.Entry<?, ?> that = (Map.Entry)object;
/* 49 */       return (Objects.equal(getKey(), that.getKey())) && (Objects.equal(getValue(), that.getValue()));
/*    */     }
/*    */     
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 56 */     K k = getKey();
/* 57 */     V v = getValue();
/* 58 */     return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 65 */     return getKey() + "=" + getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractMapEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */