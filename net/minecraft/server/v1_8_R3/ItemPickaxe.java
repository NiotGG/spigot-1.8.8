/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPickaxe
/*    */   extends ItemTool
/*    */ {
/* 11 */   private static final Set<Block> c = Sets.newHashSet(new Block[] { Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB });
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
/*    */   protected ItemPickaxe(Item.EnumToolMaterial paramEnumToolMaterial)
/*    */   {
/* 40 */     super(2.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock)
/*    */   {
/* 45 */     if (paramBlock == Blocks.OBSIDIAN) {
/* 46 */       return this.b.d() == 3;
/*    */     }
/* 48 */     if ((paramBlock == Blocks.DIAMOND_BLOCK) || (paramBlock == Blocks.DIAMOND_ORE)) {
/* 49 */       return this.b.d() >= 2;
/*    */     }
/* 51 */     if ((paramBlock == Blocks.EMERALD_ORE) || (paramBlock == Blocks.EMERALD_BLOCK)) {
/* 52 */       return this.b.d() >= 2;
/*    */     }
/* 54 */     if ((paramBlock == Blocks.GOLD_BLOCK) || (paramBlock == Blocks.GOLD_ORE)) {
/* 55 */       return this.b.d() >= 2;
/*    */     }
/* 57 */     if ((paramBlock == Blocks.IRON_BLOCK) || (paramBlock == Blocks.IRON_ORE)) {
/* 58 */       return this.b.d() >= 1;
/*    */     }
/* 60 */     if ((paramBlock == Blocks.LAPIS_BLOCK) || (paramBlock == Blocks.LAPIS_ORE)) {
/* 61 */       return this.b.d() >= 1;
/*    */     }
/* 63 */     if ((paramBlock == Blocks.REDSTONE_ORE) || (paramBlock == Blocks.LIT_REDSTONE_ORE)) {
/* 64 */       return this.b.d() >= 2;
/*    */     }
/* 66 */     if (paramBlock.getMaterial() == Material.STONE) {
/* 67 */       return true;
/*    */     }
/* 69 */     if (paramBlock.getMaterial() == Material.ORE) {
/* 70 */       return true;
/*    */     }
/* 72 */     if (paramBlock.getMaterial() == Material.HEAVY) {
/* 73 */       return true;
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
/*    */   {
/* 80 */     if ((paramBlock.getMaterial() == Material.ORE) || (paramBlock.getMaterial() == Material.HEAVY) || (paramBlock.getMaterial() == Material.STONE)) {
/* 81 */       return this.a;
/*    */     }
/* 83 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemPickaxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */