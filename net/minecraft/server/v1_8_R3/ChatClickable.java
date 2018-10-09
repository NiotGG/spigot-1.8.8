/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ChatClickable
/*    */ {
/*    */   private final EnumClickAction a;
/*    */   private final String b;
/*    */   
/*    */   public ChatClickable(EnumClickAction paramEnumClickAction, String paramString)
/*    */   {
/* 12 */     this.a = paramEnumClickAction;
/* 13 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public EnumClickAction a() {
/* 17 */     return this.a;
/*    */   }
/*    */   
/*    */   public String b() {
/* 21 */     return this.b;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 26 */     if (this == paramObject) {
/* 27 */       return true;
/*    */     }
/* 29 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     ChatClickable localChatClickable = (ChatClickable)paramObject;
/*    */     
/* 35 */     if (this.a != localChatClickable.a) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (this.b != null ? !this.b.equals(localChatClickable.b) : localChatClickable.b != null) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 47 */     return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 55 */     int i = this.a.hashCode();
/* 56 */     i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
/* 57 */     return i;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static enum EnumClickAction
/*    */   {
/*    */     private static final Map<String, EnumClickAction> g;
/*    */     
/*    */     private final boolean h;
/*    */     
/*    */     private final String i;
/*    */     
/*    */ 
/*    */     private EnumClickAction(String paramString, boolean paramBoolean)
/*    */     {
/* 73 */       this.i = paramString;
/* 74 */       this.h = paramBoolean;
/*    */     }
/*    */     
/*    */     public boolean a() {
/* 78 */       return this.h;
/*    */     }
/*    */     
/*    */     public String b() {
/* 82 */       return this.i;
/*    */     }
/*    */     
/*    */     static
/*    */     {
/* 68 */       g = com.google.common.collect.Maps.newHashMap();
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
/* 86 */       for (EnumClickAction localEnumClickAction : values()) {
/* 87 */         g.put(localEnumClickAction.b(), localEnumClickAction);
/*    */       }
/*    */     }
/*    */     
/*    */     public static EnumClickAction a(String paramString) {
/* 92 */       return (EnumClickAction)g.get(paramString);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatClickable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */