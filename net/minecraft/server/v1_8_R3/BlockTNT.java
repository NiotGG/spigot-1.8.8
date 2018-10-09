/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*   5 */ public class BlockTNT extends Block { public static final BlockStateBoolean EXPLODE = BlockStateBoolean.of("explode");
/*     */   
/*     */   public BlockTNT() {
/*   8 */     super(Material.TNT);
/*   9 */     j(this.blockStateList.getBlockData().set(EXPLODE, Boolean.valueOf(false)));
/*  10 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  14 */     super.onPlace(world, blockposition, iblockdata);
/*  15 */     if (world.isBlockIndirectlyPowered(blockposition)) {
/*  16 */       postBreak(world, blockposition, iblockdata.set(EXPLODE, Boolean.valueOf(true)));
/*  17 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  23 */     if (world.isBlockIndirectlyPowered(blockposition)) {
/*  24 */       postBreak(world, blockposition, iblockdata.set(EXPLODE, Boolean.valueOf(true)));
/*  25 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void wasExploded(World world, BlockPosition blockposition, Explosion explosion)
/*     */   {
/*  31 */     if (!world.isClientSide) {
/*  32 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockposition.getX() + 0.5F, blockposition.getY(), blockposition.getZ() + 0.5F, explosion.getSource());
/*     */       
/*  34 */       entitytntprimed.fuseTicks = (world.random.nextInt(entitytntprimed.fuseTicks / 4) + entitytntprimed.fuseTicks / 8);
/*  35 */       world.addEntity(entitytntprimed);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postBreak(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  40 */     a(world, blockposition, iblockdata, null);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving) {
/*  44 */     if ((!world.isClientSide) && 
/*  45 */       (((Boolean)iblockdata.get(EXPLODE)).booleanValue())) {
/*  46 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockposition.getX() + 0.5F, blockposition.getY(), blockposition.getZ() + 0.5F, entityliving);
/*     */       
/*  48 */       world.addEntity(entitytntprimed);
/*  49 */       world.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  56 */     if (entityhuman.bZ() != null) {
/*  57 */       Item item = entityhuman.bZ().getItem();
/*     */       
/*  59 */       if ((item == Items.FLINT_AND_STEEL) || (item == Items.FIRE_CHARGE)) {
/*  60 */         a(world, blockposition, iblockdata.set(EXPLODE, Boolean.valueOf(true)), entityhuman);
/*  61 */         world.setAir(blockposition);
/*  62 */         if (item == Items.FLINT_AND_STEEL) {
/*  63 */           entityhuman.bZ().damage(1, entityhuman);
/*  64 */         } else if (!entityhuman.abilities.canInstantlyBuild) {
/*  65 */           entityhuman.bZ().count -= 1;
/*     */         }
/*     */         
/*  68 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  72 */     return super.interact(world, blockposition, iblockdata, entityhuman, enumdirection, f, f1, f2);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/*  76 */     if ((!world.isClientSide) && ((entity instanceof EntityArrow))) {
/*  77 */       EntityArrow entityarrow = (EntityArrow)entity;
/*     */       
/*  79 */       if (entityarrow.isBurning())
/*     */       {
/*  81 */         if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityChangeBlockEvent(entityarrow, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled()) {
/*  82 */           return;
/*     */         }
/*     */         
/*  85 */         a(world, blockposition, world.getType(blockposition).set(EXPLODE, Boolean.valueOf(true)), (entityarrow.shooter instanceof EntityLiving) ? (EntityLiving)entityarrow.shooter : null);
/*  86 */         world.setAir(blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(Explosion explosion)
/*     */   {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  97 */     return getBlockData().set(EXPLODE, Boolean.valueOf((i & 0x1) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 101 */     return ((Boolean)iblockdata.get(EXPLODE)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 105 */     return new BlockStateList(this, new IBlockState[] { EXPLODE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */