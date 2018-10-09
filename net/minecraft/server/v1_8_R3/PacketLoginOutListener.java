package net.minecraft.server.v1_8_R3;

public abstract interface PacketLoginOutListener
  extends PacketListener
{
  public abstract void a(PacketLoginOutEncryptionBegin paramPacketLoginOutEncryptionBegin);
  
  public abstract void a(PacketLoginOutSuccess paramPacketLoginOutSuccess);
  
  public abstract void a(PacketLoginOutDisconnect paramPacketLoginOutDisconnect);
  
  public abstract void a(PacketLoginOutSetCompression paramPacketLoginOutSetCompression);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketLoginOutListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */