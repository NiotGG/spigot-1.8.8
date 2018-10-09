/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.net.ConnectException;
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
/*    */ public class ConnectTimeoutException
/*    */   extends ConnectException
/*    */ {
/*    */   private static final long serialVersionUID = 2317065249988317463L;
/*    */   
/*    */   public ConnectTimeoutException(String paramString)
/*    */   {
/* 28 */     super(paramString);
/*    */   }
/*    */   
/*    */   public ConnectTimeoutException() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ConnectTimeoutException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */