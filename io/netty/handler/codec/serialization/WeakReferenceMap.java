/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Map;
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
/*    */ final class WeakReferenceMap<K, V>
/*    */   extends ReferenceMap<K, V>
/*    */ {
/*    */   WeakReferenceMap(Map<K, Reference<V>> paramMap)
/*    */   {
/* 25 */     super(paramMap);
/*    */   }
/*    */   
/*    */   Reference<V> fold(V paramV)
/*    */   {
/* 30 */     return new WeakReference(paramV);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\WeakReferenceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */