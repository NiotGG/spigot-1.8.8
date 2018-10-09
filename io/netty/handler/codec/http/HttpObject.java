package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

public abstract interface HttpObject
{
  public abstract DecoderResult getDecoderResult();
  
  public abstract void setDecoderResult(DecoderResult paramDecoderResult);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */