package com.google.common.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract interface ListeningExecutorService
  extends ExecutorService
{
  public abstract <T> ListenableFuture<T> submit(Callable<T> paramCallable);
  
  public abstract ListenableFuture<?> submit(Runnable paramRunnable);
  
  public abstract <T> ListenableFuture<T> submit(Runnable paramRunnable, T paramT);
  
  public abstract <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection)
    throws InterruptedException;
  
  public abstract <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\ListeningExecutorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */