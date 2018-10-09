/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ public class ServerPing
/*     */ {
/*     */   private IChatBaseComponent a;
/*     */   private ServerPingPlayerSample b;
/*     */   private ServerData c;
/*     */   private String d;
/*     */   
/*     */   public IChatBaseComponent a()
/*     */   {
/*  21 */     return this.a;
/*     */   }
/*     */   
/*     */   public void setMOTD(IChatBaseComponent paramIChatBaseComponent) {
/*  25 */     this.a = paramIChatBaseComponent;
/*     */   }
/*     */   
/*     */   public ServerPingPlayerSample b() {
/*  29 */     return this.b;
/*     */   }
/*     */   
/*     */   public void setPlayerSample(ServerPingPlayerSample paramServerPingPlayerSample) {
/*  33 */     this.b = paramServerPingPlayerSample;
/*     */   }
/*     */   
/*     */   public ServerData c() {
/*  37 */     return this.c;
/*     */   }
/*     */   
/*     */   public void setServerInfo(ServerData paramServerData) {
/*  41 */     this.c = paramServerData;
/*     */   }
/*     */   
/*     */   public void setFavicon(String paramString) {
/*  45 */     this.d = paramString;
/*     */   }
/*     */   
/*     */   public String d() {
/*  49 */     return this.d;
/*     */   }
/*     */   
/*     */   public static class ServerPingPlayerSample {
/*     */     private final int a;
/*     */     private final int b;
/*     */     private GameProfile[] c;
/*     */     
/*     */     public ServerPingPlayerSample(int paramInt1, int paramInt2) {
/*  58 */       this.a = paramInt1;
/*  59 */       this.b = paramInt2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  63 */       return this.a;
/*     */     }
/*     */     
/*     */     public int b() {
/*  67 */       return this.b;
/*     */     }
/*     */     
/*     */     public GameProfile[] c() {
/*  71 */       return this.c;
/*     */     }
/*     */     
/*     */     public void a(GameProfile[] paramArrayOfGameProfile) {
/*  75 */       this.c = paramArrayOfGameProfile;
/*     */     }
/*     */     
/*     */     public static class Serializer implements com.google.gson.JsonDeserializer<ServerPing.ServerPingPlayerSample>, com.google.gson.JsonSerializer<ServerPing.ServerPingPlayerSample>
/*     */     {
/*     */       public ServerPing.ServerPingPlayerSample a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
/*  81 */         JsonObject localJsonObject1 = ChatDeserializer.l(paramJsonElement, "players");
/*  82 */         ServerPing.ServerPingPlayerSample localServerPingPlayerSample = new ServerPing.ServerPingPlayerSample(ChatDeserializer.m(localJsonObject1, "max"), ChatDeserializer.m(localJsonObject1, "online"));
/*     */         
/*  84 */         if (ChatDeserializer.d(localJsonObject1, "sample")) {
/*  85 */           JsonArray localJsonArray = ChatDeserializer.t(localJsonObject1, "sample");
/*  86 */           if (localJsonArray.size() > 0) {
/*  87 */             GameProfile[] arrayOfGameProfile = new GameProfile[localJsonArray.size()];
/*  88 */             for (int i = 0; i < arrayOfGameProfile.length; i++) {
/*  89 */               JsonObject localJsonObject2 = ChatDeserializer.l(localJsonArray.get(i), "player[" + i + "]");
/*  90 */               String str = ChatDeserializer.h(localJsonObject2, "id");
/*  91 */               arrayOfGameProfile[i] = new GameProfile(java.util.UUID.fromString(str), ChatDeserializer.h(localJsonObject2, "name"));
/*     */             }
/*  93 */             localServerPingPlayerSample.a(arrayOfGameProfile);
/*     */           }
/*     */         }
/*     */         
/*  97 */         return localServerPingPlayerSample;
/*     */       }
/*     */       
/*     */       public JsonElement a(ServerPing.ServerPingPlayerSample paramServerPingPlayerSample, Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*     */       {
/* 102 */         JsonObject localJsonObject1 = new JsonObject();
/*     */         
/* 104 */         localJsonObject1.addProperty("max", Integer.valueOf(paramServerPingPlayerSample.a()));
/* 105 */         localJsonObject1.addProperty("online", Integer.valueOf(paramServerPingPlayerSample.b()));
/*     */         
/* 107 */         if ((paramServerPingPlayerSample.c() != null) && (paramServerPingPlayerSample.c().length > 0)) {
/* 108 */           JsonArray localJsonArray = new JsonArray();
/*     */           
/* 110 */           for (int i = 0; i < paramServerPingPlayerSample.c().length; i++) {
/* 111 */             JsonObject localJsonObject2 = new JsonObject();
/* 112 */             java.util.UUID localUUID = paramServerPingPlayerSample.c()[i].getId();
/* 113 */             localJsonObject2.addProperty("id", localUUID == null ? "" : localUUID.toString());
/* 114 */             localJsonObject2.addProperty("name", paramServerPingPlayerSample.c()[i].getName());
/* 115 */             localJsonArray.add(localJsonObject2);
/*     */           }
/*     */           
/* 118 */           localJsonObject1.add("sample", localJsonArray);
/*     */         }
/*     */         
/* 121 */         return localJsonObject1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ServerData {
/*     */     private final String a;
/*     */     private final int b;
/*     */     
/*     */     public ServerData(String paramString, int paramInt) {
/* 131 */       this.a = paramString;
/* 132 */       this.b = paramInt;
/*     */     }
/*     */     
/*     */     public String a() {
/* 136 */       return this.a;
/*     */     }
/*     */     
/*     */     public int b() {
/* 140 */       return this.b;
/*     */     }
/*     */     
/*     */     public static class Serializer implements com.google.gson.JsonDeserializer<ServerPing.ServerData>, com.google.gson.JsonSerializer<ServerPing.ServerData>
/*     */     {
/*     */       public ServerPing.ServerData a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
/* 146 */         JsonObject localJsonObject = ChatDeserializer.l(paramJsonElement, "version");
/* 147 */         return new ServerPing.ServerData(ChatDeserializer.h(localJsonObject, "name"), ChatDeserializer.m(localJsonObject, "protocol"));
/*     */       }
/*     */       
/*     */       public JsonElement a(ServerPing.ServerData paramServerData, Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*     */       {
/* 152 */         JsonObject localJsonObject = new JsonObject();
/* 153 */         localJsonObject.addProperty("name", paramServerData.a());
/* 154 */         localJsonObject.addProperty("protocol", Integer.valueOf(paramServerData.b()));
/* 155 */         return localJsonObject;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer implements com.google.gson.JsonDeserializer<ServerPing>, com.google.gson.JsonSerializer<ServerPing>
/*     */   {
/*     */     public ServerPing a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
/* 163 */       JsonObject localJsonObject = ChatDeserializer.l(paramJsonElement, "status");
/* 164 */       ServerPing localServerPing = new ServerPing();
/*     */       
/* 166 */       if (localJsonObject.has("description")) {
/* 167 */         localServerPing.setMOTD((IChatBaseComponent)paramJsonDeserializationContext.deserialize(localJsonObject.get("description"), IChatBaseComponent.class));
/*     */       }
/*     */       
/* 170 */       if (localJsonObject.has("players")) {
/* 171 */         localServerPing.setPlayerSample((ServerPing.ServerPingPlayerSample)paramJsonDeserializationContext.deserialize(localJsonObject.get("players"), ServerPing.ServerPingPlayerSample.class));
/*     */       }
/*     */       
/* 174 */       if (localJsonObject.has("version")) {
/* 175 */         localServerPing.setServerInfo((ServerPing.ServerData)paramJsonDeserializationContext.deserialize(localJsonObject.get("version"), ServerPing.ServerData.class));
/*     */       }
/*     */       
/* 178 */       if (localJsonObject.has("favicon")) {
/* 179 */         localServerPing.setFavicon(ChatDeserializer.h(localJsonObject, "favicon"));
/*     */       }
/*     */       
/* 182 */       return localServerPing;
/*     */     }
/*     */     
/*     */     public JsonElement a(ServerPing paramServerPing, Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*     */     {
/* 187 */       JsonObject localJsonObject = new JsonObject();
/*     */       
/* 189 */       if (paramServerPing.a() != null) {
/* 190 */         localJsonObject.add("description", paramJsonSerializationContext.serialize(paramServerPing.a()));
/*     */       }
/*     */       
/* 193 */       if (paramServerPing.b() != null) {
/* 194 */         localJsonObject.add("players", paramJsonSerializationContext.serialize(paramServerPing.b()));
/*     */       }
/*     */       
/* 197 */       if (paramServerPing.c() != null) {
/* 198 */         localJsonObject.add("version", paramJsonSerializationContext.serialize(paramServerPing.c()));
/*     */       }
/*     */       
/* 201 */       if (paramServerPing.d() != null) {
/* 202 */         localJsonObject.addProperty("favicon", paramServerPing.d());
/*     */       }
/*     */       
/* 205 */       return localJsonObject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerPing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */