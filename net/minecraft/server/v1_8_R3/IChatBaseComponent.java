/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public abstract interface IChatBaseComponent extends Iterable<IChatBaseComponent>
/*     */ {
/*     */   public abstract IChatBaseComponent setChatModifier(ChatModifier paramChatModifier);
/*     */   
/*     */   public abstract ChatModifier getChatModifier();
/*     */   
/*     */   public abstract IChatBaseComponent a(String paramString);
/*     */   
/*     */   public abstract IChatBaseComponent addSibling(IChatBaseComponent paramIChatBaseComponent);
/*     */   
/*     */   public abstract String getText();
/*     */   
/*     */   public abstract String c();
/*     */   
/*     */   public abstract java.util.List<IChatBaseComponent> a();
/*     */   
/*     */   public abstract IChatBaseComponent f();
/*     */   
/*     */   public static class ChatSerializer implements com.google.gson.JsonDeserializer<IChatBaseComponent>, com.google.gson.JsonSerializer<IChatBaseComponent>
/*     */   {
/*     */     private static final com.google.gson.Gson a;
/*     */     
/*     */     static
/*     */     {
/*  34 */       GsonBuilder localGsonBuilder = new GsonBuilder();
/*  35 */       localGsonBuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer());
/*  36 */       localGsonBuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer());
/*  37 */       localGsonBuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
/*  38 */       a = localGsonBuilder.create();
/*     */     }
/*     */     
/*     */     public IChatBaseComponent a(JsonElement paramJsonElement, java.lang.reflect.Type paramType, com.google.gson.JsonDeserializationContext paramJsonDeserializationContext) throws com.google.gson.JsonParseException
/*     */     {
/*  43 */       if (paramJsonElement.isJsonPrimitive())
/*     */       {
/*  45 */         return new ChatComponentText(paramJsonElement.getAsString()); }
/*  46 */       Object localObject1; Object localObject2; Object localObject3; Object localObject4; if (paramJsonElement.isJsonObject()) {
/*  47 */         localObject1 = paramJsonElement.getAsJsonObject();
/*     */         
/*     */ 
/*  50 */         if (((JsonObject)localObject1).has("text")) {
/*  51 */           localObject2 = new ChatComponentText(((JsonObject)localObject1).get("text").getAsString());
/*  52 */         } else if (((JsonObject)localObject1).has("translate")) {
/*  53 */           localObject3 = ((JsonObject)localObject1).get("translate").getAsString();
/*     */           
/*  55 */           if (((JsonObject)localObject1).has("with")) {
/*  56 */             JsonArray localJsonArray = ((JsonObject)localObject1).getAsJsonArray("with");
/*  57 */             localObject4 = new Object[localJsonArray.size()];
/*     */             
/*  59 */             for (int j = 0; j < localObject4.length; j++) {
/*  60 */               localObject4[j] = a(localJsonArray.get(j), paramType, paramJsonDeserializationContext);
/*     */               
/*  62 */               if ((localObject4[j] instanceof ChatComponentText)) {
/*  63 */                 ChatComponentText localChatComponentText = (ChatComponentText)localObject4[j];
/*  64 */                 if ((localChatComponentText.getChatModifier().g()) && (localChatComponentText.a().isEmpty())) {
/*  65 */                   localObject4[j] = localChatComponentText.g();
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*  70 */             localObject2 = new ChatMessage((String)localObject3, (Object[])localObject4);
/*     */           } else {
/*  72 */             localObject2 = new ChatMessage((String)localObject3, new Object[0]);
/*     */           }
/*  74 */         } else if (((JsonObject)localObject1).has("score")) {
/*  75 */           localObject3 = ((JsonObject)localObject1).getAsJsonObject("score");
/*  76 */           if ((((JsonObject)localObject3).has("name")) && (((JsonObject)localObject3).has("objective"))) {
/*  77 */             localObject2 = new ChatComponentScore(ChatDeserializer.h((JsonObject)localObject3, "name"), ChatDeserializer.h((JsonObject)localObject3, "objective"));
/*  78 */             if (((JsonObject)localObject3).has("value")) {
/*  79 */               ((ChatComponentScore)localObject2).b(ChatDeserializer.h((JsonObject)localObject3, "value"));
/*     */             }
/*     */           } else {
/*  82 */             throw new com.google.gson.JsonParseException("A score component needs a least a name and an objective");
/*     */           }
/*  84 */         } else if (((JsonObject)localObject1).has("selector")) {
/*  85 */           localObject2 = new ChatComponentSelector(ChatDeserializer.h((JsonObject)localObject1, "selector"));
/*     */         } else {
/*  87 */           throw new com.google.gson.JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
/*     */         }
/*     */         
/*  90 */         if (((JsonObject)localObject1).has("extra")) {
/*  91 */           localObject3 = ((JsonObject)localObject1).getAsJsonArray("extra");
/*     */           
/*  93 */           if (((JsonArray)localObject3).size() > 0) {
/*  94 */             for (int i = 0; i < ((JsonArray)localObject3).size(); i++) {
/*  95 */               ((IChatBaseComponent)localObject2).addSibling(a(((JsonArray)localObject3).get(i), paramType, paramJsonDeserializationContext));
/*     */             }
/*     */           } else {
/*  98 */             throw new com.google.gson.JsonParseException("Unexpected empty array of components");
/*     */           }
/*     */         }
/*     */         
/* 102 */         ((IChatBaseComponent)localObject2).setChatModifier((ChatModifier)paramJsonDeserializationContext.deserialize(paramJsonElement, ChatModifier.class));
/*     */         
/* 104 */         return (IChatBaseComponent)localObject2; }
/* 105 */       if (paramJsonElement.isJsonArray())
/*     */       {
/* 107 */         localObject1 = paramJsonElement.getAsJsonArray();
/* 108 */         localObject2 = null;
/*     */         
/* 110 */         for (localObject3 = ((JsonArray)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { JsonElement localJsonElement = (JsonElement)((Iterator)localObject3).next();
/* 111 */           localObject4 = a(localJsonElement, localJsonElement.getClass(), paramJsonDeserializationContext);
/* 112 */           if (localObject2 == null) {
/* 113 */             localObject2 = localObject4;
/*     */           } else {
/* 115 */             ((IChatBaseComponent)localObject2).addSibling((IChatBaseComponent)localObject4);
/*     */           }
/*     */         }
/*     */         
/* 119 */         return (IChatBaseComponent)localObject2;
/*     */       }
/* 121 */       throw new com.google.gson.JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
/*     */     }
/*     */     
/*     */     private void a(ChatModifier paramChatModifier, JsonObject paramJsonObject, JsonSerializationContext paramJsonSerializationContext)
/*     */     {
/* 126 */       JsonElement localJsonElement = paramJsonSerializationContext.serialize(paramChatModifier);
/*     */       
/* 128 */       if (localJsonElement.isJsonObject()) {
/* 129 */         JsonObject localJsonObject = (JsonObject)localJsonElement;
/* 130 */         for (java.util.Map.Entry localEntry : localJsonObject.entrySet()) {
/* 131 */           paramJsonObject.add((String)localEntry.getKey(), (JsonElement)localEntry.getValue());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public JsonElement a(IChatBaseComponent paramIChatBaseComponent, java.lang.reflect.Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*     */     {
/* 138 */       if (((paramIChatBaseComponent instanceof ChatComponentText)) && (paramIChatBaseComponent.getChatModifier().g()) && (paramIChatBaseComponent.a().isEmpty())) {
/* 139 */         return new com.google.gson.JsonPrimitive(((ChatComponentText)paramIChatBaseComponent).g());
/*     */       }
/*     */       
/* 142 */       JsonObject localJsonObject = new JsonObject();
/*     */       
/* 144 */       if (!paramIChatBaseComponent.getChatModifier().g())
/* 145 */         a(paramIChatBaseComponent.getChatModifier(), localJsonObject, paramJsonSerializationContext);
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 148 */       Object localObject3; if (!paramIChatBaseComponent.a().isEmpty()) {
/* 149 */         localObject1 = new JsonArray();
/*     */         
/* 151 */         for (localObject2 = paramIChatBaseComponent.a().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (IChatBaseComponent)((Iterator)localObject2).next();
/* 152 */           ((JsonArray)localObject1).add(a((IChatBaseComponent)localObject3, localObject3.getClass(), paramJsonSerializationContext));
/*     */         }
/*     */         
/* 155 */         localJsonObject.add("extra", (JsonElement)localObject1);
/*     */       }
/*     */       
/* 158 */       if ((paramIChatBaseComponent instanceof ChatComponentText)) {
/* 159 */         localJsonObject.addProperty("text", ((ChatComponentText)paramIChatBaseComponent).g());
/* 160 */       } else if ((paramIChatBaseComponent instanceof ChatMessage)) {
/* 161 */         localObject1 = (ChatMessage)paramIChatBaseComponent;
/* 162 */         localJsonObject.addProperty("translate", ((ChatMessage)localObject1).i());
/*     */         
/* 164 */         if ((((ChatMessage)localObject1).j() != null) && (((ChatMessage)localObject1).j().length > 0)) {
/* 165 */           localObject2 = new JsonArray();
/*     */           
/* 167 */           for (Object localObject4 : ((ChatMessage)localObject1).j()) {
/* 168 */             if ((localObject4 instanceof IChatBaseComponent)) {
/* 169 */               ((JsonArray)localObject2).add(a((IChatBaseComponent)localObject4, localObject4.getClass(), paramJsonSerializationContext));
/*     */             } else {
/* 171 */               ((JsonArray)localObject2).add(new com.google.gson.JsonPrimitive(String.valueOf(localObject4)));
/*     */             }
/*     */           }
/*     */           
/* 175 */           localJsonObject.add("with", (JsonElement)localObject2);
/*     */         }
/* 177 */       } else if ((paramIChatBaseComponent instanceof ChatComponentScore)) {
/* 178 */         localObject1 = (ChatComponentScore)paramIChatBaseComponent;
/* 179 */         localObject2 = new JsonObject();
/* 180 */         ((JsonObject)localObject2).addProperty("name", ((ChatComponentScore)localObject1).g());
/* 181 */         ((JsonObject)localObject2).addProperty("objective", ((ChatComponentScore)localObject1).h());
/* 182 */         ((JsonObject)localObject2).addProperty("value", ((ChatComponentScore)localObject1).getText());
/* 183 */         localJsonObject.add("score", (JsonElement)localObject2);
/* 184 */       } else if ((paramIChatBaseComponent instanceof ChatComponentSelector)) {
/* 185 */         localObject1 = (ChatComponentSelector)paramIChatBaseComponent;
/* 186 */         localJsonObject.addProperty("selector", ((ChatComponentSelector)localObject1).g());
/*     */       } else {
/* 188 */         throw new IllegalArgumentException("Don't know how to serialize " + paramIChatBaseComponent + " as a Component");
/*     */       }
/*     */       
/* 191 */       return localJsonObject;
/*     */     }
/*     */     
/*     */     public static String a(IChatBaseComponent paramIChatBaseComponent) {
/* 195 */       return a.toJson(paramIChatBaseComponent);
/*     */     }
/*     */     
/*     */     public static IChatBaseComponent a(String paramString) {
/* 199 */       return (IChatBaseComponent)a.fromJson(paramString, IChatBaseComponent.class);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IChatBaseComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */