package io.netty.util.concurrent;

public abstract interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>>
  extends GenericFutureListener<F>
{
  public abstract void operationProgressed(F paramF, long paramLong1, long paramLong2)
    throws Exception;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\GenericProgressiveFutureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */