/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Objects.ToStringHelper;
/*    */ 
/*    */ public abstract class BlockState<T extends Comparable<T>> implements IBlockState<T> {
/*    */   private final Class<T> a;
/*    */   private final String b;
/*    */   
/*    */   protected BlockState(String paramString, Class<T> paramClass) {
/* 10 */     this.a = paramClass;
/* 11 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public String a()
/*    */   {
/* 16 */     return this.b;
/*    */   }
/*    */   
/*    */   public Class<T> b()
/*    */   {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 26 */     return com.google.common.base.Objects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", c()).toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 35 */     if (this == paramObject) {
/* 36 */       return true;
/*    */     }
/* 38 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     BlockState localBlockState = (BlockState)paramObject;
/* 43 */     return (this.a.equals(localBlockState.a)) && (this.b.equals(localBlockState.b));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 48 */     return 31 * this.a.hashCode() + this.b.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */