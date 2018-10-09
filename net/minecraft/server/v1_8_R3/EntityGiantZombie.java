/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityGiantZombie
/*    */   extends EntityMonster
/*    */ {
/*    */   public EntityGiantZombie(World paramWorld)
/*    */   {
/* 10 */     super(paramWorld);
/* 11 */     setSize(this.width * 6.0F, this.length * 6.0F);
/*    */   }
/*    */   
/*    */   public float getHeadHeight()
/*    */   {
/* 16 */     return 10.440001F;
/*    */   }
/*    */   
/*    */   protected void initAttributes()
/*    */   {
/* 21 */     super.initAttributes();
/*    */     
/* 23 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
/* 24 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
/* 25 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(50.0D);
/*    */   }
/*    */   
/*    */   public float a(BlockPosition paramBlockPosition)
/*    */   {
/* 30 */     return this.world.o(paramBlockPosition) - 0.5F;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityGiantZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */