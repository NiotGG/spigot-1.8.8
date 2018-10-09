package net.minecraft.server.v1_8_R3;

public abstract interface PacketLoginInListener
  extends PacketListener
{
  public abstract void a(PacketLoginInStart paramPacketLoginInStart);
  
  public abstract void a(PacketLoginInEncryptionBegin paramPacketLoginInEncryptionBegin);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketLoginInListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */