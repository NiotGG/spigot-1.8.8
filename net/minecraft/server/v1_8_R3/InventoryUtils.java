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
/*    */ public class InventoryUtils
/*    */ {
/* 13 */   private static final Random a = new Random();
/*    */   
/*    */   public static void dropInventory(World paramWorld, BlockPosition paramBlockPosition, IInventory paramIInventory) {
/* 16 */     dropInventory(paramWorld, paramBlockPosition.getX(), paramBlockPosition.getY(), paramBlockPosition.getZ(), paramIInventory);
/*    */   }
/*    */   
/*    */   public static void dropEntity(World paramWorld, Entity paramEntity, IInventory paramIInventory) {
/* 20 */     dropInventory(paramWorld, paramEntity.locX, paramEntity.locY, paramEntity.locZ, paramIInventory);
/*    */   }
/*    */   
/*    */   private static void dropInventory(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, IInventory paramIInventory) {
/* 24 */     for (int i = 0; i < paramIInventory.getSize(); i++) {
/* 25 */       ItemStack localItemStack = paramIInventory.getItem(i);
/* 26 */       if (localItemStack != null)
/*    */       {
/*    */ 
/*    */ 
/* 30 */         dropItem(paramWorld, paramDouble1, paramDouble2, paramDouble3, localItemStack); }
/*    */     }
/*    */   }
/*    */   
/*    */   private static void dropItem(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramItemStack) {
/* 35 */     float f1 = a.nextFloat() * 0.8F + 0.1F;
/* 36 */     float f2 = a.nextFloat() * 0.8F + 0.1F;
/* 37 */     float f3 = a.nextFloat() * 0.8F + 0.1F;
/*    */     
/* 39 */     while (paramItemStack.count > 0) {
/* 40 */       int i = a.nextInt(21) + 10;
/* 41 */       if (i > paramItemStack.count) {
/* 42 */         i = paramItemStack.count;
/*    */       }
/* 44 */       paramItemStack.count -= i;
/*    */       
/* 46 */       EntityItem localEntityItem = new EntityItem(paramWorld, paramDouble1 + f1, paramDouble2 + f2, paramDouble3 + f3, new ItemStack(paramItemStack.getItem(), i, paramItemStack.getData()));
/* 47 */       if (paramItemStack.hasTag()) {
/* 48 */         localEntityItem.getItemStack().setTag((NBTTagCompound)paramItemStack.getTag().clone());
/*    */       }
/*    */       
/* 51 */       float f4 = 0.05F;
/* 52 */       localEntityItem.motX = (a.nextGaussian() * f4);
/* 53 */       localEntityItem.motY = (a.nextGaussian() * f4 + 0.20000000298023224D);
/* 54 */       localEntityItem.motZ = (a.nextGaussian() * f4);
/*    */       
/* 56 */       paramWorld.addEntity(localEntityItem);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */