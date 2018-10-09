/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemWithAuxData extends ItemBlock
/*    */ {
/*    */   private final Block b;
/*    */   private String[] c;
/*    */   
/*    */   public ItemWithAuxData(Block paramBlock, boolean paramBoolean)
/*    */   {
/* 10 */     super(paramBlock);
/* 11 */     this.b = paramBlock;
/*    */     
/* 13 */     if (paramBoolean) {
/* 14 */       setMaxDurability(0);
/* 15 */       a(true);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int filterData(int paramInt)
/*    */   {
/* 26 */     return paramInt;
/*    */   }
/*    */   
/*    */   public ItemWithAuxData a(String[] paramArrayOfString) {
/* 30 */     this.c = paramArrayOfString;
/* 31 */     return this;
/*    */   }
/*    */   
/*    */   public String e_(ItemStack paramItemStack)
/*    */   {
/* 36 */     if (this.c == null) {
/* 37 */       return super.e_(paramItemStack);
/*    */     }
/* 39 */     int i = paramItemStack.getData();
/* 40 */     if ((i >= 0) && (i < this.c.length)) {
/* 41 */       return super.e_(paramItemStack) + "." + this.c[i];
/*    */     }
/* 43 */     return super.e_(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemWithAuxData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */