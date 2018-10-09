/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatComponentScore
/*    */   extends ChatBaseComponent
/*    */ {
/*    */   private final String b;
/*    */   
/*    */   private final String c;
/*    */   
/* 12 */   private String d = "";
/*    */   
/*    */   public ChatComponentScore(String paramString1, String paramString2) {
/* 15 */     this.b = paramString1;
/* 16 */     this.c = paramString2;
/*    */   }
/*    */   
/*    */   public String g() {
/* 20 */     return this.b;
/*    */   }
/*    */   
/*    */   public String h() {
/* 24 */     return this.c;
/*    */   }
/*    */   
/*    */   public void b(String paramString) {
/* 28 */     this.d = paramString;
/*    */   }
/*    */   
/*    */   public String getText()
/*    */   {
/* 33 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 34 */     if ((localMinecraftServer != null) && (localMinecraftServer.O()) && (UtilColor.b(this.d))) {
/* 35 */       Scoreboard localScoreboard = localMinecraftServer.getWorldServer(0).getScoreboard();
/* 36 */       ScoreboardObjective localScoreboardObjective = localScoreboard.getObjective(this.c);
/* 37 */       if (localScoreboard.b(this.b, localScoreboardObjective)) {
/* 38 */         ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(this.b, localScoreboardObjective);
/* 39 */         b(String.format("%d", new Object[] { Integer.valueOf(localScoreboardScore.getScore()) }));
/*    */       } else {
/* 41 */         this.d = "";
/*    */       }
/*    */     }
/* 44 */     return this.d;
/*    */   }
/*    */   
/*    */   public ChatComponentScore i()
/*    */   {
/* 49 */     ChatComponentScore localChatComponentScore = new ChatComponentScore(this.b, this.c);
/* 50 */     localChatComponentScore.b(this.d);
/* 51 */     localChatComponentScore.setChatModifier(getChatModifier().clone());
/* 52 */     for (IChatBaseComponent localIChatBaseComponent : a()) {
/* 53 */       localChatComponentScore.addSibling(localIChatBaseComponent.f());
/*    */     }
/* 55 */     return localChatComponentScore;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 60 */     if (this == paramObject) {
/* 61 */       return true;
/*    */     }
/*    */     
/* 64 */     if ((paramObject instanceof ChatComponentScore)) {
/* 65 */       ChatComponentScore localChatComponentScore = (ChatComponentScore)paramObject;
/* 66 */       return (this.b.equals(localChatComponentScore.b)) && (this.c.equals(localChatComponentScore.c)) && (super.equals(paramObject));
/*    */     }
/*    */     
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 74 */     return "ScoreComponent{name='" + this.b + '\'' + "objective='" + this.c + '\'' + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatComponentScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */