/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryID<T>
/*    */   implements Registry<T>
/*    */ {
/* 15 */   private final IdentityHashMap<T, Integer> a = new IdentityHashMap(512);
/* 16 */   private final List<T> b = Lists.newArrayList();
/*    */   
/*    */   public void a(T paramT, int paramInt) {
/* 19 */     this.a.put(paramT, Integer.valueOf(paramInt));
/*    */     
/*    */ 
/* 22 */     while (this.b.size() <= paramInt) {
/* 23 */       this.b.add(null);
/*    */     }
/*    */     
/* 26 */     this.b.set(paramInt, paramT);
/*    */   }
/*    */   
/*    */   public int b(T paramT)
/*    */   {
/* 31 */     Integer localInteger = (Integer)this.a.get(paramT);
/* 32 */     return localInteger == null ? -1 : localInteger.intValue();
/*    */   }
/*    */   
/*    */ 
/*    */   public final T a(int paramInt)
/*    */   {
/* 38 */     if ((paramInt >= 0) && (paramInt < this.b.size())) {
/* 39 */       return (T)this.b.get(paramInt);
/*    */     }
/*    */     
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public Iterator<T> iterator()
/*    */   {
/* 47 */     return Iterators.filter(this.b.iterator(), Predicates.notNull());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegistryID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */