/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ItemSaddle
/*    */   extends Item
/*    */ {
/*    */   public ItemSaddle()
/*    */   {
/*  9 */     this.maxStackSize = 1;
/* 10 */     a(CreativeModeTab.e);
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving)
/*    */   {
/* 15 */     if ((paramEntityLiving instanceof EntityPig)) {
/* 16 */       EntityPig localEntityPig = (EntityPig)paramEntityLiving;
/* 17 */       if ((!localEntityPig.hasSaddle()) && (!localEntityPig.isBaby())) {
/* 18 */         localEntityPig.setSaddle(true);
/* 19 */         localEntityPig.world.makeSound(localEntityPig, "mob.horse.leather", 0.5F, 1.0F);
/* 20 */         paramItemStack.count -= 1;
/*    */       }
/*    */       
/* 23 */       return true;
/*    */     }
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
/*    */   {
/* 30 */     a(paramItemStack, null, paramEntityLiving1);
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSaddle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */