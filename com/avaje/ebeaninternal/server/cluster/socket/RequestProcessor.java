/*    */ package com.avaje.ebeaninternal.server.cluster.socket;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ class RequestProcessor
/*    */   implements Runnable
/*    */ {
/* 35 */   private static final Logger logger = Logger.getLogger(RequestProcessor.class.getName());
/*    */   
/*    */ 
/*    */   private final Socket clientSocket;
/*    */   
/*    */ 
/*    */   private final SocketClusterBroadcast owner;
/*    */   
/*    */ 
/*    */   public RequestProcessor(SocketClusterBroadcast owner, Socket clientSocket)
/*    */   {
/* 46 */     this.clientSocket = clientSocket;
/* 47 */     this.owner = owner;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 58 */       SocketConnection sc = new SocketConnection(this.clientSocket);
/*    */       for (;;)
/*    */       {
/* 61 */         if (this.owner.process(sc)) {
/*    */           break;
/*    */         }
/*    */       }
/*    */       
/* 66 */       sc.disconnect();
/*    */     }
/*    */     catch (IOException e) {
/* 69 */       logger.log(Level.SEVERE, null, e);
/*    */     } catch (ClassNotFoundException e) {
/* 71 */       logger.log(Level.SEVERE, null, e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\socket\RequestProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */