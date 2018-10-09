/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class PacketPlayInUpdateSign
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private BlockPosition a;
/*    */   private IChatBaseComponent[] b;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 28 */     this.a = paramPacketDataSerializer.c();
/* 29 */     this.b = new IChatBaseComponent[4];
/* 30 */     for (int i = 0; i < 4; i++) {
/* 31 */       String str = paramPacketDataSerializer.c(384);
/* 32 */       IChatBaseComponent localIChatBaseComponent = IChatBaseComponent.ChatSerializer.a(str);
/* 33 */       this.b[i] = localIChatBaseComponent;
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 39 */     paramPacketDataSerializer.a(this.a);
/* 40 */     for (int i = 0; i < 4; i++) {
/* 41 */       IChatBaseComponent localIChatBaseComponent = this.b[i];
/* 42 */       String str = IChatBaseComponent.ChatSerializer.a(localIChatBaseComponent);
/* 43 */       paramPacketDataSerializer.a(str);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 49 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public BlockPosition a() {
/* 53 */     return this.a;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent[] b() {
/* 57 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInUpdateSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */