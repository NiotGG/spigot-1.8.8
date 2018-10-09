package net.minecraft.server.v1_8_R3;

import com.google.common.util.concurrent.ListenableFuture;

public abstract interface IAsyncTaskHandler
{
  public abstract ListenableFuture<Object> postToMainThread(Runnable paramRunnable);
  
  public abstract boolean isMainThread();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IAsyncTaskHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */