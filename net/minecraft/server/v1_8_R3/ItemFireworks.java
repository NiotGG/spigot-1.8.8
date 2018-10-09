/*    */ package net.minecraft.server.v1_8_R3;
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
/*    */ 
/*    */ public class ItemFireworks
/*    */   extends Item
/*    */ {
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 38 */     if (!paramWorld.isClientSide) {
/* 39 */       EntityFireworks localEntityFireworks = new EntityFireworks(paramWorld, paramBlockPosition.getX() + paramFloat1, paramBlockPosition.getY() + paramFloat2, paramBlockPosition.getZ() + paramFloat3, paramItemStack);
/* 40 */       paramWorld.addEntity(localEntityFireworks);
/*    */       
/* 42 */       if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 43 */         paramItemStack.count -= 1;
/*    */       }
/* 45 */       return true;
/*    */     }
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */