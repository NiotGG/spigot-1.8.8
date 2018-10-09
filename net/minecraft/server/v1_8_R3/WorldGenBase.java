/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenBase
/*    */ {
/* 11 */   protected int a = 8;
/* 12 */   protected Random b = new Random();
/*    */   protected World c;
/*    */   
/*    */   public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot) {
/* 16 */     int i = this.a;
/* 17 */     this.c = paramWorld;
/*    */     
/* 19 */     this.b.setSeed(paramWorld.getSeed());
/* 20 */     long l1 = this.b.nextLong();
/* 21 */     long l2 = this.b.nextLong();
/*    */     
/* 23 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 24 */       for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
/* 25 */         long l3 = j * l1;
/* 26 */         long l4 = k * l2;
/* 27 */         this.b.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
/* 28 */         a(paramWorld, j, k, paramInt1, paramInt2, paramChunkSnapshot);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ChunkSnapshot paramChunkSnapshot) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */