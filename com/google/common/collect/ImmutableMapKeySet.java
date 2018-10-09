/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.annotations.GwtIncompatible;
/*    */ import java.io.Serializable;
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
/*    */ @GwtCompatible(emulated=true)
/*    */ final class ImmutableMapKeySet<K, V>
/*    */   extends ImmutableSet<K>
/*    */ {
/*    */   private final ImmutableMap<K, V> map;
/*    */   
/*    */   ImmutableMapKeySet(ImmutableMap<K, V> map)
/*    */   {
/* 38 */     this.map = map;
/*    */   }
/*    */   
/*    */   public int size()
/*    */   {
/* 43 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public UnmodifiableIterator<K> iterator()
/*    */   {
/* 48 */     return asList().iterator();
/*    */   }
/*    */   
/*    */   public boolean contains(@Nullable Object object)
/*    */   {
/* 53 */     return this.map.containsKey(object);
/*    */   }
/*    */   
/*    */   ImmutableList<K> createAsList()
/*    */   {
/* 58 */     final ImmutableList<Map.Entry<K, V>> entryList = this.map.entrySet().asList();
/* 59 */     new ImmutableAsList()
/*    */     {
/*    */       public K get(int index)
/*    */       {
/* 63 */         return (K)((Map.Entry)entryList.get(index)).getKey();
/*    */       }
/*    */       
/*    */       ImmutableCollection<K> delegateCollection()
/*    */       {
/* 68 */         return ImmutableMapKeySet.this;
/*    */       }
/*    */     };
/*    */   }
/*    */   
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 76 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   @GwtIncompatible("serialization")
/* 81 */   Object writeReplace() { return new KeySetSerializedForm(this.map); }
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   private static class KeySetSerializedForm<K> implements Serializable {
/*    */     final ImmutableMap<K, ?> map;
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/* 88 */     KeySetSerializedForm(ImmutableMap<K, ?> map) { this.map = map; }
/*    */     
/*    */     Object readResolve() {
/* 91 */       return this.map.keySet();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableMapKeySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */