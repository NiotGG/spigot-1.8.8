/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class BlockStateInteger extends BlockState<Integer>
/*    */ {
/*    */   private final ImmutableSet<Integer> a;
/*    */   
/*    */   protected BlockStateInteger(String paramString, int paramInt1, int paramInt2)
/*    */   {
/* 13 */     super(paramString, Integer.class);
/* 14 */     if (paramInt1 < 0) {
/* 15 */       throw new IllegalArgumentException("Min value of " + paramString + " must be 0 or greater");
/*    */     }
/* 17 */     if (paramInt2 <= paramInt1) {
/* 18 */       throw new IllegalArgumentException("Max value of " + paramString + " must be greater than min (" + paramInt1 + ")");
/*    */     }
/*    */     
/* 21 */     java.util.HashSet localHashSet = Sets.newHashSet();
/* 22 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 23 */       localHashSet.add(Integer.valueOf(i));
/*    */     }
/*    */     
/* 26 */     this.a = ImmutableSet.copyOf(localHashSet);
/*    */   }
/*    */   
/*    */   public Collection<Integer> c()
/*    */   {
/* 31 */     return this.a;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 36 */     if (this == paramObject) {
/* 37 */       return true;
/*    */     }
/* 39 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 40 */       return false;
/*    */     }
/* 42 */     if (!super.equals(paramObject)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     BlockStateInteger localBlockStateInteger = (BlockStateInteger)paramObject;
/* 47 */     return this.a.equals(localBlockStateInteger.a);
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 52 */     int i = super.hashCode();
/* 53 */     i = 31 * i + this.a.hashCode();
/* 54 */     return i;
/*    */   }
/*    */   
/*    */   public static BlockStateInteger of(String paramString, int paramInt1, int paramInt2) {
/* 58 */     return new BlockStateInteger(paramString, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String a(Integer paramInteger)
/*    */   {
/* 68 */     return paramInteger.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStateInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */