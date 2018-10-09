/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutPlayerInfo
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private EnumPlayerInfoAction a;
/*  20 */   private final List<PlayerInfoData> b = Lists.newArrayList();
/*     */   
/*     */ 
/*     */   public PacketPlayOutPlayerInfo() {}
/*     */   
/*     */   public PacketPlayOutPlayerInfo(EnumPlayerInfoAction paramEnumPlayerInfoAction, EntityPlayer... paramVarArgs)
/*     */   {
/*  27 */     this.a = paramEnumPlayerInfoAction;
/*     */     
/*  29 */     for (EntityPlayer localEntityPlayer : paramVarArgs) {
/*  30 */       this.b.add(new PlayerInfoData(localEntityPlayer.getProfile(), localEntityPlayer.ping, localEntityPlayer.playerInteractManager.getGameMode(), localEntityPlayer.getPlayerListName()));
/*     */     }
/*     */   }
/*     */   
/*     */   public PacketPlayOutPlayerInfo(EnumPlayerInfoAction paramEnumPlayerInfoAction, Iterable<EntityPlayer> paramIterable) {
/*  35 */     this.a = paramEnumPlayerInfoAction;
/*     */     
/*  37 */     for (EntityPlayer localEntityPlayer : paramIterable) {
/*  38 */       this.b.add(new PlayerInfoData(localEntityPlayer.getProfile(), localEntityPlayer.ping, localEntityPlayer.playerInteractManager.getGameMode(), localEntityPlayer.getPlayerListName()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  44 */     this.a = ((EnumPlayerInfoAction)paramPacketDataSerializer.a(EnumPlayerInfoAction.class));
/*     */     
/*  46 */     int i = paramPacketDataSerializer.e();
/*  47 */     for (int j = 0; j < i; j++) {
/*  48 */       GameProfile localGameProfile = null;
/*  49 */       int k = 0;
/*  50 */       WorldSettings.EnumGamemode localEnumGamemode = null;
/*  51 */       IChatBaseComponent localIChatBaseComponent = null;
/*     */       
/*  53 */       switch (1.a[this.a.ordinal()]) {
/*     */       case 1: 
/*  55 */         localGameProfile = new GameProfile(paramPacketDataSerializer.g(), paramPacketDataSerializer.c(16));
/*  56 */         int m = paramPacketDataSerializer.e();
/*  57 */         for (int n = 0; n < m; n++) {
/*  58 */           String str1 = paramPacketDataSerializer.c(32767);
/*  59 */           String str2 = paramPacketDataSerializer.c(32767);
/*     */           
/*  61 */           if (paramPacketDataSerializer.readBoolean()) {
/*  62 */             localGameProfile.getProperties().put(str1, new Property(str1, str2, paramPacketDataSerializer.c(32767)));
/*     */           } else {
/*  64 */             localGameProfile.getProperties().put(str1, new Property(str1, str2));
/*     */           }
/*     */         }
/*  67 */         localEnumGamemode = WorldSettings.EnumGamemode.getById(paramPacketDataSerializer.e());
/*  68 */         k = paramPacketDataSerializer.e();
/*  69 */         if (paramPacketDataSerializer.readBoolean()) {
/*  70 */           localIChatBaseComponent = paramPacketDataSerializer.d();
/*     */         }
/*     */         break;
/*     */       case 2: 
/*  74 */         localGameProfile = new GameProfile(paramPacketDataSerializer.g(), null);
/*  75 */         localEnumGamemode = WorldSettings.EnumGamemode.getById(paramPacketDataSerializer.e());
/*  76 */         break;
/*     */       case 3: 
/*  78 */         localGameProfile = new GameProfile(paramPacketDataSerializer.g(), null);
/*  79 */         k = paramPacketDataSerializer.e();
/*  80 */         break;
/*     */       case 4: 
/*  82 */         localGameProfile = new GameProfile(paramPacketDataSerializer.g(), null);
/*  83 */         if (paramPacketDataSerializer.readBoolean()) {
/*  84 */           localIChatBaseComponent = paramPacketDataSerializer.d();
/*     */         }
/*     */         break;
/*     */       case 5: 
/*  88 */         localGameProfile = new GameProfile(paramPacketDataSerializer.g(), null);
/*     */       }
/*     */       
/*     */       
/*  92 */       this.b.add(new PlayerInfoData(localGameProfile, k, localEnumGamemode, localIChatBaseComponent));
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  98 */     paramPacketDataSerializer.a(this.a);
/*     */     
/* 100 */     paramPacketDataSerializer.b(this.b.size());
/* 101 */     for (PlayerInfoData localPlayerInfoData : this.b) {
/* 102 */       switch (1.a[this.a.ordinal()]) {
/*     */       case 1: 
/* 104 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getId());
/* 105 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getName());
/* 106 */         paramPacketDataSerializer.b(localPlayerInfoData.a().getProperties().size());
/* 107 */         for (Property localProperty : localPlayerInfoData.a().getProperties().values()) {
/* 108 */           paramPacketDataSerializer.a(localProperty.getName());
/* 109 */           paramPacketDataSerializer.a(localProperty.getValue());
/* 110 */           if (localProperty.hasSignature()) {
/* 111 */             paramPacketDataSerializer.writeBoolean(true);
/* 112 */             paramPacketDataSerializer.a(localProperty.getSignature());
/*     */           } else {
/* 114 */             paramPacketDataSerializer.writeBoolean(false);
/*     */           }
/*     */         }
/* 117 */         paramPacketDataSerializer.b(localPlayerInfoData.c().getId());
/* 118 */         paramPacketDataSerializer.b(localPlayerInfoData.b());
/*     */         
/* 120 */         if (localPlayerInfoData.d() == null) {
/* 121 */           paramPacketDataSerializer.writeBoolean(false);
/*     */         } else {
/* 123 */           paramPacketDataSerializer.writeBoolean(true);
/* 124 */           paramPacketDataSerializer.a(localPlayerInfoData.d());
/*     */         }
/* 126 */         break;
/*     */       case 2: 
/* 128 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getId());
/* 129 */         paramPacketDataSerializer.b(localPlayerInfoData.c().getId());
/* 130 */         break;
/*     */       case 3: 
/* 132 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getId());
/* 133 */         paramPacketDataSerializer.b(localPlayerInfoData.b());
/* 134 */         break;
/*     */       case 4: 
/* 136 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getId());
/* 137 */         if (localPlayerInfoData.d() == null) {
/* 138 */           paramPacketDataSerializer.writeBoolean(false);
/*     */         } else {
/* 140 */           paramPacketDataSerializer.writeBoolean(true);
/* 141 */           paramPacketDataSerializer.a(localPlayerInfoData.d());
/*     */         }
/* 143 */         break;
/*     */       case 5: 
/* 145 */         paramPacketDataSerializer.a(localPlayerInfoData.a().getId());
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 153 */     paramPacketListenerPlayOut.a(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 175 */     return Objects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public class PlayerInfoData
/*     */   {
/*     */     private final int b;
/*     */     private final WorldSettings.EnumGamemode c;
/*     */     private final GameProfile d;
/*     */     private final IChatBaseComponent e;
/*     */     
/*     */     public PlayerInfoData(GameProfile paramGameProfile, int paramInt, WorldSettings.EnumGamemode paramEnumGamemode, IChatBaseComponent paramIChatBaseComponent)
/*     */     {
/* 188 */       this.d = paramGameProfile;
/* 189 */       this.b = paramInt;
/* 190 */       this.c = paramEnumGamemode;
/* 191 */       this.e = paramIChatBaseComponent;
/*     */     }
/*     */     
/*     */     public GameProfile a() {
/* 195 */       return this.d;
/*     */     }
/*     */     
/*     */     public int b() {
/* 199 */       return this.b;
/*     */     }
/*     */     
/*     */     public WorldSettings.EnumGamemode c() {
/* 203 */       return this.c;
/*     */     }
/*     */     
/*     */     public IChatBaseComponent d()
/*     */     {
/* 208 */       return this.e;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 213 */       return Objects.toStringHelper(this).add("latency", this.b).add("gameMode", this.c).add("profile", this.d).add("displayName", this.e == null ? null : IChatBaseComponent.ChatSerializer.a(this.e)).toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum EnumPlayerInfoAction
/*     */   {
/*     */     private EnumPlayerInfoAction() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutPlayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */