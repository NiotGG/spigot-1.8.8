/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class BlockActionData
/*    */ {
/*    */   private BlockPosition a;
/*    */   private Block b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public BlockActionData(BlockPosition paramBlockPosition, Block paramBlock, int paramInt1, int paramInt2)
/*    */   {
/* 13 */     this.a = paramBlockPosition;
/* 14 */     this.c = paramInt1;
/* 15 */     this.d = paramInt2;
/* 16 */     this.b = paramBlock;
/*    */   }
/*    */   
/*    */   public BlockPosition a() {
/* 20 */     return this.a;
/*    */   }
/*    */   
/*    */   public int b() {
/* 24 */     return this.c;
/*    */   }
/*    */   
/*    */   public int c() {
/* 28 */     return this.d;
/*    */   }
/*    */   
/*    */   public Block d() {
/* 32 */     return this.b;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 37 */     if ((paramObject instanceof BlockActionData)) {
/* 38 */       BlockActionData localBlockActionData = (BlockActionData)paramObject;
/* 39 */       return (this.a.equals(localBlockActionData.a)) && (this.c == localBlockActionData.c) && (this.d == localBlockActionData.d) && (this.b == localBlockActionData.b);
/*    */     }
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 46 */     return "TE(" + this.a + ")," + this.c + "," + this.d + "," + this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockActionData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */