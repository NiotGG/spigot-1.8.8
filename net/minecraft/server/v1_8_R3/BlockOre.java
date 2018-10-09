/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockOre extends Block
/*    */ {
/*    */   public BlockOre() {
/*  8 */     this(Material.STONE.r());
/*    */   }
/*    */   
/*    */   public BlockOre(MaterialMapColor materialmapcolor) {
/* 12 */     super(Material.STONE, materialmapcolor);
/* 13 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 17 */     return this == Blocks.QUARTZ_ORE ? Items.QUARTZ : this == Blocks.EMERALD_ORE ? Items.EMERALD : this == Blocks.LAPIS_ORE ? Items.DYE : this == Blocks.DIAMOND_ORE ? Items.DIAMOND : this == Blocks.COAL_ORE ? Items.COAL : Item.getItemOf(this);
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 21 */     return this == Blocks.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
/*    */   }
/*    */   
/*    */   public int getDropCount(int i, Random random) {
/* 25 */     if ((i > 0) && (Item.getItemOf(this) != getDropType((IBlockData)P().a().iterator().next(), random, i))) {
/* 26 */       int j = random.nextInt(i + 2) - 1;
/*    */       
/* 28 */       if (j < 0) {
/* 29 */         j = 0;
/*    */       }
/*    */       
/* 32 */       return a(random) * (j + 1);
/*    */     }
/* 34 */     return a(random);
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i)
/*    */   {
/* 39 */     super.dropNaturally(world, blockposition, iblockdata, f, i);
/*    */   }
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
/*    */   public int getExpDrop(World world, IBlockData iblockdata, int i)
/*    */   {
/* 64 */     if (getDropType(iblockdata, world.random, i) != Item.getItemOf(this)) {
/* 65 */       int j = 0;
/*    */       
/* 67 */       if (this == Blocks.COAL_ORE) {
/* 68 */         j = MathHelper.nextInt(world.random, 0, 2);
/* 69 */       } else if (this == Blocks.DIAMOND_ORE) {
/* 70 */         j = MathHelper.nextInt(world.random, 3, 7);
/* 71 */       } else if (this == Blocks.EMERALD_ORE) {
/* 72 */         j = MathHelper.nextInt(world.random, 3, 7);
/* 73 */       } else if (this == Blocks.LAPIS_ORE) {
/* 74 */         j = MathHelper.nextInt(world.random, 2, 5);
/* 75 */       } else if (this == Blocks.QUARTZ_ORE) {
/* 76 */         j = MathHelper.nextInt(world.random, 2, 5);
/*    */       }
/*    */       
/* 79 */       return j;
/*    */     }
/*    */     
/* 82 */     return 0;
/*    */   }
/*    */   
/*    */   public int getDropData(World world, BlockPosition blockposition)
/*    */   {
/* 87 */     return 0;
/*    */   }
/*    */   
/*    */   public int getDropData(IBlockData iblockdata) {
/* 91 */     return this == Blocks.LAPIS_ORE ? EnumColor.BLUE.getInvColorIndex() : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockOre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */