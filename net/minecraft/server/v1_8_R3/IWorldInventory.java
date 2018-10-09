package net.minecraft.server.v1_8_R3;

public abstract interface IWorldInventory
  extends IInventory
{
  public abstract int[] getSlotsForFace(EnumDirection paramEnumDirection);
  
  public abstract boolean canPlaceItemThroughFace(int paramInt, ItemStack paramItemStack, EnumDirection paramEnumDirection);
  
  public abstract boolean canTakeItemThroughFace(int paramInt, ItemStack paramItemStack, EnumDirection paramEnumDirection);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IWorldInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */