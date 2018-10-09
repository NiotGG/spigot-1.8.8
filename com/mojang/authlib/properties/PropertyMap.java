/*    */ package com.mojang.authlib.properties;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class PropertyMap extends com.google.common.collect.ForwardingMultimap<String, Property>
/*    */ {
/*    */   private final com.google.common.collect.Multimap<String, Property> properties;
/*    */   
/*    */   public PropertyMap()
/*    */   {
/* 12 */     this.properties = com.google.common.collect.LinkedHashMultimap.create();
/*    */   }
/*    */   
/*    */   protected com.google.common.collect.Multimap<String, Property> delegate() {
/* 16 */     return this.properties;
/*    */   }
/*    */   
/*    */   public static class Serializer implements com.google.gson.JsonSerializer<PropertyMap>, com.google.gson.JsonDeserializer<PropertyMap>
/*    */   {
/*    */     public PropertyMap deserialize(com.google.gson.JsonElement paramJsonElement, java.lang.reflect.Type paramType, com.google.gson.JsonDeserializationContext paramJsonDeserializationContext) throws com.google.gson.JsonParseException {
/* 22 */       PropertyMap localPropertyMap = new PropertyMap();
/*    */       Object localObject1;
/* 24 */       Object localObject2; Object localObject3; Object localObject4; Object localObject5; if ((paramJsonElement instanceof JsonObject)) {
/* 25 */         localObject1 = (JsonObject)paramJsonElement;
/*    */         
/* 27 */         for (localObject2 = ((JsonObject)localObject1).entrySet().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (java.util.Map.Entry)((Iterator)localObject2).next();
/* 28 */           if ((((java.util.Map.Entry)localObject3).getValue() instanceof com.google.gson.JsonArray)) {
/* 29 */             for (localObject4 = ((com.google.gson.JsonArray)((java.util.Map.Entry)localObject3).getValue()).iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (com.google.gson.JsonElement)((Iterator)localObject4).next();
/* 30 */               localPropertyMap.put(((java.util.Map.Entry)localObject3).getKey(), new Property((String)((java.util.Map.Entry)localObject3).getKey(), ((com.google.gson.JsonElement)localObject5).getAsString()));
/*    */             }
/*    */           }
/*    */         }
/* 34 */       } else if ((paramJsonElement instanceof com.google.gson.JsonArray)) {
/* 35 */         for (localObject1 = ((com.google.gson.JsonArray)paramJsonElement).iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (com.google.gson.JsonElement)((Iterator)localObject1).next();
/* 36 */           if ((localObject2 instanceof JsonObject)) {
/* 37 */             localObject3 = (JsonObject)localObject2;
/* 38 */             localObject4 = ((JsonObject)localObject3).getAsJsonPrimitive("name").getAsString();
/* 39 */             localObject5 = ((JsonObject)localObject3).getAsJsonPrimitive("value").getAsString();
/*    */             
/* 41 */             if (((JsonObject)localObject3).has("signature")) {
/* 42 */               localPropertyMap.put(localObject4, new Property((String)localObject4, (String)localObject5, ((JsonObject)localObject3).getAsJsonPrimitive("signature").getAsString()));
/*    */             } else {
/* 44 */               localPropertyMap.put(localObject4, new Property((String)localObject4, (String)localObject5));
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */       
/* 50 */       return localPropertyMap;
/*    */     }
/*    */     
/*    */     public com.google.gson.JsonElement serialize(PropertyMap paramPropertyMap, java.lang.reflect.Type paramType, com.google.gson.JsonSerializationContext paramJsonSerializationContext)
/*    */     {
/* 55 */       com.google.gson.JsonArray localJsonArray = new com.google.gson.JsonArray();
/*    */       
/* 57 */       for (Property localProperty : paramPropertyMap.values()) {
/* 58 */         JsonObject localJsonObject = new JsonObject();
/*    */         
/* 60 */         localJsonObject.addProperty("name", localProperty.getName());
/* 61 */         localJsonObject.addProperty("value", localProperty.getValue());
/*    */         
/* 63 */         if (localProperty.hasSignature()) {
/* 64 */           localJsonObject.addProperty("signature", localProperty.getSignature());
/*    */         }
/*    */         
/* 67 */         localJsonArray.add(localJsonObject);
/*    */       }
/*    */       
/* 70 */       return localJsonArray;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\properties\PropertyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */