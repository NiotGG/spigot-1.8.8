/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketStatusOutServerInfo
/*    */   implements Packet<PacketStatusOutListener>
/*    */ {
/* 14 */   private static final Gson a = new GsonBuilder().registerTypeAdapter(ServerPing.ServerData.class, new ServerPing.ServerData.Serializer()).registerTypeAdapter(ServerPing.ServerPingPlayerSample.class, new ServerPing.ServerPingPlayerSample.Serializer()).registerTypeAdapter(ServerPing.class, new ServerPing.Serializer()).registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer()).registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer()).registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
/*    */   
/*    */ 
/*    */ 
/*    */   private ServerPing b;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public PacketStatusOutServerInfo() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public PacketStatusOutServerInfo(ServerPing paramServerPing)
/*    */   {
/* 30 */     this.b = paramServerPing;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     this.b = ((ServerPing)a.fromJson(paramPacketDataSerializer.c(32767), ServerPing.class));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 40 */     paramPacketDataSerializer.a(a.toJson(this.b));
/*    */   }
/*    */   
/*    */   public void a(PacketStatusOutListener paramPacketStatusOutListener)
/*    */   {
/* 45 */     paramPacketStatusOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketStatusOutServerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */