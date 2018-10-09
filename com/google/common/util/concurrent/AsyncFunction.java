package com.google.common.util.concurrent;

public abstract interface AsyncFunction<I, O>
{
  public abstract ListenableFuture<O> apply(I paramI)
    throws Exception;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\AsyncFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */