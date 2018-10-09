package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.io.IOException;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public abstract interface CompletionHandler
{
  public abstract boolean complete(ConsoleReader paramConsoleReader, List<CharSequence> paramList, int paramInt)
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\CompletionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */