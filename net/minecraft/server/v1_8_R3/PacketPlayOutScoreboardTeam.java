/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutScoreboardTeam
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*  22 */   private String a = "";
/*  23 */   private String b = "";
/*  24 */   private String c = "";
/*  25 */   private String d = "";
/*  26 */   private String e = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS.e;
/*  27 */   private int f = -1;
/*  28 */   private Collection<String> g = Lists.newArrayList();
/*     */   private int h;
/*     */   private int i;
/*     */   
/*     */   public PacketPlayOutScoreboardTeam() {}
/*     */   
/*     */   public PacketPlayOutScoreboardTeam(ScoreboardTeam paramScoreboardTeam, int paramInt)
/*     */   {
/*  36 */     this.a = paramScoreboardTeam.getName();
/*  37 */     this.h = paramInt;
/*     */     
/*  39 */     if ((paramInt == 0) || (paramInt == 2)) {
/*  40 */       this.b = paramScoreboardTeam.getDisplayName();
/*  41 */       this.c = paramScoreboardTeam.getPrefix();
/*  42 */       this.d = paramScoreboardTeam.getSuffix();
/*  43 */       this.i = paramScoreboardTeam.packOptionData();
/*  44 */       this.e = paramScoreboardTeam.getNameTagVisibility().e;
/*  45 */       this.f = paramScoreboardTeam.l().b();
/*     */     }
/*  47 */     if (paramInt == 0) {
/*  48 */       this.g.addAll(paramScoreboardTeam.getPlayerNameSet());
/*     */     }
/*     */   }
/*     */   
/*     */   public PacketPlayOutScoreboardTeam(ScoreboardTeam paramScoreboardTeam, Collection<String> paramCollection, int paramInt) {
/*  53 */     if ((paramInt != 3) && (paramInt != 4)) {
/*  54 */       throw new IllegalArgumentException("Method must be join or leave for player constructor");
/*     */     }
/*  56 */     if ((paramCollection == null) || (paramCollection.isEmpty())) {
/*  57 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*     */     }
/*     */     
/*  60 */     this.h = paramInt;
/*  61 */     this.a = paramScoreboardTeam.getName();
/*  62 */     this.g.addAll(paramCollection);
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  67 */     this.a = paramPacketDataSerializer.c(16);
/*  68 */     this.h = paramPacketDataSerializer.readByte();
/*     */     
/*  70 */     if ((this.h == 0) || (this.h == 2)) {
/*  71 */       this.b = paramPacketDataSerializer.c(32);
/*  72 */       this.c = paramPacketDataSerializer.c(16);
/*  73 */       this.d = paramPacketDataSerializer.c(16);
/*  74 */       this.i = paramPacketDataSerializer.readByte();
/*  75 */       this.e = paramPacketDataSerializer.c(32);
/*  76 */       this.f = paramPacketDataSerializer.readByte();
/*     */     }
/*     */     
/*  79 */     if ((this.h == 0) || (this.h == 3) || (this.h == 4)) {
/*  80 */       int j = paramPacketDataSerializer.e();
/*     */       
/*  82 */       for (int k = 0; k < j; k++) {
/*  83 */         this.g.add(paramPacketDataSerializer.c(40));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  90 */     paramPacketDataSerializer.a(this.a);
/*  91 */     paramPacketDataSerializer.writeByte(this.h);
/*     */     
/*  93 */     if ((this.h == 0) || (this.h == 2)) {
/*  94 */       paramPacketDataSerializer.a(this.b);
/*  95 */       paramPacketDataSerializer.a(this.c);
/*  96 */       paramPacketDataSerializer.a(this.d);
/*  97 */       paramPacketDataSerializer.writeByte(this.i);
/*  98 */       paramPacketDataSerializer.a(this.e);
/*  99 */       paramPacketDataSerializer.writeByte(this.f);
/*     */     }
/*     */     
/* 102 */     if ((this.h == 0) || (this.h == 3) || (this.h == 4)) {
/* 103 */       paramPacketDataSerializer.b(this.g.size());
/*     */       
/* 105 */       for (String str : this.g) {
/* 106 */         paramPacketDataSerializer.a(str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 113 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutScoreboardTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */