/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemAxe
/*    */   extends ItemTool
/*    */ {
/* 11 */   private static final Set<Block> c = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER });
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
/*    */   protected ItemAxe(Item.EnumToolMaterial paramEnumToolMaterial)
/*    */   {
/* 24 */     super(3.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
/*    */   {
/* 29 */     if ((paramBlock.getMaterial() == Material.WOOD) || (paramBlock.getMaterial() == Material.PLANT) || (paramBlock.getMaterial() == Material.REPLACEABLE_PLANT)) {
/* 30 */       return this.a;
/*    */     }
/* 32 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */