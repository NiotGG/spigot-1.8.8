/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutUpdateAttributes
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private int a;
/*  17 */   private final List<AttributeSnapshot> b = Lists.newArrayList();
/*     */   
/*     */   public PacketPlayOutUpdateAttributes() {}
/*     */   
/*     */   public PacketPlayOutUpdateAttributes(int paramInt, Collection<AttributeInstance> paramCollection)
/*     */   {
/*  23 */     this.a = paramInt;
/*     */     
/*  25 */     for (AttributeInstance localAttributeInstance : paramCollection) {
/*  26 */       this.b.add(new AttributeSnapshot(localAttributeInstance.getAttribute().getName(), localAttributeInstance.b(), localAttributeInstance.c()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  32 */     this.a = paramPacketDataSerializer.e();
/*     */     
/*  34 */     int i = paramPacketDataSerializer.readInt();
/*  35 */     for (int j = 0; j < i; j++) {
/*  36 */       String str = paramPacketDataSerializer.c(64);
/*  37 */       double d = paramPacketDataSerializer.readDouble();
/*  38 */       ArrayList localArrayList = Lists.newArrayList();
/*  39 */       int k = paramPacketDataSerializer.e();
/*     */       
/*  41 */       for (int m = 0; m < k; m++) {
/*  42 */         UUID localUUID = paramPacketDataSerializer.g();
/*  43 */         localArrayList.add(new AttributeModifier(localUUID, "Unknown synced attribute modifier", paramPacketDataSerializer.readDouble(), paramPacketDataSerializer.readByte()));
/*     */       }
/*     */       
/*  46 */       this.b.add(new AttributeSnapshot(str, d, localArrayList));
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  52 */     paramPacketDataSerializer.b(this.a);
/*  53 */     paramPacketDataSerializer.writeInt(this.b.size());
/*     */     
/*  55 */     for (AttributeSnapshot localAttributeSnapshot : this.b) {
/*  56 */       paramPacketDataSerializer.a(localAttributeSnapshot.a());
/*  57 */       paramPacketDataSerializer.writeDouble(localAttributeSnapshot.b());
/*  58 */       paramPacketDataSerializer.b(localAttributeSnapshot.c().size());
/*     */       
/*  60 */       for (AttributeModifier localAttributeModifier : localAttributeSnapshot.c()) {
/*  61 */         paramPacketDataSerializer.a(localAttributeModifier.a());
/*  62 */         paramPacketDataSerializer.writeDouble(localAttributeModifier.d());
/*  63 */         paramPacketDataSerializer.writeByte(localAttributeModifier.c());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/*  70 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public class AttributeSnapshot
/*     */   {
/*     */     private final String b;
/*     */     
/*     */ 
/*     */     private final double c;
/*     */     
/*     */     private final Collection<AttributeModifier> d;
/*     */     
/*     */ 
/*     */     public AttributeSnapshot(double paramDouble, Collection<AttributeModifier> paramCollection)
/*     */     {
/*  87 */       this.b = paramDouble;
/*  88 */       this.c = ???;
/*  89 */       Collection localCollection; this.d = localCollection;
/*     */     }
/*     */     
/*     */     public String a() {
/*  93 */       return this.b;
/*     */     }
/*     */     
/*     */     public double b() {
/*  97 */       return this.c;
/*     */     }
/*     */     
/*     */     public Collection<AttributeModifier> c() {
/* 101 */       return this.d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutUpdateAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */