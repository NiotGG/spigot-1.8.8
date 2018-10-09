/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class WorldGenFlatLayerInfo
/*    */ {
/*    */   private final int a;
/*    */   
/*    */   private IBlockData b;
/*    */   
/* 10 */   private int c = 1;
/*    */   private int d;
/*    */   
/*    */   public WorldGenFlatLayerInfo(int paramInt, Block paramBlock) {
/* 14 */     this(3, paramInt, paramBlock);
/*    */   }
/*    */   
/*    */   public WorldGenFlatLayerInfo(int paramInt1, int paramInt2, Block paramBlock) {
/* 18 */     this.a = paramInt1;
/* 19 */     this.c = paramInt2;
/* 20 */     this.b = paramBlock.getBlockData();
/*    */   }
/*    */   
/*    */   public WorldGenFlatLayerInfo(int paramInt1, int paramInt2, Block paramBlock, int paramInt3) {
/* 24 */     this(paramInt1, paramInt2, paramBlock);
/* 25 */     this.b = paramBlock.fromLegacyData(paramInt3);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int b()
/*    */   {
/* 33 */     return this.c;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IBlockData c()
/*    */   {
/* 45 */     return this.b;
/*    */   }
/*    */   
/*    */   private Block e() {
/* 49 */     return this.b.getBlock();
/*    */   }
/*    */   
/*    */   private int f() {
/* 53 */     return this.b.getBlock().toLegacyData(this.b);
/*    */   }
/*    */   
/*    */   public int d() {
/* 57 */     return this.d;
/*    */   }
/*    */   
/*    */   public void b(int paramInt) {
/* 61 */     this.d = paramInt;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/*    */     String str;
/* 67 */     if (this.a >= 3) {
/* 68 */       MinecraftKey localMinecraftKey = (MinecraftKey)Block.REGISTRY.c(e());
/* 69 */       str = localMinecraftKey == null ? "null" : localMinecraftKey.toString();
/*    */       
/* 71 */       if (this.c > 1) {
/* 72 */         str = this.c + "*" + str;
/*    */       }
/*    */     } else {
/* 75 */       str = Integer.toString(Block.getId(e()));
/*    */       
/* 77 */       if (this.c > 1) {
/* 78 */         str = this.c + "x" + str;
/*    */       }
/*    */     }
/*    */     
/* 82 */     int i = f();
/* 83 */     if (i > 0) {
/* 84 */       str = str + ":" + i;
/*    */     }
/*    */     
/* 87 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenFlatLayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */