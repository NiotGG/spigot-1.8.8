/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible(serializable=true)
/*    */ class ImmutableEntry<K, V>
/*    */   extends AbstractMapEntry<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   final K key;
/*    */   final V value;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ImmutableEntry(@Nullable K key, @Nullable V value)
/*    */   {
/* 35 */     this.key = key;
/* 36 */     this.value = value;
/*    */   }
/*    */   
/*    */   @Nullable
/* 40 */   public final K getKey() { return (K)this.key; }
/*    */   
/*    */   @Nullable
/*    */   public final V getValue() {
/* 44 */     return (V)this.value;
/*    */   }
/*    */   
/*    */   public final V setValue(V value) {
/* 48 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */