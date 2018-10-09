/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityMetadata
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private List<DataWatcher.WatchableObject> b;
/*    */   
/*    */   public PacketPlayOutEntityMetadata() {}
/*    */   
/*    */   public PacketPlayOutEntityMetadata(int paramInt, DataWatcher paramDataWatcher, boolean paramBoolean)
/*    */   {
/* 19 */     this.a = paramInt;
/* 20 */     if (paramBoolean) {
/* 21 */       this.b = paramDataWatcher.c();
/*    */     } else {
/* 23 */       this.b = paramDataWatcher.b();
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.e();
/* 30 */     this.b = DataWatcher.b(paramPacketDataSerializer);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     paramPacketDataSerializer.b(this.a);
/* 36 */     DataWatcher.a(this.b, paramPacketDataSerializer);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 41 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */