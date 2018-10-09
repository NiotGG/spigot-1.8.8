package net.minecraft.server.v1_8_R3;

public abstract interface ICommandListener
{
  public abstract String getName();
  
  public abstract IChatBaseComponent getScoreboardDisplayName();
  
  public abstract void sendMessage(IChatBaseComponent paramIChatBaseComponent);
  
  public abstract boolean a(int paramInt, String paramString);
  
  public abstract BlockPosition getChunkCoordinates();
  
  public abstract Vec3D d();
  
  public abstract World getWorld();
  
  public abstract Entity f();
  
  public abstract boolean getSendCommandFeedback();
  
  public abstract void a(CommandObjectiveExecutor.EnumCommandResult paramEnumCommandResult, int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ICommandListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */