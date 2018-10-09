/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ public class MinecraftKey
/*    */ {
/*    */   protected final String a;
/*    */   protected final String b;
/*    */   
/*    */   protected MinecraftKey(int paramInt, String... paramVarArgs)
/*    */   {
/* 14 */     this.a = (StringUtils.isEmpty(paramVarArgs[0]) ? "minecraft" : paramVarArgs[0].toLowerCase());
/* 15 */     this.b = paramVarArgs[1];
/*    */     
/* 17 */     Validate.notNull(this.b);
/*    */   }
/*    */   
/*    */   public MinecraftKey(String paramString) {
/* 21 */     this(0, a(paramString));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected static String[] a(String paramString)
/*    */   {
/* 29 */     String[] arrayOfString = { null, paramString };
/* 30 */     int i = paramString.indexOf(':');
/* 31 */     if (i >= 0) {
/* 32 */       arrayOfString[1] = paramString.substring(i + 1, paramString.length());
/* 33 */       if (i > 1) {
/* 34 */         arrayOfString[0] = paramString.substring(0, i);
/*    */       }
/*    */     }
/*    */     
/* 38 */     return arrayOfString;
/*    */   }
/*    */   
/*    */   public String a() {
/* 42 */     return this.b;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 51 */     return this.a + ':' + this.b;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 56 */     if (this == paramObject) {
/* 57 */       return true;
/*    */     }
/*    */     
/* 60 */     if ((paramObject instanceof MinecraftKey)) {
/* 61 */       MinecraftKey localMinecraftKey = (MinecraftKey)paramObject;
/*    */       
/* 63 */       return (this.a.equals(localMinecraftKey.a)) && (this.b.equals(localMinecraftKey.b));
/*    */     }
/*    */     
/* 66 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 71 */     return 31 * this.a.hashCode() + this.b.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MinecraftKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */