/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ChatComponentText extends ChatBaseComponent {
/*    */   private final String b;
/*    */   
/*    */   public ChatComponentText(String paramString) {
/*  7 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public String g() {
/* 11 */     return this.b;
/*    */   }
/*    */   
/*    */   public String getText()
/*    */   {
/* 16 */     return this.b;
/*    */   }
/*    */   
/*    */   public ChatComponentText h()
/*    */   {
/* 21 */     ChatComponentText localChatComponentText = new ChatComponentText(this.b);
/* 22 */     localChatComponentText.setChatModifier(getChatModifier().clone());
/* 23 */     for (IChatBaseComponent localIChatBaseComponent : a()) {
/* 24 */       localChatComponentText.addSibling(localIChatBaseComponent.f());
/*    */     }
/* 26 */     return localChatComponentText;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 31 */     if (this == paramObject) {
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     if ((paramObject instanceof ChatComponentText)) {
/* 36 */       ChatComponentText localChatComponentText = (ChatComponentText)paramObject;
/* 37 */       return (this.b.equals(localChatComponentText.g())) && (super.equals(paramObject));
/*    */     }
/*    */     
/* 40 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 45 */     return "TextComponent{text='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatComponentText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */