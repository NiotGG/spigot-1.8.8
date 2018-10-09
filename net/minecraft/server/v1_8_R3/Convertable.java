package net.minecraft.server.v1_8_R3;

public abstract interface Convertable
{
  public abstract IDataManager a(String paramString, boolean paramBoolean);
  
  public abstract void d();
  
  public abstract boolean e(String paramString);
  
  public abstract boolean isConvertable(String paramString);
  
  public abstract boolean convert(String paramString, IProgressUpdate paramIProgressUpdate);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Convertable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */