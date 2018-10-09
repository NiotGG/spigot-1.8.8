/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockRedstoneLamp extends Block
/*    */ {
/*    */   private final boolean a;
/*    */   
/*    */   public BlockRedstoneLamp(boolean flag)
/*    */   {
/* 12 */     super(Material.BUILDABLE_GLASS);
/* 13 */     this.a = flag;
/* 14 */     if (flag) {
/* 15 */       a(1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*    */   {
/* 21 */     if (!world.isClientSide) {
/* 22 */       if ((this.a) && (!world.isBlockIndirectlyPowered(blockposition))) {
/* 23 */         world.setTypeAndData(blockposition, Blocks.REDSTONE_LAMP.getBlockData(), 2);
/* 24 */       } else if ((!this.a) && (world.isBlockIndirectlyPowered(blockposition)))
/*    */       {
/* 26 */         if (CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 0, 15).getNewCurrent() != 15) {
/* 27 */           return;
/*    */         }
/*    */         
/* 30 */         world.setTypeAndData(blockposition, Blocks.LIT_REDSTONE_LAMP.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*    */   {
/* 37 */     if (!world.isClientSide) {
/* 38 */       if ((this.a) && (!world.isBlockIndirectlyPowered(blockposition))) {
/* 39 */         world.a(blockposition, this, 4);
/* 40 */       } else if ((!this.a) && (world.isBlockIndirectlyPowered(blockposition)))
/*    */       {
/* 42 */         if (CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 0, 15).getNewCurrent() != 15) {
/* 43 */           return;
/*    */         }
/*    */         
/* 46 */         world.setTypeAndData(blockposition, Blocks.LIT_REDSTONE_LAMP.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*    */   {
/* 53 */     if ((!world.isClientSide) && 
/* 54 */       (this.a) && (!world.isBlockIndirectlyPowered(blockposition)))
/*    */     {
/* 56 */       if (CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 15, 0).getNewCurrent() != 0) {
/* 57 */         return;
/*    */       }
/*    */       
/* 60 */       world.setTypeAndData(blockposition, Blocks.REDSTONE_LAMP.getBlockData(), 2);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*    */   {
/* 67 */     return Item.getItemOf(Blocks.REDSTONE_LAMP);
/*    */   }
/*    */   
/*    */   protected ItemStack i(IBlockData iblockdata) {
/* 71 */     return new ItemStack(Blocks.REDSTONE_LAMP);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedstoneLamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */