/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class InsensitiveStringMap<V> implements Map<String, V>
/*    */ {
/* 10 */   private final Map<String, V> a = com.google.common.collect.Maps.newLinkedHashMap();
/*    */   
/*    */   public int size()
/*    */   {
/* 14 */     return this.a.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty()
/*    */   {
/* 19 */     return this.a.isEmpty();
/*    */   }
/*    */   
/*    */   public boolean containsKey(Object paramObject)
/*    */   {
/* 24 */     return this.a.containsKey(paramObject.toString().toLowerCase());
/*    */   }
/*    */   
/*    */   public boolean containsValue(Object paramObject)
/*    */   {
/* 29 */     return this.a.containsKey(paramObject);
/*    */   }
/*    */   
/*    */   public V get(Object paramObject)
/*    */   {
/* 34 */     return (V)this.a.get(paramObject.toString().toLowerCase());
/*    */   }
/*    */   
/*    */   public V a(String paramString, V paramV)
/*    */   {
/* 39 */     return (V)this.a.put(paramString.toLowerCase(), paramV);
/*    */   }
/*    */   
/*    */   public V remove(Object paramObject)
/*    */   {
/* 44 */     return (V)this.a.remove(paramObject.toString().toLowerCase());
/*    */   }
/*    */   
/*    */   public void putAll(Map<? extends String, ? extends V> paramMap)
/*    */   {
/* 49 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 50 */       a((String)localEntry.getKey(), localEntry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public void clear()
/*    */   {
/* 56 */     this.a.clear();
/*    */   }
/*    */   
/*    */   public Set<String> keySet()
/*    */   {
/* 61 */     return this.a.keySet();
/*    */   }
/*    */   
/*    */   public Collection<V> values()
/*    */   {
/* 66 */     return this.a.values();
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<String, V>> entrySet()
/*    */   {
/* 71 */     return this.a.entrySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InsensitiveStringMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */