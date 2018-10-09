package net.minecraft.server.v1_8_R3;

import java.util.List;

public abstract interface ICrafting
{
  public abstract void a(Container paramContainer, List<ItemStack> paramList);
  
  public abstract void a(Container paramContainer, int paramInt, ItemStack paramItemStack);
  
  public abstract void setContainerData(Container paramContainer, int paramInt1, int paramInt2);
  
  public abstract void setContainerData(Container paramContainer, IInventory paramIInventory);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ICrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */