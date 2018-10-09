/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityDestroy
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int[] a;
/*    */   
/*    */   public PacketPlayOutEntityDestroy() {}
/*    */   
/*    */   public PacketPlayOutEntityDestroy(int... paramVarArgs)
/*    */   {
/* 19 */     this.a = paramVarArgs;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     this.a = new int[paramPacketDataSerializer.e()];
/*    */     
/* 26 */     for (int i = 0; i < this.a.length; i++) {
/* 27 */       this.a[i] = paramPacketDataSerializer.e();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.b(this.a.length);
/*    */     
/* 35 */     for (int i = 0; i < this.a.length; i++) {
/* 36 */       paramPacketDataSerializer.b(this.a[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 42 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityDestroy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */