/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ 
/*    */ public class JsonListEntry<T> {
/*    */   private final T a;
/*    */   
/*    */   public JsonListEntry(T paramT) {
/*  9 */     this.a = paramT;
/*    */   }
/*    */   
/*    */   protected JsonListEntry(T paramT, JsonObject paramJsonObject) {
/* 13 */     this.a = paramT;
/*    */   }
/*    */   
/*    */   public T getKey() {
/* 17 */     return (T)this.a;
/*    */   }
/*    */   
/*    */   boolean hasExpired() {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   protected void a(JsonObject paramJsonObject) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\JsonListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */