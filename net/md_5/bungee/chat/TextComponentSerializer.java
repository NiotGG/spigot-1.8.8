/*    */ package net.md_5.bungee.chat;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ 
/*    */ public class TextComponentSerializer
/*    */   extends BaseComponentSerializer
/*    */   implements JsonSerializer<TextComponent>, JsonDeserializer<TextComponent>
/*    */ {
/*    */   public TextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
/*    */     throws JsonParseException
/*    */   {
/* 23 */     TextComponent component = new TextComponent();
/* 24 */     JsonObject object = json.getAsJsonObject();
/* 25 */     deserialize(object, component, context);
/* 26 */     component.setText(object.get("text").getAsString());
/* 27 */     return component;
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonElement serialize(TextComponent src, Type typeOfSrc, JsonSerializationContext context)
/*    */   {
/* 33 */     List<BaseComponent> extra = src.getExtra();
/* 34 */     if ((!src.hasFormatting()) && ((extra == null) || (extra.isEmpty())))
/*    */     {
/* 36 */       return new JsonPrimitive(src.getText());
/*    */     }
/* 38 */     JsonObject object = new JsonObject();
/* 39 */     serialize(object, src, context);
/* 40 */     object.addProperty("text", src.getText());
/* 41 */     return object;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\chat\TextComponentSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */