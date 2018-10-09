/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStatePredicate
/*    */   implements Predicate<IBlockData>
/*    */ {
/*    */   private final BlockStateList a;
/* 15 */   private final Map<IBlockState, Predicate> b = Maps.newHashMap();
/*    */   
/*    */   private BlockStatePredicate(BlockStateList paramBlockStateList) {
/* 18 */     this.a = paramBlockStateList;
/*    */   }
/*    */   
/*    */   public static BlockStatePredicate a(Block paramBlock) {
/* 22 */     return new BlockStatePredicate(paramBlock.P());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean a(IBlockData paramIBlockData)
/*    */   {
/* 32 */     if ((paramIBlockData == null) || (!paramIBlockData.getBlock().equals(this.a.getBlock()))) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     for (Map.Entry localEntry : this.b.entrySet()) {
/* 37 */       Comparable localComparable = paramIBlockData.get((IBlockState)localEntry.getKey());
/* 38 */       if (!((Predicate)localEntry.getValue()).apply(localComparable)) {
/* 39 */         return false;
/*    */       }
/*    */     }
/*    */     
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   public <V extends Comparable<V>> BlockStatePredicate a(IBlockState<V> paramIBlockState, Predicate<? extends V> paramPredicate) {
/* 47 */     if (!this.a.d().contains(paramIBlockState)) {
/* 48 */       throw new IllegalArgumentException(this.a + " cannot support property " + paramIBlockState);
/*    */     }
/* 50 */     this.b.put(paramIBlockState, paramPredicate);
/* 51 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStatePredicate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */