package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public abstract interface IChunkLoader
{
  public abstract Chunk a(World paramWorld, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract void a(World paramWorld, Chunk paramChunk)
    throws IOException, ExceptionWorldConflict;
  
  public abstract void b(World paramWorld, Chunk paramChunk)
    throws IOException;
  
  public abstract void a();
  
  public abstract void b();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IChunkLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */