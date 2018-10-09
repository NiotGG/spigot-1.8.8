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
/*    */ public class PacketPlayOutCombatEvent
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   public EnumCombatEventType a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public String e;
/*    */   
/*    */   public PacketPlayOutCombatEvent() {}
/*    */   
/*    */   public PacketPlayOutCombatEvent(CombatTracker paramCombatTracker, EnumCombatEventType paramEnumCombatEventType)
/*    */   {
/* 29 */     this.a = paramEnumCombatEventType;
/*    */     
/* 31 */     EntityLiving localEntityLiving = paramCombatTracker.c();
/*    */     
/* 33 */     switch (1.a[paramEnumCombatEventType.ordinal()]) {
/*    */     case 1: 
/* 35 */       this.d = paramCombatTracker.f();
/* 36 */       this.c = (localEntityLiving == null ? -1 : localEntityLiving.getId());
/* 37 */       break;
/*    */     case 2: 
/* 39 */       this.b = paramCombatTracker.h().getId();
/* 40 */       this.c = (localEntityLiving == null ? -1 : localEntityLiving.getId());
/* 41 */       this.e = paramCombatTracker.b().c();
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 48 */     this.a = ((EnumCombatEventType)paramPacketDataSerializer.a(EnumCombatEventType.class));
/*    */     
/* 50 */     if (this.a == EnumCombatEventType.END_COMBAT) {
/* 51 */       this.d = paramPacketDataSerializer.e();
/* 52 */       this.c = paramPacketDataSerializer.readInt();
/* 53 */     } else if (this.a == EnumCombatEventType.ENTITY_DIED) {
/* 54 */       this.b = paramPacketDataSerializer.e();
/* 55 */       this.c = paramPacketDataSerializer.readInt();
/* 56 */       this.e = paramPacketDataSerializer.c(32767);
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 62 */     paramPacketDataSerializer.a(this.a);
/*    */     
/* 64 */     if (this.a == EnumCombatEventType.END_COMBAT) {
/* 65 */       paramPacketDataSerializer.b(this.d);
/* 66 */       paramPacketDataSerializer.writeInt(this.c);
/* 67 */     } else if (this.a == EnumCombatEventType.ENTITY_DIED) {
/* 68 */       paramPacketDataSerializer.b(this.b);
/* 69 */       paramPacketDataSerializer.writeInt(this.c);
/* 70 */       paramPacketDataSerializer.a(this.e);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 76 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public static enum EnumCombatEventType
/*    */   {
/*    */     private EnumCombatEventType() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutCombatEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */