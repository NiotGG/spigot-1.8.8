/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Iterables;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ 
/*    */ public abstract class BlockDataAbstract
/*    */   implements IBlockData
/*    */ {
/* 15 */   private static final Joiner a = Joiner.on(',');
/* 16 */   private static final Function<Map.Entry<IBlockState, Comparable>, String> b = new Function()
/*    */   {
/*    */     public String a(Map.Entry<IBlockState, Comparable> paramAnonymousEntry)
/*    */     {
/* 20 */       if (paramAnonymousEntry == null) {
/* 21 */         return "<NULL>";
/*    */       }
/*    */       
/* 24 */       IBlockState localIBlockState = (IBlockState)paramAnonymousEntry.getKey();
/* 25 */       return localIBlockState.a() + "=" + localIBlockState.a((Comparable)paramAnonymousEntry.getValue());
/*    */     }
/*    */   };
/*    */   
/*    */   public <T extends Comparable<T>> IBlockData a(IBlockState<T> paramIBlockState)
/*    */   {
/* 31 */     return set(paramIBlockState, (Comparable)a(paramIBlockState.c(), get(paramIBlockState)));
/*    */   }
/*    */   
/*    */   protected static <T> T a(Collection<T> paramCollection, T paramT) {
/* 35 */     Iterator localIterator = paramCollection.iterator();
/*    */     
/* 37 */     while (localIterator.hasNext()) {
/* 38 */       if (localIterator.next().equals(paramT)) {
/* 39 */         if (localIterator.hasNext()) {
/* 40 */           return (T)localIterator.next();
/*    */         }
/* 42 */         return (T)paramCollection.iterator().next();
/*    */       }
/*    */     }
/*    */     
/* 46 */     return (T)localIterator.next();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 51 */     StringBuilder localStringBuilder = new StringBuilder();
/* 52 */     localStringBuilder.append(Block.REGISTRY.c(getBlock()));
/*    */     
/* 54 */     if (!b().isEmpty()) {
/* 55 */       localStringBuilder.append("[");
/* 56 */       a.appendTo(localStringBuilder, Iterables.transform(b().entrySet(), b));
/* 57 */       localStringBuilder.append("]");
/*    */     }
/*    */     
/* 60 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDataAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */