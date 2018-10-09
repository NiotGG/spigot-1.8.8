/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ 
/*     */ public class BlockMonsterEggs
/*     */   extends Block
/*     */ {
/*   9 */   public static final BlockStateEnum<EnumMonsterEggVarient> VARIANT = BlockStateEnum.of("variant", EnumMonsterEggVarient.class);
/*     */   
/*     */   public BlockMonsterEggs() {
/*  12 */     super(Material.CLAY);
/*  13 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumMonsterEggVarient.STONE));
/*  14 */     c(0.0F);
/*  15 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  19 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean d(IBlockData iblockdata) {
/*  23 */     Block block = iblockdata.getBlock();
/*     */     
/*  25 */     return (iblockdata == Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE)) || (block == Blocks.COBBLESTONE) || (block == Blocks.STONEBRICK);
/*     */   }
/*     */   
/*     */   protected ItemStack i(IBlockData iblockdata) {
/*  29 */     switch (SyntheticClass_1.a[((EnumMonsterEggVarient)iblockdata.get(VARIANT)).ordinal()]) {
/*     */     case 1: 
/*  31 */       return new ItemStack(Blocks.COBBLESTONE);
/*     */     
/*     */     case 2: 
/*  34 */       return new ItemStack(Blocks.STONEBRICK);
/*     */     
/*     */     case 3: 
/*  37 */       return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.MOSSY.a());
/*     */     
/*     */     case 4: 
/*  40 */       return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CRACKED.a());
/*     */     
/*     */     case 5: 
/*  43 */       return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CHISELED.a());
/*     */     }
/*     */     
/*  46 */     return new ItemStack(Blocks.STONE);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i)
/*     */   {
/*  51 */     if ((!world.isClientSide) && (world.getGameRules().getBoolean("doTileDrops"))) {
/*  52 */       EntitySilverfish entitysilverfish = new EntitySilverfish(world);
/*     */       
/*  54 */       entitysilverfish.setPositionRotation(blockposition.getX() + 0.5D, blockposition.getY(), blockposition.getZ() + 0.5D, 0.0F, 0.0F);
/*  55 */       world.addEntity(entitysilverfish, CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK);
/*  56 */       entitysilverfish.y();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(World world, BlockPosition blockposition)
/*     */   {
/*  62 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/*  64 */     return iblockdata.getBlock().toLegacyData(iblockdata);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  68 */     return getBlockData().set(VARIANT, EnumMonsterEggVarient.a(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/*  72 */     return ((EnumMonsterEggVarient)iblockdata.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/*  76 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/*  81 */     static final int[] a = new int[BlockMonsterEggs.EnumMonsterEggVarient.values().length];
/*     */     
/*     */     static {
/*     */       try {
/*  85 */         a[BlockMonsterEggs.EnumMonsterEggVarient.COBBLESTONE.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/*  91 */         a[BlockMonsterEggs.EnumMonsterEggVarient.STONEBRICK.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/*  97 */         a[BlockMonsterEggs.EnumMonsterEggVarient.MOSSY_STONEBRICK.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 103 */         a[BlockMonsterEggs.EnumMonsterEggVarient.CRACKED_STONEBRICK.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 109 */         a[BlockMonsterEggs.EnumMonsterEggVarient.CHISELED_STONEBRICK.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static abstract enum EnumMonsterEggVarient
/*     */     implements INamable
/*     */   {
/* 119 */     STONE(0, "stone"), 
/*     */     
/*     */ 
/*     */ 
/* 123 */     COBBLESTONE(1, "cobblestone", "cobble"), 
/*     */     
/*     */ 
/*     */ 
/* 127 */     STONEBRICK(2, "stone_brick", "brick"), 
/*     */     
/*     */ 
/*     */ 
/* 131 */     MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick"), 
/*     */     
/*     */ 
/*     */ 
/* 135 */     CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick"), 
/*     */     
/*     */ 
/*     */ 
/* 139 */     CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick");
/*     */     
/*     */ 
/*     */     private static final EnumMonsterEggVarient[] g;
/*     */     
/*     */     private final int h;
/*     */     
/*     */     private final String i;
/*     */     private final String j;
/*     */     
/*     */     private EnumMonsterEggVarient(int i, String s)
/*     */     {
/* 151 */       this(i, s, s);
/*     */     }
/*     */     
/*     */     private EnumMonsterEggVarient(int i, String s, String s1) {
/* 155 */       this.h = i;
/* 156 */       this.i = s;
/* 157 */       this.j = s1;
/*     */     }
/*     */     
/*     */     public int a() {
/* 161 */       return this.h;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 165 */       return this.i;
/*     */     }
/*     */     
/*     */     public static EnumMonsterEggVarient a(int i) {
/* 169 */       if ((i < 0) || (i >= g.length)) {
/* 170 */         i = 0;
/*     */       }
/*     */       
/* 173 */       return g[i];
/*     */     }
/*     */     
/*     */     public String getName() {
/* 177 */       return this.i;
/*     */     }
/*     */     
/*     */     public String c() {
/* 181 */       return this.j;
/*     */     }
/*     */     
/*     */ 
/*     */     public static EnumMonsterEggVarient a(IBlockData iblockdata)
/*     */     {
/* 187 */       EnumMonsterEggVarient[] ablockmonstereggs_enummonstereggvarient = values();
/* 188 */       int i = ablockmonstereggs_enummonstereggvarient.length;
/*     */       
/* 190 */       for (int j = 0; j < i; j++) {
/* 191 */         EnumMonsterEggVarient blockmonstereggs_enummonstereggvarient = ablockmonstereggs_enummonstereggvarient[j];
/*     */         
/* 193 */         if (iblockdata == blockmonstereggs_enummonstereggvarient.d()) {
/* 194 */           return blockmonstereggs_enummonstereggvarient;
/*     */         }
/*     */       }
/*     */       
/* 198 */       return STONE;
/*     */     }
/*     */     
/*     */     private EnumMonsterEggVarient(int i, String s, BlockMonsterEggs.SyntheticClass_1 blockmonstereggs_syntheticclass_1) {
/* 202 */       this(i, s);
/*     */     }
/*     */     
/*     */     private EnumMonsterEggVarient(int i, String s, String s1, BlockMonsterEggs.SyntheticClass_1 blockmonstereggs_syntheticclass_1) {
/* 206 */       this(i, s, s1);
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 145 */       g = new EnumMonsterEggVarient[values().length];
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 210 */       EnumMonsterEggVarient[] ablockmonstereggs_enummonstereggvarient = values();
/* 211 */       int i = ablockmonstereggs_enummonstereggvarient.length;
/*     */       
/* 213 */       for (int j = 0; j < i; j++) {
/* 214 */         EnumMonsterEggVarient blockmonstereggs_enummonstereggvarient = ablockmonstereggs_enummonstereggvarient[j];
/*     */         
/* 216 */         g[blockmonstereggs_enummonstereggvarient.a()] = blockmonstereggs_enummonstereggvarient;
/*     */       }
/*     */     }
/*     */     
/*     */     public abstract IBlockData d();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMonsterEggs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */