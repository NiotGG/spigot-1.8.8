/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInTabComplete
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private String a;
/*    */   private BlockPosition b;
/*    */   
/*    */   public PacketPlayInTabComplete() {}
/*    */   
/*    */   public PacketPlayInTabComplete(String paramString)
/*    */   {
/* 17 */     this(paramString, null);
/*    */   }
/*    */   
/*    */   public PacketPlayInTabComplete(String paramString, BlockPosition paramBlockPosition) {
/* 21 */     this.a = paramString;
/* 22 */     this.b = paramBlockPosition;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     this.a = paramPacketDataSerializer.c(32767);
/* 28 */     boolean bool = paramPacketDataSerializer.readBoolean();
/* 29 */     if (bool) {
/* 30 */       this.b = paramPacketDataSerializer.c();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     paramPacketDataSerializer.a(StringUtils.substring(this.a, 0, 32767));
/* 37 */     boolean bool = this.b != null;
/* 38 */     paramPacketDataSerializer.writeBoolean(bool);
/* 39 */     if (bool) {
/* 40 */       paramPacketDataSerializer.a(this.b);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 46 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public String a() {
/* 50 */     return this.a;
/*    */   }
/*    */   
/*    */   public BlockPosition b() {
/* 54 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInTabComplete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */