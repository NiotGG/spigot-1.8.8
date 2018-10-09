/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemLeaves extends ItemBlock
/*    */ {
/*    */   private final BlockLeaves b;
/*    */   
/*    */   public ItemLeaves(BlockLeaves paramBlockLeaves)
/*    */   {
/*  9 */     super(paramBlockLeaves);
/* 10 */     this.b = paramBlockLeaves;
/*    */     
/* 12 */     setMaxDurability(0);
/* 13 */     a(true);
/*    */   }
/*    */   
/*    */   public int filterData(int paramInt)
/*    */   {
/* 18 */     return paramInt | 0x4;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String e_(ItemStack paramItemStack)
/*    */   {
/* 28 */     return super.getName() + "." + this.b.b(paramItemStack.getData()).d();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemLeaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */