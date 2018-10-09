/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapGeneratorUtils
/*    */ {
/*    */   public static <K, V> Map<K, V> b(Iterable<K> paramIterable, Iterable<V> paramIterable1)
/*    */   {
/* 15 */     return a(paramIterable, paramIterable1, Maps.newLinkedHashMap());
/*    */   }
/*    */   
/*    */   public static <K, V> Map<K, V> a(Iterable<K> paramIterable, Iterable<V> paramIterable1, Map<K, V> paramMap) {
/* 19 */     Iterator localIterator1 = paramIterable1.iterator();
/* 20 */     for (Object localObject : paramIterable) {
/* 21 */       paramMap.put(localObject, localIterator1.next());
/*    */     }
/*    */     
/* 24 */     if (localIterator1.hasNext()) {
/* 25 */       throw new NoSuchElementException();
/*    */     }
/*    */     
/* 28 */     return paramMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MapGeneratorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */