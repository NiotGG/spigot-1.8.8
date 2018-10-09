/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class ItemSpade
/*    */   extends ItemTool
/*    */ {
/* 10 */   private static final Set<Block> c = Sets.newHashSet(new Block[] { Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND });
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
/*    */   public ItemSpade(Item.EnumToolMaterial paramEnumToolMaterial)
/*    */   {
/* 24 */     super(1.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock)
/*    */   {
/* 29 */     if (paramBlock == Blocks.SNOW_LAYER) {
/* 30 */       return true;
/*    */     }
/* 32 */     if (paramBlock == Blocks.SNOW) {
/* 33 */       return true;
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSpade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */