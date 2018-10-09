/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EntityMinecartRideable
/*    */   extends EntityMinecartAbstract
/*    */ {
/*    */   public EntityMinecartRideable(World paramWorld)
/*    */   {
/*  8 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartRideable(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 12 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */   
/*    */   public boolean e(EntityHuman paramEntityHuman)
/*    */   {
/* 17 */     if ((this.passenger != null) && ((this.passenger instanceof EntityHuman)) && (this.passenger != paramEntityHuman)) {
/* 18 */       return true;
/*    */     }
/* 20 */     if ((this.passenger != null) && (this.passenger != paramEntityHuman)) {
/* 21 */       return false;
/*    */     }
/* 23 */     if (!this.world.isClientSide) {
/* 24 */       paramEntityHuman.mount(this);
/*    */     }
/*    */     
/* 27 */     return true;
/*    */   }
/*    */   
/*    */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 32 */     if (paramBoolean) {
/* 33 */       if (this.passenger != null) {
/* 34 */         this.passenger.mount(null);
/*    */       }
/* 36 */       if (getType() == 0) {
/* 37 */         k(-r());
/* 38 */         j(10);
/* 39 */         setDamage(50.0F);
/* 40 */         ac();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public EntityMinecartAbstract.EnumMinecartType s()
/*    */   {
/* 47 */     return EntityMinecartAbstract.EnumMinecartType.RIDEABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartRideable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */