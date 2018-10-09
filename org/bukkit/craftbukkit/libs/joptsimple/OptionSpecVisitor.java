package org.bukkit.craftbukkit.libs.joptsimple;

abstract interface OptionSpecVisitor
{
  public abstract void visit(NoArgumentOptionSpec paramNoArgumentOptionSpec);
  
  public abstract void visit(RequiredArgumentOptionSpec<?> paramRequiredArgumentOptionSpec);
  
  public abstract void visit(OptionalArgumentOptionSpec<?> paramOptionalArgumentOptionSpec);
  
  public abstract void visit(AlternativeLongOptionSpec paramAlternativeLongOptionSpec);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSpecVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */