/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PlayerConnectionUtils
/*    */ {
/*    */   public static <T extends PacketListener> void ensureMainThread(Packet<T> paramPacket, final T paramT, IAsyncTaskHandler paramIAsyncTaskHandler)
/*    */     throws CancelledPacketHandleException
/*    */   {
/*  9 */     if (!paramIAsyncTaskHandler.isMainThread()) {
/* 10 */       paramIAsyncTaskHandler.postToMainThread(new Runnable()
/*    */       {
/*    */         public void run() {
/* 13 */           this.a.a(paramT);
/*    */         }
/* 15 */       });
/* 16 */       throw CancelledPacketHandleException.INSTANCE;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerConnectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */