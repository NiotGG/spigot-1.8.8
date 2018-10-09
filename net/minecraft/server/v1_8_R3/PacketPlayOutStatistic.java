/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutStatistic
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private Map<Statistic, Integer> a;
/*    */   
/*    */   public PacketPlayOutStatistic() {}
/*    */   
/*    */   public PacketPlayOutStatistic(Map<Statistic, Integer> paramMap)
/*    */   {
/* 20 */     this.a = paramMap;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 25 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     int i = paramPacketDataSerializer.e();
/* 31 */     this.a = Maps.newHashMap();
/*    */     
/* 33 */     for (int j = 0; j < i; j++) {
/* 34 */       Statistic localStatistic = StatisticList.getStatistic(paramPacketDataSerializer.c(32767));
/* 35 */       int k = paramPacketDataSerializer.e();
/*    */       
/* 37 */       if (localStatistic != null) {
/* 38 */         this.a.put(localStatistic, Integer.valueOf(k));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 45 */     paramPacketDataSerializer.b(this.a.size());
/*    */     
/* 47 */     for (Map.Entry localEntry : this.a.entrySet()) {
/* 48 */       paramPacketDataSerializer.a(((Statistic)localEntry.getKey()).name);
/* 49 */       paramPacketDataSerializer.b(((Integer)localEntry.getValue()).intValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */