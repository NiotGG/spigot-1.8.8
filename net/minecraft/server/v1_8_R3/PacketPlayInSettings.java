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
/*    */ 
/*    */ public class PacketPlayInSettings
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private String a;
/*    */   private int b;
/*    */   private EntityHuman.EnumChatVisibility c;
/*    */   private boolean d;
/*    */   private int e;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.c(7);
/* 33 */     this.b = paramPacketDataSerializer.readByte();
/*    */     
/* 35 */     this.c = EntityHuman.EnumChatVisibility.a(paramPacketDataSerializer.readByte());
/* 36 */     this.d = paramPacketDataSerializer.readBoolean();
/*    */     
/* 38 */     this.e = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 43 */     paramPacketDataSerializer.a(this.a);
/* 44 */     paramPacketDataSerializer.writeByte(this.b);
/* 45 */     paramPacketDataSerializer.writeByte(this.c.a());
/* 46 */     paramPacketDataSerializer.writeBoolean(this.d);
/* 47 */     paramPacketDataSerializer.writeByte(this.e);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 52 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public String a() {
/* 56 */     return this.a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public EntityHuman.EnumChatVisibility c()
/*    */   {
/* 64 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 68 */     return this.d;
/*    */   }
/*    */   
/*    */   public int e() {
/* 72 */     return this.e;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */