/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ChatTypeAdapter
/*    */   extends TypeAdapter<T>
/*    */ {
/*    */   ChatTypeAdapter(ChatTypeAdapterFactory paramChatTypeAdapterFactory, Map paramMap) {}
/*    */   
/*    */   public void write(JsonWriter paramJsonWriter, T paramT)
/*    */     throws IOException
/*    */   {
/* 36 */     if (paramT == null) {
/* 37 */       paramJsonWriter.nullValue();
/*    */     } else {
/* 39 */       paramJsonWriter.value(ChatTypeAdapterFactory.a(this.b, paramT));
/*    */     }
/*    */   }
/*    */   
/*    */   public T read(JsonReader paramJsonReader) throws IOException
/*    */   {
/* 45 */     if (paramJsonReader.peek() == JsonToken.NULL) {
/* 46 */       paramJsonReader.nextNull();
/* 47 */       return null;
/*    */     }
/* 49 */     return (T)this.a.get(paramJsonReader.nextString());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */