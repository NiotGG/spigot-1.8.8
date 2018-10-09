/*    */ package org.spigotmc;
/*    */ 
/*    */ import gnu.trove.map.hash.TCustomHashMap;
/*    */ 
/*    */ public class CaseInsensitiveMap<V> extends TCustomHashMap<String, V>
/*    */ {
/*    */   public CaseInsensitiveMap()
/*    */   {
/*  9 */     super(CaseInsensitiveHashingStrategy.INSTANCE);
/*    */   }
/*    */   
/*    */   public CaseInsensitiveMap(java.util.Map<? extends String, ? extends V> map) {
/* 13 */     super(CaseInsensitiveHashingStrategy.INSTANCE, map);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\CaseInsensitiveMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */