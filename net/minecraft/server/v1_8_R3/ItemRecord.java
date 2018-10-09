/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ItemRecord extends Item
/*    */ {
/*  8 */   private static final Map<String, ItemRecord> b = ;
/*    */   public final String a;
/*    */   
/*    */   protected ItemRecord(String s) {
/* 12 */     this.a = s;
/* 13 */     this.maxStackSize = 1;
/* 14 */     a(CreativeModeTab.f);
/* 15 */     b.put("records." + s, this);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/* 19 */     IBlockData iblockdata = world.getType(blockposition);
/*    */     
/* 21 */     if ((iblockdata.getBlock() == Blocks.JUKEBOX) && (!((Boolean)iblockdata.get(BlockJukeBox.HAS_RECORD)).booleanValue())) {
/* 22 */       if (world.isClientSide) {
/* 23 */         return true;
/*    */       }
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 33 */       return true;
/*    */     }
/*    */     
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public EnumItemRarity g(ItemStack itemstack)
/*    */   {
/* 41 */     return EnumItemRarity.RARE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */