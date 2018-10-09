/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutTabComplete
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private String[] a;
/*    */   
/*    */   public PacketPlayOutTabComplete() {}
/*    */   
/*    */   public PacketPlayOutTabComplete(String[] paramArrayOfString)
/*    */   {
/* 16 */     this.a = paramArrayOfString;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 21 */     this.a = new String[paramPacketDataSerializer.e()];
/*    */     
/* 23 */     for (int i = 0; i < this.a.length; i++) {
/* 24 */       this.a[i] = paramPacketDataSerializer.c(32767);
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     paramPacketDataSerializer.b(this.a.length);
/*    */     
/* 32 */     for (String str : this.a) {
/* 33 */       paramPacketDataSerializer.a(str);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 39 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutTabComplete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */