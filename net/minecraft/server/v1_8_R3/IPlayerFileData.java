package net.minecraft.server.v1_8_R3;

public abstract interface IPlayerFileData
{
  public abstract void save(EntityHuman paramEntityHuman);
  
  public abstract NBTTagCompound load(EntityHuman paramEntityHuman);
  
  public abstract String[] getSeenPlayers();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IPlayerFileData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */