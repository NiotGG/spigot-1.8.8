/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.ForwardingSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AchievementSet extends ForwardingSet<String> implements IJsonStatistic
/*    */ {
/* 12 */   private final Set<String> a = Sets.newHashSet();
/*    */   
/*    */   public void a(JsonElement paramJsonElement)
/*    */   {
/* 16 */     if (paramJsonElement.isJsonArray()) {
/* 17 */       for (JsonElement localJsonElement : paramJsonElement.getAsJsonArray()) {
/* 18 */         add(localJsonElement.getAsString());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public JsonElement a()
/*    */   {
/* 25 */     JsonArray localJsonArray = new JsonArray();
/*    */     
/* 27 */     for (String str : this) {
/* 28 */       localJsonArray.add(new JsonPrimitive(str));
/*    */     }
/*    */     
/* 31 */     return localJsonArray;
/*    */   }
/*    */   
/*    */   protected Set<String> delegate()
/*    */   {
/* 36 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AchievementSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */