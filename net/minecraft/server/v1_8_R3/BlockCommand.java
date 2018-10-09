/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockCommand extends BlockContainer
/*     */ {
/*   9 */   public static final BlockStateBoolean TRIGGERED = BlockStateBoolean.of("triggered");
/*     */   
/*     */   public BlockCommand() {
/*  12 */     super(Material.ORE, MaterialMapColor.q);
/*  13 */     j(this.blockStateList.getBlockData().set(TRIGGERED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  17 */     return new TileEntityCommand();
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  21 */     if (!world.isClientSide) {
/*  22 */       boolean flag = world.isBlockIndirectlyPowered(blockposition);
/*  23 */       boolean flag1 = ((Boolean)iblockdata.get(TRIGGERED)).booleanValue();
/*     */       
/*     */ 
/*  26 */       org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  27 */       int old = flag1 ? 15 : 0;
/*  28 */       int current = flag ? 15 : 0;
/*     */       
/*  30 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, old, current);
/*  31 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */       
/*     */ 
/*  34 */       if ((eventRedstone.getNewCurrent() > 0) && (eventRedstone.getOldCurrent() <= 0)) {
/*  35 */         world.setTypeAndData(blockposition, iblockdata.set(TRIGGERED, Boolean.valueOf(true)), 4);
/*  36 */         world.a(blockposition, this, a(world));
/*  37 */       } else if ((eventRedstone.getNewCurrent() <= 0) && (eventRedstone.getOldCurrent() > 0)) {
/*  38 */         world.setTypeAndData(blockposition, iblockdata.set(TRIGGERED, Boolean.valueOf(false)), 4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  45 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  47 */     if ((tileentity instanceof TileEntityCommand)) {
/*  48 */       ((TileEntityCommand)tileentity).getCommandBlock().a(world);
/*  49 */       world.updateAdjacentComparators(blockposition, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(World world)
/*     */   {
/*  55 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  59 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  61 */     return (tileentity instanceof TileEntityCommand) ? ((TileEntityCommand)tileentity).getCommandBlock().a(entityhuman) : false;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/*  65 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/*  69 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  71 */     return (tileentity instanceof TileEntityCommand) ? ((TileEntityCommand)tileentity).getCommandBlock().j() : 0;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/*  75 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  77 */     if ((tileentity instanceof TileEntityCommand)) {
/*  78 */       CommandBlockListenerAbstract commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();
/*     */       
/*  80 */       if (itemstack.hasName()) {
/*  81 */         commandblocklistenerabstract.setName(itemstack.getName());
/*     */       }
/*     */       
/*  84 */       if (!world.isClientSide) {
/*  85 */         commandblocklistenerabstract.a(world.getGameRules().getBoolean("sendCommandFeedback"));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(Random random)
/*     */   {
/*  92 */     return 0;
/*     */   }
/*     */   
/*     */   public int b() {
/*  96 */     return 3;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 100 */     return getBlockData().set(TRIGGERED, Boolean.valueOf((i & 0x1) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 104 */     int i = 0;
/*     */     
/* 106 */     if (((Boolean)iblockdata.get(TRIGGERED)).booleanValue()) {
/* 107 */       i |= 0x1;
/*     */     }
/*     */     
/* 110 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 114 */     return new BlockStateList(this, new IBlockState[] { TRIGGERED });
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/* 118 */     return getBlockData().set(TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */