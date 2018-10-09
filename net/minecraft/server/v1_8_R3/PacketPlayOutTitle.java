/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutTitle
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private EnumTitleAction a;
/*     */   private IChatBaseComponent b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   
/*     */   public PacketPlayOutTitle() {}
/*     */   
/*     */   public PacketPlayOutTitle(EnumTitleAction paramEnumTitleAction, IChatBaseComponent paramIChatBaseComponent)
/*     */   {
/*  22 */     this(paramEnumTitleAction, paramIChatBaseComponent, -1, -1, -1);
/*     */   }
/*     */   
/*     */   public PacketPlayOutTitle(int paramInt1, int paramInt2, int paramInt3) {
/*  26 */     this(EnumTitleAction.TIMES, null, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public PacketPlayOutTitle(EnumTitleAction paramEnumTitleAction, IChatBaseComponent paramIChatBaseComponent, int paramInt1, int paramInt2, int paramInt3) {
/*  30 */     this.a = paramEnumTitleAction;
/*  31 */     this.b = paramIChatBaseComponent;
/*  32 */     this.c = paramInt1;
/*  33 */     this.d = paramInt2;
/*  34 */     this.e = paramInt3;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  39 */     this.a = ((EnumTitleAction)paramPacketDataSerializer.a(EnumTitleAction.class));
/*  40 */     if ((this.a == EnumTitleAction.TITLE) || (this.a == EnumTitleAction.SUBTITLE)) {
/*  41 */       this.b = paramPacketDataSerializer.d();
/*     */     }
/*  43 */     if (this.a == EnumTitleAction.TIMES) {
/*  44 */       this.c = paramPacketDataSerializer.readInt();
/*  45 */       this.d = paramPacketDataSerializer.readInt();
/*  46 */       this.e = paramPacketDataSerializer.readInt();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  52 */     paramPacketDataSerializer.a(this.a);
/*  53 */     if ((this.a == EnumTitleAction.TITLE) || (this.a == EnumTitleAction.SUBTITLE)) {
/*  54 */       paramPacketDataSerializer.a(this.b);
/*     */     }
/*  56 */     if (this.a == EnumTitleAction.TIMES) {
/*  57 */       paramPacketDataSerializer.writeInt(this.c);
/*  58 */       paramPacketDataSerializer.writeInt(this.d);
/*  59 */       paramPacketDataSerializer.writeInt(this.e);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/*  65 */     paramPacketListenerPlayOut.a(this);
/*     */   }
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
/*     */   public static enum EnumTitleAction
/*     */   {
/*     */     private EnumTitleAction() {}
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
/*     */     public static EnumTitleAction a(String paramString)
/*     */     {
/*  97 */       for (EnumTitleAction localEnumTitleAction : ) {
/*  98 */         if (localEnumTitleAction.name().equalsIgnoreCase(paramString)) {
/*  99 */           return localEnumTitleAction;
/*     */         }
/*     */       }
/* 102 */       return TITLE;
/*     */     }
/*     */     
/*     */     public static String[] a() {
/* 106 */       String[] arrayOfString = new String[values().length];
/* 107 */       int i = 0;
/* 108 */       for (EnumTitleAction localEnumTitleAction : values()) {
/* 109 */         arrayOfString[(i++)] = localEnumTitleAction.name().toLowerCase();
/*     */       }
/* 111 */       return arrayOfString;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */