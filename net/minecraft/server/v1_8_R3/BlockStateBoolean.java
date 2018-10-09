/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ 
/*    */ public class BlockStateBoolean extends BlockState<Boolean>
/*    */ {
/*    */   private final ImmutableSet<Boolean> a;
/*    */   
/*    */   protected BlockStateBoolean(String paramString)
/*    */   {
/* 11 */     super(paramString, Boolean.class);
/* 12 */     this.a = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));
/*    */   }
/*    */   
/*    */   public java.util.Collection<Boolean> c()
/*    */   {
/* 17 */     return this.a;
/*    */   }
/*    */   
/*    */   public static BlockStateBoolean of(String paramString) {
/* 21 */     return new BlockStateBoolean(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String a(Boolean paramBoolean)
/*    */   {
/* 31 */     return paramBoolean.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStateBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */