/*    */ package net.md_5.bungee.chat;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.HashSet;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ import net.md_5.bungee.api.chat.TranslatableComponent;
/*    */ 
/*    */ 
/*    */ public class ComponentSerializer
/*    */   implements JsonDeserializer<BaseComponent>
/*    */ {
/* 20 */   private static final Gson gson = new GsonBuilder().registerTypeAdapter(BaseComponent.class, new ComponentSerializer()).registerTypeAdapter(TextComponent.class, new TextComponentSerializer()).registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer()).create();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 26 */   public static final ThreadLocal<HashSet<BaseComponent>> serializedComponents = new ThreadLocal();
/*    */   
/*    */   public static BaseComponent[] parse(String json)
/*    */   {
/* 30 */     if (json.startsWith("["))
/*    */     {
/* 32 */       return (BaseComponent[])gson.fromJson(json, BaseComponent[].class);
/*    */     }
/* 34 */     return new BaseComponent[] { (BaseComponent)gson.fromJson(json, BaseComponent.class) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String toString(BaseComponent component)
/*    */   {
/* 42 */     return gson.toJson(component);
/*    */   }
/*    */   
/*    */   public static String toString(BaseComponent... components)
/*    */   {
/* 47 */     return gson.toJson(new TextComponent(components));
/*    */   }
/*    */   
/*    */   public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
/*    */     throws JsonParseException
/*    */   {
/* 53 */     if (json.isJsonPrimitive())
/*    */     {
/* 55 */       return new TextComponent(json.getAsString());
/*    */     }
/* 57 */     JsonObject object = json.getAsJsonObject();
/* 58 */     if (object.has("translate"))
/*    */     {
/* 60 */       return (BaseComponent)context.deserialize(json, TranslatableComponent.class);
/*    */     }
/* 62 */     return (BaseComponent)context.deserialize(json, TextComponent.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\chat\ComponentSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */