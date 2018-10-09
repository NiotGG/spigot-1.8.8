/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumCreatureType
/*    */ {
/*    */   private final Class<? extends IAnimal> e;
/*    */   
/*    */ 
/*    */ 
/*    */   private final int f;
/*    */   
/*    */ 
/*    */ 
/*    */   private final Material g;
/*    */   
/*    */ 
/*    */ 
/*    */   private final boolean h;
/*    */   
/*    */ 
/*    */   private final boolean i;
/*    */   
/*    */ 
/*    */ 
/*    */   private EnumCreatureType(Class<? extends IAnimal> paramClass, int paramInt, Material paramMaterial, boolean paramBoolean1, boolean paramBoolean2)
/*    */   {
/* 29 */     this.e = paramClass;
/* 30 */     this.f = paramInt;
/* 31 */     this.g = paramMaterial;
/* 32 */     this.h = paramBoolean1;
/* 33 */     this.i = paramBoolean2;
/*    */   }
/*    */   
/*    */   public Class<? extends IAnimal> a() {
/* 37 */     return this.e;
/*    */   }
/*    */   
/*    */   public int b() {
/* 41 */     return this.f;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean d()
/*    */   {
/* 49 */     return this.h;
/*    */   }
/*    */   
/*    */   public boolean e() {
/* 53 */     return this.i;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumCreatureType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */