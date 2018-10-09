package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import java.util.concurrent.ScheduledFuture;

@Beta
public abstract interface ListenableScheduledFuture<V>
  extends ScheduledFuture<V>, ListenableFuture<V>
{}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\ListenableScheduledFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */