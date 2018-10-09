/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ 
/*    */ public class ItemMultiTexture
/*    */   extends ItemBlock
/*    */ {
/*    */   protected final Block b;
/*    */   protected final Function<ItemStack, String> c;
/*    */   
/*    */   public ItemMultiTexture(Block paramBlock1, Block paramBlock2, Function<ItemStack, String> paramFunction)
/*    */   {
/* 13 */     super(paramBlock1);
/*    */     
/* 15 */     this.b = paramBlock2;
/* 16 */     this.c = paramFunction;
/*    */     
/* 18 */     setMaxDurability(0);
/* 19 */     a(true);
/*    */   }
/*    */   
/*    */   public ItemMultiTexture(Block paramBlock1, Block paramBlock2, String[] paramArrayOfString) {
/* 23 */     this(paramBlock1, paramBlock2, new Function()
/*    */     {
/*    */       public String a(ItemStack paramAnonymousItemStack)
/*    */       {
/* 27 */         int i = paramAnonymousItemStack.getData();
/* 28 */         if ((i < 0) || (i >= ItemMultiTexture.this.length)) {
/* 29 */           i = 0;
/*    */         }
/* 31 */         return ItemMultiTexture.this[i];
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public int filterData(int paramInt)
/*    */   {
/* 38 */     return paramInt;
/*    */   }
/*    */   
/*    */   public String e_(ItemStack paramItemStack)
/*    */   {
/* 43 */     return super.getName() + "." + (String)this.c.apply(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemMultiTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */