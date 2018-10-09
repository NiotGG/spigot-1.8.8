/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ChunkCoordIntPair
/*    */ {
/*    */   public final int x;
/*    */   public final int z;
/*    */   
/*    */   public ChunkCoordIntPair(int paramInt1, int paramInt2)
/*    */   {
/* 10 */     this.x = paramInt1;
/* 11 */     this.z = paramInt2;
/*    */   }
/*    */   
/*    */   public static long a(int paramInt1, int paramInt2) {
/* 15 */     return paramInt1 & 0xFFFFFFFF | (paramInt2 & 0xFFFFFFFF) << 32;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 24 */     int i = 1664525 * this.x + 1013904223;
/* 25 */     int j = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
/* 26 */     return i ^ j;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 31 */     if (this == paramObject) {
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     if ((paramObject instanceof ChunkCoordIntPair)) {
/* 36 */       ChunkCoordIntPair localChunkCoordIntPair = (ChunkCoordIntPair)paramObject;
/*    */       
/* 38 */       return (this.x == localChunkCoordIntPair.x) && (this.z == localChunkCoordIntPair.z);
/*    */     }
/*    */     
/* 41 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int a()
/*    */   {
/* 55 */     return (this.x << 4) + 8;
/*    */   }
/*    */   
/*    */   public int b() {
/* 59 */     return (this.z << 4) + 8;
/*    */   }
/*    */   
/*    */   public int c() {
/* 63 */     return this.x << 4;
/*    */   }
/*    */   
/*    */   public int d() {
/* 67 */     return this.z << 4;
/*    */   }
/*    */   
/*    */   public int e() {
/* 71 */     return (this.x << 4) + 15;
/*    */   }
/*    */   
/*    */   public int f() {
/* 75 */     return (this.z << 4) + 15;
/*    */   }
/*    */   
/*    */   public BlockPosition a(int paramInt1, int paramInt2, int paramInt3) {
/* 79 */     return new BlockPosition((this.x << 4) + paramInt1, paramInt2, (this.z << 4) + paramInt3);
/*    */   }
/*    */   
/*    */   public BlockPosition a(int paramInt) {
/* 83 */     return new BlockPosition(a(), paramInt, b());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return "[" + this.x + ", " + this.z + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkCoordIntPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */