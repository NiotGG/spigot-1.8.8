/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegistryMaterials<K, V>
/*    */   extends RegistrySimple<K, V> implements Registry<V>
/*    */ {
/* 11 */   protected final RegistryID<V> a = new RegistryID();
/*    */   
/*    */   protected final Map<V, K> b;
/*    */   
/*    */   public RegistryMaterials()
/*    */   {
/* 17 */     this.b = ((BiMap)this.c).inverse();
/*    */   }
/*    */   
/*    */   public void a(int paramInt, K paramK, V paramV) {
/* 21 */     this.a.a(paramV, paramInt);
/* 22 */     a(paramK, paramV);
/*    */   }
/*    */   
/*    */   protected Map<K, V> b()
/*    */   {
/* 27 */     return HashBiMap.create();
/*    */   }
/*    */   
/*    */ 
/*    */   public V get(K paramK)
/*    */   {
/* 33 */     return (V)super.get(paramK);
/*    */   }
/*    */   
/*    */   public K c(V paramV)
/*    */   {
/* 38 */     return (K)this.b.get(paramV);
/*    */   }
/*    */   
/*    */   public boolean d(K paramK)
/*    */   {
/* 43 */     return super.d(paramK);
/*    */   }
/*    */   
/*    */   public int b(V paramV)
/*    */   {
/* 48 */     return this.a.b(paramV);
/*    */   }
/*    */   
/*    */ 
/*    */   public V a(int paramInt)
/*    */   {
/* 54 */     return (V)this.a.a(paramInt);
/*    */   }
/*    */   
/*    */   public Iterator<V> iterator()
/*    */   {
/* 59 */     return this.a.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegistryMaterials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */