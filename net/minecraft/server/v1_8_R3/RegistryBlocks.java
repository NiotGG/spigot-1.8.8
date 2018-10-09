/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class RegistryBlocks<K, V> extends RegistryMaterials<K, V>
/*    */ {
/*    */   private final K d;
/*    */   private V e;
/*    */   
/*    */   public RegistryBlocks(K paramK)
/*    */   {
/* 12 */     this.d = paramK;
/*    */   }
/*    */   
/*    */   public void a(int paramInt, K paramK, V paramV)
/*    */   {
/* 17 */     if (this.d.equals(paramK)) {
/* 18 */       this.e = paramV;
/*    */     }
/*    */     
/* 21 */     super.a(paramInt, paramK, paramV);
/*    */   }
/*    */   
/*    */   public void a() {
/* 25 */     Validate.notNull(this.d);
/*    */   }
/*    */   
/*    */   public V get(K paramK)
/*    */   {
/* 30 */     Object localObject = super.get(paramK);
/* 31 */     return (V)(localObject == null ? this.e : localObject);
/*    */   }
/*    */   
/*    */ 
/*    */   public V a(int paramInt)
/*    */   {
/* 37 */     Object localObject = super.a(paramInt);
/* 38 */     return (V)(localObject == null ? this.e : localObject);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegistryBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */