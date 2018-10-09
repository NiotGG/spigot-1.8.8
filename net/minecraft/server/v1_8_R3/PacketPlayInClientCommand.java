/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInClientCommand
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private EnumClientCommand a;
/*    */   
/*    */   public PacketPlayInClientCommand() {}
/*    */   
/*    */   public PacketPlayInClientCommand(EnumClientCommand paramEnumClientCommand)
/*    */   {
/* 16 */     this.a = paramEnumClientCommand;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 21 */     this.a = ((EnumClientCommand)paramPacketDataSerializer.a(EnumClientCommand.class));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     paramPacketDataSerializer.a(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 31 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public EnumClientCommand a() {
/* 35 */     return this.a;
/*    */   }
/*    */   
/*    */   public static enum EnumClientCommand
/*    */   {
/*    */     private EnumClientCommand() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInClientCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */