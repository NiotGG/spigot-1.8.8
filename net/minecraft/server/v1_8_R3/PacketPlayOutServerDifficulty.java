/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutServerDifficulty
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private EnumDifficulty a;
/*    */   private boolean b;
/*    */   
/*    */   public PacketPlayOutServerDifficulty() {}
/*    */   
/*    */   public PacketPlayOutServerDifficulty(EnumDifficulty paramEnumDifficulty, boolean paramBoolean)
/*    */   {
/* 19 */     this.a = paramEnumDifficulty;
/* 20 */     this.b = paramBoolean;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 25 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     this.a = EnumDifficulty.getById(paramPacketDataSerializer.readUnsignedByte());
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     paramPacketDataSerializer.writeByte(this.a.a());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutServerDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */