/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class RegistryDefault<K, V> extends RegistrySimple<K, V>
/*    */ {
/*    */   private final V a;
/*    */   
/*    */   public RegistryDefault(V paramV)
/*    */   {
/*  9 */     this.a = paramV;
/*    */   }
/*    */   
/*    */   public V get(K paramK)
/*    */   {
/* 14 */     Object localObject = super.get(paramK);
/* 15 */     return (V)(localObject == null ? this.a : localObject);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegistryDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */