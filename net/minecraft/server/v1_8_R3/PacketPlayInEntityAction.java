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
/*    */ public class PacketPlayInEntityAction
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int a;
/*    */   private EnumPlayerAction animation;
/*    */   private int c;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.e();
/* 30 */     this.animation = ((EnumPlayerAction)paramPacketDataSerializer.a(EnumPlayerAction.class));
/* 31 */     this.c = paramPacketDataSerializer.e();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     paramPacketDataSerializer.b(this.a);
/* 37 */     paramPacketDataSerializer.a(this.animation);
/* 38 */     paramPacketDataSerializer.b(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 43 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumPlayerAction b()
/*    */   {
/* 51 */     return this.animation;
/*    */   }
/*    */   
/*    */   public int c() {
/* 55 */     return this.c;
/*    */   }
/*    */   
/*    */   public static enum EnumPlayerAction
/*    */   {
/*    */     private EnumPlayerAction() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInEntityAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */