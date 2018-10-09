/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
/*    */ import java.net.InetAddress;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.core.appender.OutputStreamManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractSocketManager
/*    */   extends OutputStreamManager
/*    */ {
/*    */   protected final InetAddress address;
/*    */   protected final String host;
/*    */   protected final int port;
/*    */   
/*    */   public AbstractSocketManager(String paramString1, OutputStream paramOutputStream, InetAddress paramInetAddress, String paramString2, int paramInt, Layout<? extends Serializable> paramLayout)
/*    */   {
/* 56 */     super(paramOutputStream, paramString1, paramLayout);
/* 57 */     this.address = paramInetAddress;
/* 58 */     this.host = paramString2;
/* 59 */     this.port = paramInt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Map<String, String> getContentFormat()
/*    */   {
/* 71 */     HashMap localHashMap = new HashMap(super.getContentFormat());
/* 72 */     localHashMap.put("port", Integer.toString(this.port));
/* 73 */     localHashMap.put("address", this.address.getHostAddress());
/*    */     
/* 75 */     return localHashMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */