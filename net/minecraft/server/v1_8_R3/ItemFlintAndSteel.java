/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ public class ItemFlintAndSteel extends Item
/*    */ {
/*    */   public ItemFlintAndSteel()
/*    */   {
/* 11 */     this.maxStackSize = 1;
/* 12 */     setMaxDurability(64);
/* 13 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/* 17 */     BlockPosition clicked = blockposition;
/* 18 */     blockposition = blockposition.shift(enumdirection);
/* 19 */     if (!entityhuman.a(blockposition, enumdirection, itemstack)) {
/* 20 */       return false;
/*    */     }
/* 22 */     if (world.getType(blockposition).getBlock().getMaterial() == Material.AIR)
/*    */     {
/* 24 */       if (CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
/* 25 */         itemstack.damage(1, entityhuman);
/* 26 */         return false;
/*    */       }
/*    */       
/* 29 */       CraftBlockState blockState = CraftBlockState.getBlockState(world, blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*    */       
/* 31 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
/* 32 */       world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*    */       
/*    */ 
/* 35 */       BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clicked.getX(), clicked.getY(), clicked.getZ());
/*    */       
/* 37 */       if ((placeEvent.isCancelled()) || (!placeEvent.canBuild())) {
/* 38 */         placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
/* 39 */         return false;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 44 */     itemstack.damage(1, entityhuman);
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFlintAndSteel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */