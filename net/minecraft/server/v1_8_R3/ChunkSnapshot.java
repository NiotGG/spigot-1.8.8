/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSnapshot
/*    */ {
/* 11 */   private final short[] a = new short[65536];
/* 12 */   private final IBlockData b = Blocks.AIR.getBlockData();
/*    */   
/*    */   public IBlockData a(int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     int i = paramInt1 << 12 | paramInt3 << 8 | paramInt2;
/* 16 */     return a(i);
/*    */   }
/*    */   
/*    */   public IBlockData a(int paramInt)
/*    */   {
/* 21 */     if ((paramInt < 0) || (paramInt >= this.a.length)) {
/* 22 */       throw new IndexOutOfBoundsException("The coordinate is out of range");
/*    */     }
/*    */     
/* 25 */     IBlockData localIBlockData = (IBlockData)Block.d.a(this.a[paramInt]);
/*    */     
/* 27 */     if (localIBlockData != null) {
/* 28 */       return localIBlockData;
/*    */     }
/* 30 */     return this.b;
/*    */   }
/*    */   
/*    */   public void a(int paramInt1, int paramInt2, int paramInt3, IBlockData paramIBlockData)
/*    */   {
/* 35 */     int i = paramInt1 << 12 | paramInt3 << 8 | paramInt2;
/*    */     
/* 37 */     a(i, paramIBlockData);
/*    */   }
/*    */   
/*    */   public void a(int paramInt, IBlockData paramIBlockData)
/*    */   {
/* 42 */     if ((paramInt < 0) || (paramInt >= this.a.length)) {
/* 43 */       throw new IndexOutOfBoundsException("The coordinate is out of range");
/*    */     }
/*    */     
/* 46 */     this.a[paramInt] = ((short)Block.d.b(paramIBlockData));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */