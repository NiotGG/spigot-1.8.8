/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ public abstract class EntityWaterAnimal
/*    */   extends EntityInsentient
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityWaterAnimal(World paramWorld)
/*    */   {
/* 20 */     super(paramWorld);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean aY()
/*    */   {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public boolean bR()
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public boolean canSpawn()
/*    */   {
/* 36 */     return this.world.a(getBoundingBox(), this);
/*    */   }
/*    */   
/*    */   public int w()
/*    */   {
/* 41 */     return 120;
/*    */   }
/*    */   
/*    */   protected boolean isTypeNotPersistent()
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   protected int getExpValue(EntityHuman paramEntityHuman)
/*    */   {
/* 51 */     return 1 + this.world.random.nextInt(3);
/*    */   }
/*    */   
/*    */   public void K()
/*    */   {
/* 56 */     int i = getAirTicks();
/*    */     
/* 58 */     super.K();
/*    */     
/* 60 */     if ((isAlive()) && (!V())) {
/* 61 */       setAirTicks(--i);
/* 62 */       if (getAirTicks() == -20) {
/* 63 */         setAirTicks(0);
/* 64 */         damageEntity(DamageSource.DROWN, 2.0F);
/*    */       }
/*    */     } else {
/* 67 */       setAirTicks(300);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean aL()
/*    */   {
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityWaterAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */