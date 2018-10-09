/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ 
/*    */ 
/*    */ public class BlockPredicate
/*    */   implements Predicate<IBlockData>
/*    */ {
/*    */   private final Block a;
/*    */   
/*    */   private BlockPredicate(Block paramBlock)
/*    */   {
/* 13 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public static BlockPredicate a(Block paramBlock) {
/* 17 */     return new BlockPredicate(paramBlock);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean a(IBlockData paramIBlockData)
/*    */   {
/* 23 */     if ((paramIBlockData == null) || (paramIBlockData.getBlock() != this.a)) {
/* 24 */       return false;
/*    */     }
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPredicate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */