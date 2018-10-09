/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class EntityComplexPart
/*    */   extends Entity
/*    */ {
/*    */   public final IComplex owner;
/*    */   public final String b;
/*    */   
/*    */   public EntityComplexPart(IComplex paramIComplex, String paramString, float paramFloat1, float paramFloat2)
/*    */   {
/* 12 */     super(paramIComplex.a());
/* 13 */     setSize(paramFloat1, paramFloat2);
/* 14 */     this.owner = paramIComplex;
/* 15 */     this.b = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void h() {}
/*    */   
/*    */ 
/*    */ 
/*    */   protected void a(NBTTagCompound paramNBTTagCompound) {}
/*    */   
/*    */ 
/*    */   protected void b(NBTTagCompound paramNBTTagCompound) {}
/*    */   
/*    */ 
/*    */   public boolean ad()
/*    */   {
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
/*    */   {
/* 37 */     if (isInvulnerable(paramDamageSource)) {
/* 38 */       return false;
/*    */     }
/* 40 */     return this.owner.a(this, paramDamageSource, paramFloat);
/*    */   }
/*    */   
/*    */   public boolean k(Entity paramEntity)
/*    */   {
/* 45 */     return (this == paramEntity) || (this.owner == paramEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityComplexPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */