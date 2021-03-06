/*    */ package net.md_5.bungee.chat;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Arrays;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TranslatableComponent;
/*    */ 
/*    */ public class TranslatableComponentSerializer
/*    */   extends BaseComponentSerializer
/*    */   implements JsonSerializer<TranslatableComponent>, JsonDeserializer<TranslatableComponent>
/*    */ {
/*    */   public TranslatableComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
/*    */     throws JsonParseException
/*    */   {
/* 22 */     TranslatableComponent component = new TranslatableComponent();
/* 23 */     JsonObject object = json.getAsJsonObject();
/* 24 */     deserialize(object, component, context);
/* 25 */     component.setTranslate(object.get("translate").getAsString());
/* 26 */     if (object.has("with"))
/*    */     {
/* 28 */       component.setWith(Arrays.asList((BaseComponent[])context.deserialize(object.get("with"), BaseComponent[].class)));
/*    */     }
/* 30 */     return component;
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonElement serialize(TranslatableComponent src, Type typeOfSrc, JsonSerializationContext context)
/*    */   {
/* 36 */     JsonObject object = new JsonObject();
/* 37 */     serialize(object, src, context);
/* 38 */     object.addProperty("translate", src.getTranslate());
/* 39 */     if (src.getWith() != null)
/*    */     {
/* 41 */       object.add("with", context.serialize(src.getWith()));
/*    */     }
/* 43 */     return object;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\chat\TranslatableComponentSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */