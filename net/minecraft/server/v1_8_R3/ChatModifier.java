/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ public class ChatModifier
/*     */ {
/*     */   private ChatModifier a;
/*     */   private EnumChatFormat b;
/*     */   private Boolean c;
/*     */   private Boolean d;
/*     */   private Boolean e;
/*     */   private Boolean f;
/*     */   private Boolean g;
/*     */   private ChatClickable h;
/*     */   private ChatHoverable i;
/*     */   private String j;
/*  25 */   private static final ChatModifier k = new ChatModifier() {
/*     */     public EnumChatFormat getColor() {
/*  27 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isBold() {
/*  31 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isItalic() {
/*  35 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isStrikethrough() {
/*  39 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isUnderlined() {
/*  43 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isRandom() {
/*  47 */       return false;
/*     */     }
/*     */     
/*     */     public ChatClickable h() {
/*  51 */       return null;
/*     */     }
/*     */     
/*     */     public ChatHoverable i() {
/*  55 */       return null;
/*     */     }
/*     */     
/*     */     public String j() {
/*  59 */       return null;
/*     */     }
/*     */     
/*     */     public ChatModifier setColor(EnumChatFormat enumchatformat) {
/*  63 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setBold(Boolean obool) {
/*  67 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setItalic(Boolean obool) {
/*  71 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setStrikethrough(Boolean obool) {
/*  75 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setUnderline(Boolean obool) {
/*  79 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setRandom(Boolean obool) {
/*  83 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setChatClickable(ChatClickable chatclickable) {
/*  87 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setChatHoverable(ChatHoverable chathoverable) {
/*  91 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ChatModifier setChatModifier(ChatModifier chatmodifier) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String toString() {
/*  99 */       return "Style.ROOT";
/*     */     }
/*     */     
/*     */     public ChatModifier clone() {
/* 103 */       return this;
/*     */     }
/*     */     
/*     */     public ChatModifier n() {
/* 107 */       return this;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   public EnumChatFormat getColor()
/*     */   {
/* 114 */     return this.b == null ? o().getColor() : this.b;
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/* 118 */     return this.c == null ? o().isBold() : this.c.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/* 122 */     return this.d == null ? o().isItalic() : this.d.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isStrikethrough() {
/* 126 */     return this.f == null ? o().isStrikethrough() : this.f.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isUnderlined() {
/* 130 */     return this.e == null ? o().isUnderlined() : this.e.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isRandom() {
/* 134 */     return this.g == null ? o().isRandom() : this.g.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 138 */     return (this.c == null) && (this.d == null) && (this.f == null) && (this.e == null) && (this.g == null) && (this.b == null) && (this.h == null) && (this.i == null);
/*     */   }
/*     */   
/*     */   public ChatClickable h() {
/* 142 */     return this.h == null ? o().h() : this.h;
/*     */   }
/*     */   
/*     */   public ChatHoverable i() {
/* 146 */     return this.i == null ? o().i() : this.i;
/*     */   }
/*     */   
/*     */   public String j() {
/* 150 */     return this.j == null ? o().j() : this.j;
/*     */   }
/*     */   
/*     */   public ChatModifier setColor(EnumChatFormat enumchatformat) {
/* 154 */     this.b = enumchatformat;
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setBold(Boolean obool) {
/* 159 */     this.c = obool;
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setItalic(Boolean obool) {
/* 164 */     this.d = obool;
/* 165 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setStrikethrough(Boolean obool) {
/* 169 */     this.f = obool;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setUnderline(Boolean obool) {
/* 174 */     this.e = obool;
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setRandom(Boolean obool) {
/* 179 */     this.g = obool;
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setChatClickable(ChatClickable chatclickable) {
/* 184 */     this.h = chatclickable;
/* 185 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setChatHoverable(ChatHoverable chathoverable) {
/* 189 */     this.i = chathoverable;
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setInsertion(String s) {
/* 194 */     this.j = s;
/* 195 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setChatModifier(ChatModifier chatmodifier) {
/* 199 */     this.a = chatmodifier;
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   private ChatModifier o() {
/* 204 */     return this.a == null ? k : this.a;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 208 */     return "Style{hasParent=" + (this.a != null) + ", color=" + this.b + ", bold=" + this.c + ", italic=" + this.d + ", underlined=" + this.e + ", obfuscated=" + this.g + ", clickEvent=" + h() + ", hoverEvent=" + i() + ", insertion=" + j() + '}';
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 212 */     if (this == object)
/* 213 */       return true;
/* 214 */     if (!(object instanceof ChatModifier)) {
/* 215 */       return false;
/*     */     }
/* 217 */     ChatModifier chatmodifier = (ChatModifier)object;
/*     */     
/*     */ 
/* 220 */     if ((isBold() == chatmodifier.isBold()) && (getColor() == chatmodifier.getColor()) && (isItalic() == chatmodifier.isItalic()) && (isRandom() == chatmodifier.isRandom()) && (isStrikethrough() == chatmodifier.isStrikethrough()) && (isUnderlined() == chatmodifier.isUnderlined()))
/*     */     {
/* 222 */       if (h() != null ? 
/* 223 */         h().equals(chatmodifier.h()) : 
/*     */         
/*     */ 
/* 226 */         chatmodifier.h() == null)
/*     */       {
/*     */ 
/*     */ 
/* 230 */         if (i() != null ? 
/* 231 */           i().equals(chatmodifier.i()) : 
/*     */           
/*     */ 
/* 234 */           chatmodifier.i() == null)
/*     */         {
/*     */ 
/*     */ 
/* 238 */           if (j() != null ? 
/* 239 */             j().equals(chatmodifier.j()) : 
/*     */             
/*     */ 
/* 242 */             chatmodifier.j() == null)
/*     */           {
/*     */ 
/*     */ 
/* 246 */             boolean flag = true;
/* 247 */             return flag;
/*     */           } }
/*     */       }
/*     */     }
/* 251 */     boolean flag = false;
/* 252 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 258 */     int i = this.b == null ? 0 : this.b.hashCode();
/*     */     
/* 260 */     i = 31 * i + (this.c == null ? 0 : this.c.hashCode());
/* 261 */     i = 31 * i + (this.d == null ? 0 : this.d.hashCode());
/* 262 */     i = 31 * i + (this.e == null ? 0 : this.e.hashCode());
/* 263 */     i = 31 * i + (this.f == null ? 0 : this.f.hashCode());
/* 264 */     i = 31 * i + (this.g == null ? 0 : this.g.hashCode());
/* 265 */     i = 31 * i + (this.h == null ? 0 : this.h.hashCode());
/* 266 */     i = 31 * i + (this.i == null ? 0 : this.i.hashCode());
/* 267 */     i = 31 * i + (this.j == null ? 0 : this.j.hashCode());
/*     */     
/* 269 */     return i;
/*     */   }
/*     */   
/*     */   public ChatModifier clone() {
/* 273 */     ChatModifier chatmodifier = new ChatModifier();
/*     */     
/* 275 */     chatmodifier.c = this.c;
/* 276 */     chatmodifier.d = this.d;
/* 277 */     chatmodifier.f = this.f;
/* 278 */     chatmodifier.e = this.e;
/* 279 */     chatmodifier.g = this.g;
/* 280 */     chatmodifier.b = this.b;
/* 281 */     chatmodifier.h = this.h;
/* 282 */     chatmodifier.i = this.i;
/* 283 */     chatmodifier.a = this.a;
/* 284 */     chatmodifier.j = this.j;
/* 285 */     return chatmodifier;
/*     */   }
/*     */   
/*     */   public ChatModifier n() {
/* 289 */     ChatModifier chatmodifier = new ChatModifier();
/*     */     
/* 291 */     chatmodifier.setBold(Boolean.valueOf(isBold()));
/* 292 */     chatmodifier.setItalic(Boolean.valueOf(isItalic()));
/* 293 */     chatmodifier.setStrikethrough(Boolean.valueOf(isStrikethrough()));
/* 294 */     chatmodifier.setUnderline(Boolean.valueOf(isUnderlined()));
/* 295 */     chatmodifier.setRandom(Boolean.valueOf(isRandom()));
/* 296 */     chatmodifier.setColor(getColor());
/* 297 */     chatmodifier.setChatClickable(h());
/* 298 */     chatmodifier.setChatHoverable(i());
/* 299 */     chatmodifier.setInsertion(j());
/* 300 */     return chatmodifier;
/*     */   }
/*     */   
/*     */   public static class ChatModifierSerializer
/*     */     implements JsonDeserializer<ChatModifier>, JsonSerializer<ChatModifier>
/*     */   {
/*     */     public ChatModifier a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException
/*     */     {
/* 308 */       if (jsonelement.isJsonObject()) {
/* 309 */         ChatModifier chatmodifier = new ChatModifier();
/* 310 */         JsonObject jsonobject = jsonelement.getAsJsonObject();
/*     */         
/* 312 */         if (jsonobject == null) {
/* 313 */           return null;
/*     */         }
/* 315 */         if (jsonobject.has("bold")) {
/* 316 */           chatmodifier.c = Boolean.valueOf(jsonobject.get("bold").getAsBoolean());
/*     */         }
/*     */         
/* 319 */         if (jsonobject.has("italic")) {
/* 320 */           chatmodifier.d = Boolean.valueOf(jsonobject.get("italic").getAsBoolean());
/*     */         }
/*     */         
/* 323 */         if (jsonobject.has("underlined")) {
/* 324 */           chatmodifier.e = Boolean.valueOf(jsonobject.get("underlined").getAsBoolean());
/*     */         }
/*     */         
/* 327 */         if (jsonobject.has("strikethrough")) {
/* 328 */           chatmodifier.f = Boolean.valueOf(jsonobject.get("strikethrough").getAsBoolean());
/*     */         }
/*     */         
/* 331 */         if (jsonobject.has("obfuscated")) {
/* 332 */           chatmodifier.g = Boolean.valueOf(jsonobject.get("obfuscated").getAsBoolean());
/*     */         }
/*     */         
/* 335 */         if (jsonobject.has("color")) {
/* 336 */           chatmodifier.b = ((EnumChatFormat)jsondeserializationcontext.deserialize(jsonobject.get("color"), EnumChatFormat.class));
/*     */         }
/*     */         
/* 339 */         if (jsonobject.has("insertion")) {
/* 340 */           chatmodifier.j = jsonobject.get("insertion").getAsString();
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 346 */         if (jsonobject.has("clickEvent")) {
/* 347 */           JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
/* 348 */           if (jsonobject1 != null) {
/* 349 */             JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
/* 350 */             ChatClickable.EnumClickAction chatclickable_enumclickaction = jsonprimitive == null ? null : ChatClickable.EnumClickAction.a(jsonprimitive.getAsString());
/* 351 */             JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
/* 352 */             String s = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();
/*     */             
/* 354 */             if ((chatclickable_enumclickaction != null) && (s != null) && (chatclickable_enumclickaction.a())) {
/* 355 */               chatmodifier.h = new ChatClickable(chatclickable_enumclickaction, s);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 360 */         if (jsonobject.has("hoverEvent")) {
/* 361 */           JsonObject jsonobject1 = jsonobject.getAsJsonObject("hoverEvent");
/* 362 */           if (jsonobject1 != null) {
/* 363 */             JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
/* 364 */             ChatHoverable.EnumHoverAction chathoverable_enumhoveraction = jsonprimitive == null ? null : ChatHoverable.EnumHoverAction.a(jsonprimitive.getAsString());
/* 365 */             IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)jsondeserializationcontext.deserialize(jsonobject1.get("value"), IChatBaseComponent.class);
/*     */             
/* 367 */             if ((chathoverable_enumhoveraction != null) && (ichatbasecomponent != null) && (chathoverable_enumhoveraction.a())) {
/* 368 */               chatmodifier.i = new ChatHoverable(chathoverable_enumhoveraction, ichatbasecomponent);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 373 */         return chatmodifier;
/*     */       }
/*     */       
/* 376 */       return null;
/*     */     }
/*     */     
/*     */     public JsonElement a(ChatModifier chatmodifier, Type type, JsonSerializationContext jsonserializationcontext)
/*     */     {
/* 381 */       if (chatmodifier.g()) {
/* 382 */         return null;
/*     */       }
/* 384 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 386 */       if (chatmodifier.c != null) {
/* 387 */         jsonobject.addProperty("bold", chatmodifier.c);
/*     */       }
/*     */       
/* 390 */       if (chatmodifier.d != null) {
/* 391 */         jsonobject.addProperty("italic", chatmodifier.d);
/*     */       }
/*     */       
/* 394 */       if (chatmodifier.e != null) {
/* 395 */         jsonobject.addProperty("underlined", chatmodifier.e);
/*     */       }
/*     */       
/* 398 */       if (chatmodifier.f != null) {
/* 399 */         jsonobject.addProperty("strikethrough", chatmodifier.f);
/*     */       }
/*     */       
/* 402 */       if (chatmodifier.g != null) {
/* 403 */         jsonobject.addProperty("obfuscated", chatmodifier.g);
/*     */       }
/*     */       
/* 406 */       if (chatmodifier.b != null) {
/* 407 */         jsonobject.add("color", jsonserializationcontext.serialize(chatmodifier.b));
/*     */       }
/*     */       
/* 410 */       if (chatmodifier.j != null) {
/* 411 */         jsonobject.add("insertion", jsonserializationcontext.serialize(chatmodifier.j));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 416 */       if (chatmodifier.h != null) {
/* 417 */         JsonObject jsonobject1 = new JsonObject();
/* 418 */         jsonobject1.addProperty("action", chatmodifier.h.a().b());
/* 419 */         jsonobject1.addProperty("value", chatmodifier.h.b());
/* 420 */         jsonobject.add("clickEvent", jsonobject1);
/*     */       }
/*     */       
/* 423 */       if (chatmodifier.i != null) {
/* 424 */         JsonObject jsonobject1 = new JsonObject();
/* 425 */         jsonobject1.addProperty("action", chatmodifier.i.a().b());
/* 426 */         jsonobject1.add("value", jsonserializationcontext.serialize(chatmodifier.i.b()));
/* 427 */         jsonobject.add("hoverEvent", jsonobject1);
/*     */       }
/*     */       
/* 430 */       return jsonobject;
/*     */     }
/*     */     
/*     */     public JsonElement serialize(ChatModifier object, Type type, JsonSerializationContext jsonserializationcontext)
/*     */     {
/* 435 */       return a(object, type, jsonserializationcontext);
/*     */     }
/*     */     
/*     */     public ChatModifier deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
/* 439 */       return a(jsonelement, type, jsondeserializationcontext);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */