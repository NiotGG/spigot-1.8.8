/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutNamedSoundEffect
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private String a;
/*    */   private int b;
/* 18 */   private int c = Integer.MAX_VALUE;
/*    */   
/*    */   private int d;
/*    */   private float e;
/*    */   private int f;
/*    */   
/*    */   public PacketPlayOutNamedSoundEffect() {}
/*    */   
/*    */   public PacketPlayOutNamedSoundEffect(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2)
/*    */   {
/* 28 */     Validate.notNull(paramString, "name", new Object[0]);
/* 29 */     this.a = paramString;
/* 30 */     this.b = ((int)(paramDouble1 * 8.0D));
/* 31 */     this.c = ((int)(paramDouble2 * 8.0D));
/* 32 */     this.d = ((int)(paramDouble3 * 8.0D));
/* 33 */     this.e = paramFloat1;
/* 34 */     this.f = ((int)(paramFloat2 * 63.0F));
/*    */     
/* 36 */     paramFloat2 = MathHelper.a(paramFloat2, 0.0F, 255.0F);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 41 */     this.a = paramPacketDataSerializer.c(256);
/* 42 */     this.b = paramPacketDataSerializer.readInt();
/* 43 */     this.c = paramPacketDataSerializer.readInt();
/* 44 */     this.d = paramPacketDataSerializer.readInt();
/* 45 */     this.e = paramPacketDataSerializer.readFloat();
/* 46 */     this.f = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 51 */     paramPacketDataSerializer.a(this.a);
/* 52 */     paramPacketDataSerializer.writeInt(this.b);
/* 53 */     paramPacketDataSerializer.writeInt(this.c);
/* 54 */     paramPacketDataSerializer.writeInt(this.d);
/* 55 */     paramPacketDataSerializer.writeFloat(this.e);
/* 56 */     paramPacketDataSerializer.writeByte(this.f);
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 85 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutNamedSoundEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */