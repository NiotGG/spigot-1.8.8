/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RegistrySimple<K, V> implements IRegistry<K, V>
/*    */ {
/* 15 */   private static final Logger a = ;
/* 16 */   protected final Map<K, V> c = b();
/*    */   
/*    */   protected Map<K, V> b() {
/* 19 */     return Maps.newHashMap();
/*    */   }
/*    */   
/*    */   public V get(K paramK)
/*    */   {
/* 24 */     return (V)this.c.get(paramK);
/*    */   }
/*    */   
/*    */   public void a(K paramK, V paramV)
/*    */   {
/* 29 */     Validate.notNull(paramK);
/* 30 */     Validate.notNull(paramV);
/*    */     
/* 32 */     if (this.c.containsKey(paramK)) {
/* 33 */       a.debug("Adding duplicate key '" + paramK + "' to registry");
/*    */     }
/* 35 */     this.c.put(paramK, paramV);
/*    */   }
/*    */   
/*    */   public Set<K> keySet()
/*    */   {
/* 40 */     return Collections.unmodifiableSet(this.c.keySet());
/*    */   }
/*    */   
/*    */   public boolean d(K paramK) {
/* 44 */     return this.c.containsKey(paramK);
/*    */   }
/*    */   
/*    */   public Iterator<V> iterator()
/*    */   {
/* 49 */     return this.c.values().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegistrySimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */