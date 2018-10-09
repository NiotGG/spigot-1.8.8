/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class NibbleArray
/*    */ {
/*    */   private final byte[] a;
/*    */   
/*    */   public NibbleArray() {
/*  8 */     this.a = new byte['ࠀ'];
/*    */   }
/*    */   
/*    */   public NibbleArray(byte[] abyte) {
/* 12 */     this.a = abyte;
/* 13 */     if (abyte.length != 2048) {
/* 14 */       throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + abyte.length);
/*    */     }
/*    */   }
/*    */   
/*    */   public int a(int i, int j, int k) {
/* 19 */     return a(b(i, j, k));
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, int l) {
/* 23 */     a(b(i, j, k), l);
/*    */   }
/*    */   
/*    */   private int b(int i, int j, int k) {
/* 27 */     return j << 8 | k << 4 | i;
/*    */   }
/*    */   
/*    */   public int a(int i) {
/* 31 */     int j = c(i);
/*    */     
/* 33 */     return b(i) ? this.a[j] & 0xF : this.a[j] >> 4 & 0xF;
/*    */   }
/*    */   
/*    */   public void a(int i, int j) {
/* 37 */     int k = c(i);
/*    */     
/* 39 */     if (b(i)) {
/* 40 */       this.a[k] = ((byte)(this.a[k] & 0xF0 | j & 0xF));
/*    */     } else {
/* 42 */       this.a[k] = ((byte)(this.a[k] & 0xF | (j & 0xF) << 4));
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean b(int i)
/*    */   {
/* 48 */     return (i & 0x1) == 0;
/*    */   }
/*    */   
/*    */   private int c(int i) {
/* 52 */     return i >> 1;
/*    */   }
/*    */   
/*    */   public byte[] a() {
/* 56 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NibbleArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */