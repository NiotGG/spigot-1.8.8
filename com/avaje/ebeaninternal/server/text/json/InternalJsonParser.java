/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonElement;
/*    */ import java.io.Reader;
/*    */ 
/*    */ 
/*    */ public class InternalJsonParser
/*    */ {
/*    */   public static JsonElement parse(String s)
/*    */   {
/* 11 */     ReadJsonSourceString src = new ReadJsonSourceString(s);
/* 12 */     ReadBasicJsonContext b = new ReadBasicJsonContext(src);
/* 13 */     return ReadJsonRawReader.readJsonElement(b);
/*    */   }
/*    */   
/*    */   public static JsonElement parse(Reader s)
/*    */   {
/* 18 */     ReadJsonSourceReader src = new ReadJsonSourceReader(s, 512, 256);
/* 19 */     ReadBasicJsonContext b = new ReadBasicJsonContext(src);
/* 20 */     return ReadJsonRawReader.readJsonElement(b);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\InternalJsonParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */