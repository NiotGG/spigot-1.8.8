package net.minecraft.server.v1_8_R3;

import java.util.List;

public abstract interface ICommand
  extends Comparable<ICommand>
{
  public abstract String getCommand();
  
  public abstract String getUsage(ICommandListener paramICommandListener);
  
  public abstract List<String> b();
  
  public abstract void execute(ICommandListener paramICommandListener, String[] paramArrayOfString)
    throws CommandException;
  
  public abstract boolean canUse(ICommandListener paramICommandListener);
  
  public abstract List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition);
  
  public abstract boolean isListStart(String[] paramArrayOfString, int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ICommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */