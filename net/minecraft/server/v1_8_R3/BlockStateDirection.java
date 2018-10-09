/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class BlockStateDirection extends BlockStateEnum<EnumDirection>
/*    */ {
/*    */   protected BlockStateDirection(String paramString, Collection<EnumDirection> paramCollection)
/*    */   {
/* 13 */     super(paramString, EnumDirection.class, paramCollection);
/*    */   }
/*    */   
/*    */   public static BlockStateDirection of(String paramString) {
/* 17 */     return of(paramString, Predicates.alwaysTrue());
/*    */   }
/*    */   
/*    */   public static BlockStateDirection of(String paramString, Predicate<EnumDirection> paramPredicate) {
/* 21 */     return a(paramString, Collections2.filter(Lists.newArrayList(EnumDirection.values()), paramPredicate));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static BlockStateDirection a(String paramString, Collection<EnumDirection> paramCollection)
/*    */   {
/* 29 */     return new BlockStateDirection(paramString, paramCollection);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStateDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */