/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ import org.bukkit.event.block.BlockFadeEvent;
/*    */ import org.bukkit.event.block.BlockSpreadEvent;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class BlockMycel
/*    */   extends Block
/*    */ {
/* 14 */   public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");
/*    */   
/*    */   protected BlockMycel() {
/* 17 */     super(Material.GRASS, MaterialMapColor.z);
/* 18 */     j(this.blockStateList.getBlockData().set(SNOWY, Boolean.valueOf(false)));
/* 19 */     a(true);
/* 20 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 24 */     Block block = iblockaccess.getType(blockposition.up()).getBlock();
/*    */     
/* 26 */     return iblockdata.set(SNOWY, Boolean.valueOf((block == Blocks.SNOW) || (block == Blocks.SNOW_LAYER)));
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 30 */     if (!world.isClientSide) {
/* 31 */       if ((world.getLightLevel(blockposition.up()) < 4) && (world.getType(blockposition.up()).getBlock().p() > 2))
/*    */       {
/*    */ 
/* 34 */         org.bukkit.World bworld = world.getWorld();
/* 35 */         BlockState blockState = bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
/* 36 */         blockState.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
/*    */         
/* 38 */         BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
/* 39 */         world.getServer().getPluginManager().callEvent(event);
/*    */         
/* 41 */         if (!event.isCancelled()) {
/* 42 */           blockState.update(true);
/*    */         }
/*    */         
/*    */       }
/* 46 */       else if (world.getLightLevel(blockposition.up()) >= 9) {
/* 47 */         for (int i = 0; i < Math.min(4, Math.max(20, (int)(400.0F / world.growthOdds))); i++) {
/* 48 */           BlockPosition blockposition1 = blockposition.a(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
/* 49 */           IBlockData iblockdata1 = world.getType(blockposition1);
/* 50 */           Block block = world.getType(blockposition1.up()).getBlock();
/*    */           
/* 52 */           if ((iblockdata1.getBlock() == Blocks.DIRT) && (iblockdata1.get(BlockDirt.VARIANT) == BlockDirt.EnumDirtVariant.DIRT) && (world.getLightLevel(blockposition1.up()) >= 4) && (block.p() <= 2))
/*    */           {
/*    */ 
/* 55 */             org.bukkit.World bworld = world.getWorld();
/* 56 */             BlockState blockState = bworld.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
/* 57 */             blockState.setType(CraftMagicNumbers.getMaterial(this));
/*    */             
/* 59 */             BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), blockState);
/* 60 */             world.getServer().getPluginManager().callEvent(event);
/*    */             
/* 62 */             if (!event.isCancelled()) {
/* 63 */               blockState.update(true);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*    */   {
/* 75 */     return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), random, i);
/*    */   }
/*    */   
/*    */   public int toLegacyData(IBlockData iblockdata) {
/* 79 */     return 0;
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList() {
/* 83 */     return new BlockStateList(this, new IBlockState[] { SNOWY });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMycel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */