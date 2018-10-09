package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public abstract interface Packet<T extends PacketListener>
{
  public abstract void a(PacketDataSerializer paramPacketDataSerializer)
    throws IOException;
  
  public abstract void b(PacketDataSerializer paramPacketDataSerializer)
    throws IOException;
  
  public abstract void a(T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */