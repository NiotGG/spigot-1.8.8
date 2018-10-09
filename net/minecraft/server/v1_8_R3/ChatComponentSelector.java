/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ChatComponentSelector extends ChatBaseComponent {
/*    */   private final String b;
/*    */   
/*    */   public ChatComponentSelector(String paramString) {
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
/*    */   public ChatComponentSelector h()
/*    */   {
/* 21 */     ChatComponentSelector localChatComponentSelector = new ChatComponentSelector(this.b);
/* 22 */     localChatComponentSelector.setChatModifier(getChatModifier().clone());
/* 23 */     for (IChatBaseComponent localIChatBaseComponent : a()) {
/* 24 */       localChatComponentSelector.addSibling(localIChatBaseComponent.f());
/*    */     }
/* 26 */     return localChatComponentSelector;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 31 */     if (this == paramObject) {
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     if ((paramObject instanceof ChatComponentSelector)) {
/* 36 */       ChatComponentSelector localChatComponentSelector = (ChatComponentSelector)paramObject;
/* 37 */       return (this.b.equals(localChatComponentSelector.b)) && (super.equals(paramObject));
/*    */     }
/*    */     
/* 40 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 45 */     return "SelectorComponent{pattern='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatComponentSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */