package net.minecraft.server.v1_8_R3;

public abstract interface PacketStatusOutListener
  extends PacketListener
{
  public abstract void a(PacketStatusOutServerInfo paramPacketStatusOutServerInfo);
  
  public abstract void a(PacketStatusOutPong paramPacketStatusOutPong);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketStatusOutListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */