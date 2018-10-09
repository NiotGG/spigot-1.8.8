/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class ItemArmorStand
/*    */   extends Item
/*    */ {
/*    */   public ItemArmorStand()
/*    */   {
/* 20 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 25 */     if (paramEnumDirection == EnumDirection.DOWN) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     boolean bool = paramWorld.getType(paramBlockPosition).getBlock().a(paramWorld, paramBlockPosition);
/* 30 */     BlockPosition localBlockPosition1 = bool ? paramBlockPosition : paramBlockPosition.shift(paramEnumDirection);
/* 31 */     if (!paramEntityHuman.a(localBlockPosition1, paramEnumDirection, paramItemStack)) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     BlockPosition localBlockPosition2 = localBlockPosition1.up();
/* 36 */     int i = (!paramWorld.isEmpty(localBlockPosition1)) && (!paramWorld.getType(localBlockPosition1).getBlock().a(paramWorld, localBlockPosition1)) ? 1 : 0;
/* 37 */     i |= ((!paramWorld.isEmpty(localBlockPosition2)) && (!paramWorld.getType(localBlockPosition2).getBlock().a(paramWorld, localBlockPosition2)) ? 1 : 0);
/* 38 */     if (i != 0) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     double d1 = localBlockPosition1.getX();
/* 43 */     double d2 = localBlockPosition1.getY();
/* 44 */     double d3 = localBlockPosition1.getZ();
/*    */     
/* 46 */     List localList = paramWorld.getEntities(null, AxisAlignedBB.a(d1, d2, d3, d1 + 1.0D, d2 + 2.0D, d3 + 1.0D));
/* 47 */     if (localList.size() > 0) {
/* 48 */       return false;
/*    */     }
/*    */     
/* 51 */     if (!paramWorld.isClientSide) {
/* 52 */       paramWorld.setAir(localBlockPosition1);
/* 53 */       paramWorld.setAir(localBlockPosition2);
/*    */       
/* 55 */       EntityArmorStand localEntityArmorStand = new EntityArmorStand(paramWorld, d1 + 0.5D, d2, d3 + 0.5D);
/* 56 */       float f = MathHelper.d((MathHelper.g(paramEntityHuman.yaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/* 57 */       localEntityArmorStand.setPositionRotation(d1 + 0.5D, d2, d3 + 0.5D, f, 0.0F);
/* 58 */       a(localEntityArmorStand, paramWorld.random);
/* 59 */       NBTTagCompound localNBTTagCompound1 = paramItemStack.getTag();
/* 60 */       if ((localNBTTagCompound1 != null) && (localNBTTagCompound1.hasKeyOfType("EntityTag", 10))) {
/* 61 */         NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/* 62 */         localEntityArmorStand.d(localNBTTagCompound2);
/* 63 */         localNBTTagCompound2.a(localNBTTagCompound1.getCompound("EntityTag"));
/* 64 */         localEntityArmorStand.f(localNBTTagCompound2);
/*    */       }
/* 66 */       paramWorld.addEntity(localEntityArmorStand);
/*    */     }
/* 68 */     paramItemStack.count -= 1;
/* 69 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void a(EntityArmorStand paramEntityArmorStand, Random paramRandom)
/*    */   {
/* 78 */     Vector3f localVector3f1 = paramEntityArmorStand.t();
/* 79 */     float f1 = paramRandom.nextFloat() * 5.0F;
/* 80 */     float f2 = paramRandom.nextFloat() * 20.0F - 10.0F;
/* 81 */     Vector3f localVector3f2 = new Vector3f(localVector3f1.getX() + f1, localVector3f1.getY() + f2, localVector3f1.getZ());
/* 82 */     paramEntityArmorStand.setHeadPose(localVector3f2);
/*    */     
/* 84 */     localVector3f1 = paramEntityArmorStand.u();
/* 85 */     f1 = paramRandom.nextFloat() * 10.0F - 5.0F;
/* 86 */     localVector3f2 = new Vector3f(localVector3f1.getX(), localVector3f1.getY() + f1, localVector3f1.getZ());
/* 87 */     paramEntityArmorStand.setBodyPose(localVector3f2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemArmorStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */