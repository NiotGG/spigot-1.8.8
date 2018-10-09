/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ChatHoverable
/*    */ {
/*    */   private final EnumHoverAction a;
/*    */   private final IChatBaseComponent b;
/*    */   
/*    */   public ChatHoverable(EnumHoverAction paramEnumHoverAction, IChatBaseComponent paramIChatBaseComponent)
/*    */   {
/* 12 */     this.a = paramEnumHoverAction;
/* 13 */     this.b = paramIChatBaseComponent;
/*    */   }
/*    */   
/*    */   public EnumHoverAction a() {
/* 17 */     return this.a;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent b() {
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
/* 33 */     ChatHoverable localChatHoverable = (ChatHoverable)paramObject;
/*    */     
/* 35 */     if (this.a != localChatHoverable.a) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (this.b != null ? !this.b.equals(localChatHoverable.b) : localChatHoverable.b != null) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 47 */     return "HoverEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
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
/*    */   public static enum EnumHoverAction
/*    */   {
/*    */     private static final Map<String, EnumHoverAction> e;
/*    */     
/*    */     private final boolean f;
/*    */     
/*    */     private final String g;
/*    */     
/*    */ 
/*    */     private EnumHoverAction(String paramString, boolean paramBoolean)
/*    */     {
/* 72 */       this.g = paramString;
/* 73 */       this.f = paramBoolean;
/*    */     }
/*    */     
/*    */     public boolean a() {
/* 77 */       return this.f;
/*    */     }
/*    */     
/*    */     public String b() {
/* 81 */       return this.g;
/*    */     }
/*    */     
/*    */     static
/*    */     {
/* 67 */       e = com.google.common.collect.Maps.newHashMap();
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
/* 85 */       for (EnumHoverAction localEnumHoverAction : values()) {
/* 86 */         e.put(localEnumHoverAction.b(), localEnumHoverAction);
/*    */       }
/*    */     }
/*    */     
/*    */     public static EnumHoverAction a(String paramString) {
/* 91 */       return (EnumHoverAction)e.get(paramString);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatHoverable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */