package net.minecraft.server.v1_8_R3;

public abstract interface ITileEntityContainer
  extends INamableTileEntity
{
  public abstract Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman);
  
  public abstract String getContainerName();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ITileEntityContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */