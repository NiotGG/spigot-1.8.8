package net.minecraft.server.v1_8_R3;

public abstract interface IMerchant
{
  public abstract void a_(EntityHuman paramEntityHuman);
  
  public abstract EntityHuman v_();
  
  public abstract MerchantRecipeList getOffers(EntityHuman paramEntityHuman);
  
  public abstract void a(MerchantRecipe paramMerchantRecipe);
  
  public abstract void a_(ItemStack paramItemStack);
  
  public abstract IChatBaseComponent getScoreboardDisplayName();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */