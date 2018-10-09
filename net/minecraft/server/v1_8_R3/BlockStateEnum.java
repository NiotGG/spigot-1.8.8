/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class BlockStateEnum<T extends Enum<T>,  extends INamable>
/*    */   extends BlockState<T>
/*    */ {
/*    */   private final ImmutableSet<T> a;
/* 16 */   private final Map<String, T> b = Maps.newHashMap();
/*    */   
/*    */   protected BlockStateEnum(String paramString, Class<T> paramClass, Collection<T> paramCollection) {
/* 19 */     super(paramString, paramClass);
/* 20 */     this.a = ImmutableSet.copyOf(paramCollection);
/*    */     
/* 22 */     for (Enum localEnum : paramCollection) {
/* 23 */       String str = ((INamable)localEnum).getName();
/* 24 */       if (this.b.containsKey(str)) {
/* 25 */         throw new IllegalArgumentException("Multiple values have the same name '" + str + "'");
/*    */       }
/* 27 */       this.b.put(str, localEnum);
/*    */     }
/*    */   }
/*    */   
/*    */   public Collection<T> c()
/*    */   {
/* 33 */     return this.a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String a(T paramT)
/*    */   {
/* 43 */     return ((INamable)paramT).getName();
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T>,  extends INamable> BlockStateEnum<T> of(String paramString, Class<T> paramClass) {
/* 47 */     return a(paramString, paramClass, Predicates.alwaysTrue());
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T>,  extends INamable> BlockStateEnum<T> a(String paramString, Class<T> paramClass, Predicate<T> paramPredicate) {
/* 51 */     return a(paramString, paramClass, Collections2.filter(Lists.newArrayList(paramClass.getEnumConstants()), paramPredicate));
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T>,  extends INamable> BlockStateEnum<T> of(String paramString, Class<T> paramClass, T... paramVarArgs) {
/* 55 */     return a(paramString, paramClass, Lists.newArrayList(paramVarArgs));
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T>,  extends INamable> BlockStateEnum<T> a(String paramString, Class<T> paramClass, Collection<T> paramCollection) {
/* 59 */     return new BlockStateEnum(paramString, paramClass, paramCollection);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStateEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */