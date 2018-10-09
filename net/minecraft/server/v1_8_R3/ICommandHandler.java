package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Map;

public abstract interface ICommandHandler
{
  public abstract int a(ICommandListener paramICommandListener, String paramString);
  
  public abstract List<String> a(ICommandListener paramICommandListener, String paramString, BlockPosition paramBlockPosition);
  
  public abstract List<ICommand> a(ICommandListener paramICommandListener);
  
  public abstract Map<String, ICommand> getCommands();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ICommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */