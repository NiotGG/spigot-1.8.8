package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpRequest;
import java.nio.charset.Charset;

public abstract interface HttpDataFactory
{
  public abstract Attribute createAttribute(HttpRequest paramHttpRequest, String paramString);
  
  public abstract Attribute createAttribute(HttpRequest paramHttpRequest, String paramString1, String paramString2);
  
  public abstract FileUpload createFileUpload(HttpRequest paramHttpRequest, String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong);
  
  public abstract void removeHttpDataFromClean(HttpRequest paramHttpRequest, InterfaceHttpData paramInterfaceHttpData);
  
  public abstract void cleanRequestHttpDatas(HttpRequest paramHttpRequest);
  
  public abstract void cleanAllHttpDatas();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\HttpDataFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */