/*   */ package net.minecraft.server.v1_8_R3;
/*   */ 
/*   */ 
/*   */ 
/*   */ 
/*   */ 
/*   */ public abstract interface IDispenseBehavior
/*   */ {
/* 9 */   public static final IDispenseBehavior NONE = new IDispenseBehavior()
/*   */   {
/*   */     public ItemStack a(ISourceBlock paramAnonymousISourceBlock, ItemStack paramAnonymousItemStack) {
/* < */       return paramAnonymousItemStack;
/*   */     }
/*   */   };
/*   */   
/*   */   public abstract ItemStack a(ISourceBlock paramISourceBlock, ItemStack paramItemStack);
/*   */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IDispenseBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */