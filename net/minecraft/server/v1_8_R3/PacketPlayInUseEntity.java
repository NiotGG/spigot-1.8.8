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
/*    */ 
/*    */ 
/*    */ public class PacketPlayInUseEntity
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int a;
/*    */   private EnumEntityUseAction action;
/*    */   private Vec3D c;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.e();
/* 33 */     this.action = ((EnumEntityUseAction)paramPacketDataSerializer.a(EnumEntityUseAction.class));
/* 34 */     if (this.action == EnumEntityUseAction.INTERACT_AT) {
/* 35 */       this.c = new Vec3D(paramPacketDataSerializer.readFloat(), paramPacketDataSerializer.readFloat(), paramPacketDataSerializer.readFloat());
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 41 */     paramPacketDataSerializer.b(this.a);
/* 42 */     paramPacketDataSerializer.a(this.action);
/* 43 */     if (this.action == EnumEntityUseAction.INTERACT_AT) {
/* 44 */       paramPacketDataSerializer.writeFloat((float)this.c.a);
/* 45 */       paramPacketDataSerializer.writeFloat((float)this.c.b);
/* 46 */       paramPacketDataSerializer.writeFloat((float)this.c.c);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 52 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public Entity a(World paramWorld) {
/* 56 */     return paramWorld.a(this.a);
/*    */   }
/*    */   
/*    */   public EnumEntityUseAction a() {
/* 60 */     return this.action;
/*    */   }
/*    */   
/*    */   public Vec3D b() {
/* 64 */     return this.c;
/*    */   }
/*    */   
/*    */   public static enum EnumEntityUseAction
/*    */   {
/*    */     private EnumEntityUseAction() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInUseEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */