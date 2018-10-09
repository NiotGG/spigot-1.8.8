/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*    */ 
/*    */ public class PacketPlayInChat implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private String a;
/*    */   
/*    */   public PacketPlayInChat() {}
/*    */   
/*    */   public PacketPlayInChat(String s) {
/* 12 */     if (s.length() > 100) {
/* 13 */       s = s.substring(0, 100);
/*    */     }
/*    */     
/* 16 */     this.a = s;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws java.io.IOException {
/* 20 */     this.a = packetdataserializer.c(100);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws java.io.IOException {
/* 24 */     packetdataserializer.a(this.a);
/*    */   }
/*    */   
/*    */ 
/* 28 */   private static final java.util.concurrent.ExecutorService executors = java.util.concurrent.Executors.newCachedThreadPool(
/* 29 */     new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Async Chat Thread - #%d").build());
/*    */   
/* 31 */   public void a(final PacketListenerPlayIn packetlistenerplayin) { if (!this.a.startsWith("/"))
/*    */     {
/* 33 */       executors.submit(new Runnable()
/*    */       {
/*    */ 
/*    */         public void run()
/*    */         {
/*    */ 
/* 39 */           packetlistenerplayin.a(PacketPlayInChat.this);
/*    */         }
/* 41 */       });
/* 42 */       return;
/*    */     }
/*    */     
/* 45 */     packetlistenerplayin.a(this);
/*    */   }
/*    */   
/*    */   public String a() {
/* 49 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */