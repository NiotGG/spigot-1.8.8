/*    */ package com.mojang.util;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UUIDTypeAdapter extends TypeAdapter<UUID>
/*    */ {
/*    */   public void write(JsonWriter paramJsonWriter, UUID paramUUID) throws IOException
/*    */   {
/* 13 */     paramJsonWriter.value(fromUUID(paramUUID));
/*    */   }
/*    */   
/*    */   public UUID read(JsonReader paramJsonReader) throws IOException
/*    */   {
/* 18 */     return fromString(paramJsonReader.nextString());
/*    */   }
/*    */   
/*    */   public static String fromUUID(UUID paramUUID) {
/* 22 */     return paramUUID.toString().replace("-", "");
/*    */   }
/*    */   
/*    */   public static UUID fromString(String paramString) {
/* 26 */     return UUID.fromString(paramString.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\util\UUIDTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */