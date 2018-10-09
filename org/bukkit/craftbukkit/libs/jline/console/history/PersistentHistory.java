package org.bukkit.craftbukkit.libs.jline.console.history;

import java.io.IOException;

public abstract interface PersistentHistory
  extends History
{
  public abstract void flush()
    throws IOException;
  
  public abstract void purge()
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\PersistentHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */