/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatDeserializer
/*     */ {
/*     */   public static boolean d(JsonObject paramJsonObject, String paramString)
/*     */   {
/*  54 */     if (!g(paramJsonObject, paramString)) {
/*  55 */       return false;
/*     */     }
/*  57 */     if (!paramJsonObject.get(paramString).isJsonArray()) {
/*  58 */       return false;
/*     */     }
/*  60 */     return true;
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
/*     */ 
/*     */ 
/*     */   public static boolean g(JsonObject paramJsonObject, String paramString)
/*     */   {
/*  84 */     if (paramJsonObject == null) {
/*  85 */       return false;
/*     */     }
/*  87 */     if (paramJsonObject.get(paramString) == null) {
/*  88 */       return false;
/*     */     }
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public static String a(JsonElement paramJsonElement, String paramString) {
/*  94 */     if (paramJsonElement.isJsonPrimitive()) {
/*  95 */       return paramJsonElement.getAsString();
/*     */     }
/*  97 */     throw new JsonSyntaxException("Expected " + paramString + " to be a string, was " + d(paramJsonElement));
/*     */   }
/*     */   
/*     */   public static String h(JsonObject paramJsonObject, String paramString)
/*     */   {
/* 102 */     if (paramJsonObject.has(paramString)) {
/* 103 */       return a(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 105 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a string");
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
/*     */   public static boolean b(JsonElement paramJsonElement, String paramString)
/*     */   {
/* 118 */     if (paramJsonElement.isJsonPrimitive()) {
/* 119 */       return paramJsonElement.getAsBoolean();
/*     */     }
/* 121 */     throw new JsonSyntaxException("Expected " + paramString + " to be a Boolean, was " + d(paramJsonElement));
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
/*     */   public static boolean a(JsonObject paramJsonObject, String paramString, boolean paramBoolean)
/*     */   {
/* 134 */     if (paramJsonObject.has(paramString)) {
/* 135 */       return b(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 137 */     return paramBoolean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float d(JsonElement paramJsonElement, String paramString)
/*     */   {
/* 166 */     if ((paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isNumber())) {
/* 167 */       return paramJsonElement.getAsFloat();
/*     */     }
/* 169 */     throw new JsonSyntaxException("Expected " + paramString + " to be a Float, was " + d(paramJsonElement));
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
/*     */   public static float a(JsonObject paramJsonObject, String paramString, float paramFloat)
/*     */   {
/* 182 */     if (paramJsonObject.has(paramString)) {
/* 183 */       return d(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 185 */     return paramFloat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int f(JsonElement paramJsonElement, String paramString)
/*     */   {
/* 214 */     if ((paramJsonElement.isJsonPrimitive()) && (paramJsonElement.getAsJsonPrimitive().isNumber())) {
/* 215 */       return paramJsonElement.getAsInt();
/*     */     }
/* 217 */     throw new JsonSyntaxException("Expected " + paramString + " to be a Int, was " + d(paramJsonElement));
/*     */   }
/*     */   
/*     */   public static int m(JsonObject paramJsonObject, String paramString)
/*     */   {
/* 222 */     if (paramJsonObject.has(paramString)) {
/* 223 */       return f(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 225 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a Int");
/*     */   }
/*     */   
/*     */   public static int a(JsonObject paramJsonObject, String paramString, int paramInt)
/*     */   {
/* 230 */     if (paramJsonObject.has(paramString)) {
/* 231 */       return f(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 233 */     return paramInt;
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
/*     */   public static JsonObject l(JsonElement paramJsonElement, String paramString)
/*     */   {
/* 358 */     if (paramJsonElement.isJsonObject()) {
/* 359 */       return paramJsonElement.getAsJsonObject();
/*     */     }
/* 361 */     throw new JsonSyntaxException("Expected " + paramString + " to be a JsonObject, was " + d(paramJsonElement));
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
/*     */   public static JsonArray m(JsonElement paramJsonElement, String paramString)
/*     */   {
/* 382 */     if (paramJsonElement.isJsonArray()) {
/* 383 */       return paramJsonElement.getAsJsonArray();
/*     */     }
/* 385 */     throw new JsonSyntaxException("Expected " + paramString + " to be a JsonArray, was " + d(paramJsonElement));
/*     */   }
/*     */   
/*     */   public static JsonArray t(JsonObject paramJsonObject, String paramString)
/*     */   {
/* 390 */     if (paramJsonObject.has(paramString)) {
/* 391 */       return m(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 393 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a JsonArray");
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
/*     */   public static String d(JsonElement paramJsonElement)
/*     */   {
/* 406 */     String str = StringUtils.abbreviateMiddle(String.valueOf(paramJsonElement), "...", 10);
/* 407 */     if (paramJsonElement == null) {
/* 408 */       return "null (missing)";
/*     */     }
/* 410 */     if (paramJsonElement.isJsonNull()) {
/* 411 */       return "null (json)";
/*     */     }
/* 413 */     if (paramJsonElement.isJsonArray()) {
/* 414 */       return "an array (" + str + ")";
/*     */     }
/* 416 */     if (paramJsonElement.isJsonObject()) {
/* 417 */       return "an object (" + str + ")";
/*     */     }
/* 419 */     if (paramJsonElement.isJsonPrimitive()) {
/* 420 */       JsonPrimitive localJsonPrimitive = paramJsonElement.getAsJsonPrimitive();
/* 421 */       if (localJsonPrimitive.isNumber()) {
/* 422 */         return "a number (" + str + ")";
/*     */       }
/* 424 */       if (localJsonPrimitive.isBoolean()) {
/* 425 */         return "a boolean (" + str + ")";
/*     */       }
/*     */     }
/* 428 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */