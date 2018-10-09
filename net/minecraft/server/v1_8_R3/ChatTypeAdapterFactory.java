/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
/*    */   {
/* 23 */     Class localClass = paramTypeToken.getRawType();
/* 24 */     if (!localClass.isEnum()) {
/* 25 */       return null;
/*    */     }
/*    */     
/* 28 */     HashMap localHashMap = Maps.newHashMap();
/* 29 */     for (Object localObject : localClass.getEnumConstants()) {
/* 30 */       localHashMap.put(a(localObject), localObject);
/*    */     }
/*    */     
/* 33 */     return new ChatTypeAdapter(this, localHashMap);
/*    */   }
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
/*    */   private String a(Object paramObject)
/*    */   {
/* 56 */     if ((paramObject instanceof Enum)) {
/* 57 */       return ((Enum)paramObject).name().toLowerCase(Locale.US);
/*    */     }
/* 59 */     return paramObject.toString().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */